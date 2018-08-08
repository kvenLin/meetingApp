package com.uchain.meetingapp.util;

import com.alibaba.fastjson.JSON;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class WebUtil {

    //自定义token的header的名称
    public static String tokenHeader = "Authorization";

    public static String getUserInfo(Integer index,String username){
        String[] re = username.split("_");
        return re[index];
    }
    public static Long getUserId(String username){
        String re = getUserInfo(1,username);
        return Long.valueOf(re);
    }
    public static String getUserEmail(String username){
        return getUserInfo(0,username);
    }

    public static void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String s = JSON.toJSONString(Result.error(codeMsg));
        out.write(s.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    //获取ip地址
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
        //return InetAddress.getByName(ip).getCanonicalHostName();
    }
}
