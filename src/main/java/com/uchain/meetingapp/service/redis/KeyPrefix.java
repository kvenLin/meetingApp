package com.uchain.meetingapp.service.redis;

public interface KeyPrefix {
    public int expireSeconds();
    public String getPrefix();
}
