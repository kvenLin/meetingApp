package com.uchain.meetingapp.service;


import com.uchain.meetingapp.result.Result;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(String to, String subject, String txt) throws MessagingException;
    boolean checkVerifyCode(String email,String verifyCode);
    Result sendVerifyCode(String email);
}
