package com.uchain.meetingapp.service.impl;

import com.uchain.meetingapp.DTO.CancelForm;
import com.uchain.meetingapp.dao.MeetingDao;
import com.uchain.meetingapp.entity.Meeting;
import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.exception.GlobalException;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.service.MeetingService;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.service.redis.MeetingKey;
import com.uchain.meetingapp.service.redis.RedisService;
import com.uchain.meetingapp.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public Result addMeeting(Meeting meeting) {
        List<Meeting> meetings = findByTime(meeting.getStartTime(),meeting.getEndTime());
        if (meetings!=null){
            return Result.error(CodeMsg.MEETING_TIME_CONFLICT);
        }
        Authentication authentication = userService.getCurrentAuthentication();
        if (authentication==null){
            return Result.error(CodeMsg.AUTHENTICATION_ERROR);
        }
        String username = (String) authentication.getPrincipal();
        long id = WebUtil.getUserId(username);
        meeting.setCreatedBy(id);
        meeting.setUpdatedBy(id);
        if (meetingDao.addMeeting(meeting)!=1){
            throw new GlobalException(CodeMsg.INSERT_ERROR);
        }
        redisService.set(MeetingKey.getById,""+meeting.getId(),meeting);
        return Result.success(meeting);
    }

    @Override
    public boolean deleteMeeting(long id) {
        Meeting meeting = findById(id);
        if (meeting==null){
            log.error("不存在会议Id:{}",id);
            return false;
        }
        User user = userService.getCurrentUser();
        if (meeting.getCreatedBy()!=user.getId()){
            throw new GlobalException(CodeMsg.PERMISSION_DENY);
        }
        meetingDao.deleteMeeting(id);
        redisService.delete(MeetingKey.getById,""+meeting.getId());
        return true;
    }

    @Override
    public List<Meeting> findAll() {
        return meetingDao.findAll();
    }

    @Override
    public Meeting findById(long id) {
        Meeting meeting = redisService.get(MeetingKey.getById, "" + id, Meeting.class);
        if (meeting==null){
            meeting = meetingDao.findById(id);
        }
        if (meeting!=null){
            redisService.set(MeetingKey.getById,""+id,meeting);
            return meeting;
        }
        return meeting;
    }

    @Override
    public boolean updateMeeting(Meeting meeting) {
        String username = (String) userService.getCurrentAuthentication().getPrincipal();
        long userId = WebUtil.getUserId(username);
        Meeting meeting1 = findById(meeting.getId());
        if (meeting1==null){
            return false;
        }
        if (meeting1.getCreatedBy()!=userId&&!userService.getCurrentUser().getRole().equals("admin")){
            throw new GlobalException(CodeMsg.PERMISSION_DENY);
        }
        meeting.setUpdatedBy(userId);
        if (meetingDao.updateMeeting(meeting)==1){
            redisService.delete(MeetingKey.getById,""+meeting.getId());
            return true;
        }
        return false;
    }

    @Override
    public Result cancelMeeting(CancelForm cancelForm) {
        Meeting meeting = findById(cancelForm.getMeetingId());
        if (meeting==null){
            return Result.error(CodeMsg.DATA_NOT_EXIST);
        }
        String username = (String) userService.getCurrentAuthentication().getPrincipal();
        int re = meetingDao.updateCancel(cancelForm.getReason(),cancelForm.getMeetingId(),WebUtil.getUserId(username));
        if (re==1){
            meeting.setStatus(2);
            meeting.setUpdatedBy(WebUtil.getUserId(username));
            meeting.setCancelReason(cancelForm.getReason());
            redisService.set(MeetingKey.getById,""+cancelForm.getMeetingId(),meeting);
            return Result.success(meeting);
        }
        return Result.error(CodeMsg.UPDATE_ERROR);
    }


    @Override
    public List<Meeting> findByRoomId(long roomId) {
        return meetingDao.findByRoomId(roomId);
    }

    @Override
    public List<Meeting> findByTime(Date startTime, Date endTime) {
        return meetingDao.findByTime(startTime,endTime);
    }

}
