package com.uchain.meetingapp.service.impl;

import com.uchain.meetingapp.dao.RoomDao;
import com.uchain.meetingapp.entity.Room;
import com.uchain.meetingapp.exception.GlobalException;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.service.RoomService;
import com.uchain.meetingapp.service.redis.RedisService;
import com.uchain.meetingapp.service.redis.RoomKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RedisService redisService;

    @Override
    public Room addRoom(String address, Integer max_contain) {
        Room room = new Room();
        room.setAddress(address);
        room.setMaxContain(max_contain);
        if (roomDao.addRoom(room)!=1){
            throw new GlobalException(CodeMsg.INSERT_ERROR);
        }
        redisService.set(RoomKey.getById,""+room.getId(),room);
        redisService.delete(RoomKey.getRooms,"");
        return room;
    }

    @Override
    public boolean deleteRoom(long id) {
        roomDao.deleteRoom(id);
        redisService.delete(RoomKey.getById,""+id);
        redisService.delete(RoomKey.getRooms,"");
        return true;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = redisService.get(RoomKey.getRooms,"",List.class);
        if (rooms!=null){
            return rooms;
        }
        rooms = roomDao.findAll();
        if (rooms!=null){
            redisService.set(RoomKey.getRooms,"",rooms);
        }
        return rooms;
    }

    @Override
    public List<Room> findByAddress(String address) {
        address = '%'+address+'%';
        return roomDao.findByAddress(address);
    }

    @Override
    public Room findById(long id) {
        Room room = redisService.get(RoomKey.getById,""+id,Room.class);
        if (room!=null){
            return room;
        }
        room = roomDao.findById(id);
        if (room!=null){
            redisService.set(RoomKey.getById,""+id,room);
        }
        return room;
    }

    @Override
    public boolean updateRoom(Room room) {
        if (findById(room.getId())==null){
            throw new GlobalException(CodeMsg.DATA_NOT_EXIST);
        }
        if (roomDao.updateRoom(room)!=1){
            throw new GlobalException(CodeMsg.UPDATE_ERROR);
        }
        redisService.delete(RoomKey.getRooms,"");
        redisService.set(RoomKey.getById,""+room.getId(),room);
        return true;
    }
}
