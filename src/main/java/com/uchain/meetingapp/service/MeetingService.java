package com.uchain.meetingapp.service;

import com.uchain.meetingapp.DTO.CancelForm;
import com.uchain.meetingapp.entity.Meeting;
import com.uchain.meetingapp.result.Result;

import java.util.Date;
import java.util.List;

public interface MeetingService {

    Result addMeeting(Meeting meeting);
    boolean deleteMeeting(long id);
    List<Meeting> findAll();
    Meeting findById(long id);
    boolean updateMeeting(Meeting meeting);
    Result cancelMeeting(CancelForm cancelForm);
    List<Meeting> findByRoomId(long roomId);
    List<Meeting> findByTime(Date startTime,Date endTime);
}
