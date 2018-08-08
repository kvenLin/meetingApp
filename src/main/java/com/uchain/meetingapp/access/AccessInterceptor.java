package com.uchain.meetingapp.access;

import com.uchain.meetingapp.entity.User;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.sercurity.JwtTokenUtil;
import com.uchain.meetingapp.service.UserService;
import com.uchain.meetingapp.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = userService.getCurrentUser();
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
            if (user.getRole().equals(requireRole.role())){
                return true;
            }
            WebUtil.render(response,CodeMsg.PERMISSION_DENY);
            return false;
        }
        String token = request.getHeader(WebUtil.tokenHeader);
        if (jwtTokenUtil.canTokenBeRefreshed(token)){
            response.setHeader(WebUtil.tokenHeader,jwtTokenUtil.refreshToken(token));
        }
        return true;
    }
}
