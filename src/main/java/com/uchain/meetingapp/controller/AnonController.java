package com.uchain.meetingapp.controller;

import com.uchain.meetingapp.DTO.LoginForm;
import com.uchain.meetingapp.DTO.RegisterForm;
import com.uchain.meetingapp.DTO.ResetForm;
import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.service.EmailService;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/anon")
public class AnonController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/verifyCode")
    public Object sendVerifyCode(String email){
        if (email==null){
            return Result.error(CodeMsg.EMAIL_PATTERN_ERROR);
        }
        if (userService.findByEmail(email)!=null){
            return Result.error(CodeMsg.EMAIL_REGISTERED);
        }
        return emailService.sendVerifyCode(email);
    }

    @PostMapping("/register")
    public Object register(@Valid RegisterForm registerForm,HttpServletRequest request){
        return userService.register(registerForm,request);
    }

    @PostMapping("/signUp")
    public Object login(@Valid LoginForm loginForm, HttpServletRequest request, HttpServletResponse response){
        String ip = WebUtil.getClientIpAddr(request);
        Result result = userService.login(loginForm.getEmail(),loginForm.getPassword(),response);
        //登录成功,更改登录的ip记录
        if (result.getCode()==1){
            userService.updateUser(ip,4,loginForm.getEmail());
        }
        return result;
    }

    @PostMapping("/resetPassword")
    public Object resetPassword(@Valid ResetForm resetForm){
        return userService.resetPassword(resetForm.getOldPassword()
                ,resetForm.getNewPassword()
                ,resetForm.getEmail());
    }

}
