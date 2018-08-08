package com.uchain.meetingapp.service.impl;

import com.uchain.meetingapp.DTO.RegisterForm;
import com.uchain.meetingapp.dao.UserDao;
import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.exception.GlobalException;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.sercurity.JwtTokenUtil;
import com.uchain.meetingapp.service.EmailService;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.service.redis.RedisService;
import com.uchain.meetingapp.service.redis.UserKey;
import com.uchain.meetingapp.util.Convert;
import com.uchain.meetingapp.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private EmailService emailService;
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public User addUser(User user) {
        if (userDao.addUser(user)==1){
            redisService.set(UserKey.getByEmail,user.getEmail(),user);
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        user = redisService.get(UserKey.getByEmail,email,User.class);
        if (user!=null){
            return user;
        }
        user = userDao.findByEmail(email);
        if (user==null){
            redisService.set(UserKey.getByEmail,email,user);
        }
        return user;
    }

    //更新用户信息:1.用户名2.密码3.电话号码4.登录的ip地址
    @Override
    public boolean updateUser(String info,Integer choice,String email) {
        int re;
        switch (choice){
            case 1:
                re = userDao.updateFullName(info,email);
                break;
            case 2:
                re = userDao.updatePassword(info,email);
                break;
            case 3:
                re = userDao.updateTelephone(info,email);
                break;
            case 4:
                re = userDao.updateLoginIp(info,email);
                break;
            default:
                re = 0;
                break;
        }
        if (re==1){
            redisService.delete(UserKey.getByEmail,email);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        redisService.delete(UserKey.getByEmail,email);
        userDao.deleteUser(email);
        return true;
    }

    @Override
    public Result login(String email, String password, HttpServletResponse response) {
        User user = findByEmail(email);
        if (user==null){
            log.error("用户不存在..");
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        //验证根据参数username和password生成的token是否有效若有效则生成authentication
        Authentication token1 = new UsernamePasswordAuthenticationToken(email+"_"+user.getId(),password);
        Authentication authentication = authenticationManager.authenticate(token1);
        //将认证通过的authentication放入容器
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //重新加载security来生成userDetails
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        log.info("加载userDetails:"+userDetails.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        response.setHeader(WebUtil.tokenHeader,token);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        return Result.success(role);
    }

    @Override
    public Result register(RegisterForm registerForm, HttpServletRequest request) {
        //检测用户是否存在
        if(findByEmail(registerForm.getEmail())!=null){
            return Result.error(CodeMsg.EMAIL_REGISTERED);
        }
        User user = Convert.convertUser(registerForm);
        user.setRegisterIp(WebUtil.getClientIpAddr(request));
        //检测验证码是否正确
        if (!emailService.checkVerifyCode(registerForm.getEmail(),registerForm.getVerifyCode())) {
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }
        addUser(user);
        return Result.success("注册成功..");
    }

    @Override
    public Result resetPassword(String oldPassword, String newPassword, String email) {
        User user = findByEmail(email);
        if (user==null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(oldPassword)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }
        if (!updateUser(newPassword,2,email)) {
            return Result.error(CodeMsg.UPDATE_ERROR);
        }
        return Result.success("更新密码成功..");
    }

    @Override
    public Authentication getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            throw new GlobalException(CodeMsg.AUTHENTICATION_ERROR);
        }
        return authentication;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = getCurrentAuthentication();
        String username = (String) authentication.getPrincipal();
        return findByEmail(WebUtil.getUserEmail(username));
    }

}
