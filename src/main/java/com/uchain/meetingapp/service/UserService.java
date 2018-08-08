package com.uchain.meetingapp.service;

import com.uchain.meetingapp.DTO.RegisterForm;
import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.result.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    User addUser(User user);
    User findByEmail(String email);
    boolean updateUser(String info,Integer choice,String email);
    boolean deleteUser(String email);
    Result login(String email, String password, HttpServletResponse response);
    Result register(RegisterForm registerForm, HttpServletRequest request);
    Result resetPassword(String oldPassword,String newPassword,String email);
    Authentication getCurrentAuthentication();
    User getCurrentUser();
}
