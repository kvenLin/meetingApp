package com.uchain.meetingapp.service.redis;

public class MeetingKey extends BasePrefix{
    public MeetingKey(String prefix) {
        super(prefix);
    }

    public MeetingKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MeetingKey getById = new MeetingKey(5*60,"id");
    public static MeetingKey getByRoomId = new MeetingKey(5*60,"roomId");
}
