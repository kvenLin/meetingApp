package com.uchain.meetingapp.sercurity;

import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email = WebUtil.getUserEmail(username);
        //TODO,从数据库查询username,如果不存在则抛出异常
        User user = userService.findByEmail(email);
        if (user==null) {
            log.error("认证邮箱信息不存在");
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            //TODO,若存在则返回userDetails对象
            return new JwtUser(user.getEmail()+"_"+user.getId(),passwordEncoder.encode(user.getPassword()), user.getRole());
        }
    }
}
