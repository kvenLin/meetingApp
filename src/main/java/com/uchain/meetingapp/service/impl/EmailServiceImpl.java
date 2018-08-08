package com.uchain.meetingapp.service.impl;

import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.service.EmailService;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.service.redis.RedisService;
import com.uchain.meetingapp.service.redis.UserKey;
import com.uchain.meetingapp.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String fromMail;
    @Value("${email.code.expiration}")
    private long validateCodeExpiration;

    @Override
    public void sendMail(String to, String subject, String txt) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setText(txt, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(fromMail);
        sender.send(message);
    }

    @Override
    public Result sendVerifyCode(String email) {
        String code = CodeUtil.getRandomStr();
        //TODO,测试可以关闭邮箱
//        try {
//            sendMail(email,"注册验证码","欢迎注册UChain会议室App应用,你的验证码是:"+code+"请在3分钟内输入");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            log.error("发送邮件失败..");
//            return Result.error(CodeMsg.SEND_EMAIL_ERROR);
//        }
        log.info("邮箱:{},验证码:{}",email,code);
        redisService.set(UserKey.getVerifyCode,email,code);
        return Result.success("发送邮件成功..");
    }

    @Override
    public boolean checkVerifyCode(String email, String verifyCode) {
        String code = redisService.get(UserKey.getVerifyCode,email,String.class);
        if (code!=null&&verifyCode.equals(code)){
            return true;
        }
        return false;
    }

}
