package com.uchain.meetingapp.service;

import com.uchain.meetingapp.entity.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(String address,Integer max_contain);
    boolean deleteRoom(long id);
    List<Room> findAll();
    List<Room> findByAddress(String address);
    Room findById(long id);
    boolean updateRoom(Room room);
}
