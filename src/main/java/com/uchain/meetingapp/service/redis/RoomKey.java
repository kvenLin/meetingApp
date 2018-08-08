package com.uchain.meetingapp.service.redis;

public class RoomKey extends BasePrefix{
    public RoomKey(String prefix) {
        super(prefix);
    }

    public RoomKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static RoomKey getById = new RoomKey("id");
    public static RoomKey getRooms = new RoomKey("rooms");
}
