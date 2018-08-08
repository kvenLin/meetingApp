package com.uchain.meetingapp.util;

import org.apache.commons.lang3.RandomStringUtils;

public class CodeUtil {

    //得到6位随机字符串
    public static String getRandomStr(){
        return RandomStringUtils.random(6,"1234567890");
    }


}
