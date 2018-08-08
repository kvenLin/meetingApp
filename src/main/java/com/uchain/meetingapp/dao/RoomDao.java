package com.uchain.meetingapp.dao;

import com.uchain.meetingapp.entity.Room;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoomDao {
    @Insert("insert into room(address,max_contain) values(#{address},#{maxContain})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = Long.class,statement = "select last_insert_id()")
    int addRoom(Room room);
    @Delete("delete from room where id=#{id}")
    int deleteRoom(long id);
    @Select("select id,address,max_contain as maxContain from room where id=#{id}")
    Room findById(long id);
    @Select("select id,address,max_contain as maxContain from room where address like #{address}")
    List<Room> findByAddress(String address);
    @Select({"select id,address,max_contain as maxContain from room"})
    List<Room> findAll();
    @Update("update room set address=#{address},max_contain=#{maxContain} where id=#{id}")
    int updateRoom(Room room);
}
