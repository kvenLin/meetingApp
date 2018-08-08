package com.uchain.meetingapp.service.redis;

public class UserKey extends BasePrefix{
    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getVerifyCode = new UserKey(3*60,"vc");
    public static UserKey getByEmail = new UserKey("email");
}
