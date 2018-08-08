package com.uchain.meetingapp.util;

import com.uchain.meetingapp.DTO.MeetingForm;
import com.uchain.meetingapp.DTO.RegisterForm;
import com.uchain.meetingapp.entity.Meeting;
import com.uchain.meetingapp.entity.User;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    public static String convertDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static User convertUser(RegisterForm registerForm){
        User user = new User();
//        user.setEmail(registerForm.getEmail());
//        user.setFullName(registerForm.getFullName());
//        user.setRole("user");
//        user.setPassword(registerForm.getPassword());
//        user.setTelephone(registerForm.getTelephone());
        BeanUtils.copyProperties(registerForm,user);
        user.setRole("user");
        return user;
    }

    public static Meeting convertMeeting(MeetingForm meetingForm){
        Meeting meeting = new Meeting();
        BeanUtils.copyProperties(meetingForm,meeting);
        meeting.setStatus(1);
        return meeting;
    }
}
