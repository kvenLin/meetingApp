package com.uchain.meetingapp.controller;

import com.uchain.meetingapp.DTO.CancelForm;
import com.uchain.meetingapp.DTO.MeetingForm;
import com.uchain.meetingapp.access.RequireRole;
import com.uchain.meetingapp.entity.Meeting;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.service.MeetingService;
import com.uchain.meetingapp.service.redis.RedisService;
import com.uchain.meetingapp.util.Convert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController implements InitializingBean {
    @Autowired
    private RedisService redisService;
    @Autowired
    private MeetingService meetingService;

    @PostMapping("/appointment")
    public Object appointMeeting(@Valid MeetingForm meetingForm){
        Meeting meeting = Convert.convertMeeting(meetingForm);
        return meetingService.addMeeting(meeting);
    }

    @PostMapping("/delete")
    public Object delete(long meetingId){
        if (meetingService.deleteMeeting(meetingId)){
            return Result.success(null);
        }
        return Result.error(CodeMsg.DATA_NOT_EXIST);
    }

    @GetMapping("/all")
    public Object allMeetings(){
        List<Meeting> meetings = meetingService.findAll();
        return Result.success(meetings);
    }

    @GetMapping("/room")
    public Object roomMeetings(Long roomId){
        if (roomId==null){
            return Result.error(CodeMsg.DATA_NOT_EXIST);
        }
        List<Meeting> meetings = meetingService.findByRoomId(roomId);
        return Result.success(meetings);
    }

    @PostMapping("/cancel")
    @RequireRole(role = "admin")
    public Object cancel(@Valid CancelForm cancelForm){
        return meetingService.cancelMeeting(cancelForm);
    }


    @PostMapping("/update")
    public Object changeMeetingInfo(@Valid Meeting meeting){
        if (meetingService.updateMeeting(meeting)){
            return Result.success(null);
        }
        return Result.error(CodeMsg.UPDATE_ERROR);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        redisService.flush();
    }
}
