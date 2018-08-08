package com.uchain.meetingapp.controller;

import com.uchain.meetingapp.DTO.RoomForm;
import com.uchain.meetingapp.access.RequireRole;
import com.uchain.meetingapp.entity.Room;
import com.uchain.meetingapp.result.CodeMsg;
import com.uchain.meetingapp.result.Result;
import com.uchain.meetingapp.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @RequireRole(role = "admin")
    @PostMapping("/add")
    public Object addRoom(@Valid RoomForm roomForm){
        Room room = roomService.addRoom(roomForm.getAddress(),roomForm.getMaxContain());
        return Result.success(room);
    }

    @RequireRole(role = "admin")
    @PostMapping("/delete")
    public Object deleteRoom(long id){
        if (roomService.deleteRoom(id)){
            return Result.success(null);
        }
        return Result.error(CodeMsg.DELETE_ERROR);
    }

    @RequireRole(role = "admin")
    @PostMapping("/update")
    public Object updateRoom(@Valid Room room){
        if (roomService.updateRoom(room)){
            return Result.success(null);
        }
        return Result.error(CodeMsg.UPDATE_ERROR);
    }

    @GetMapping("/findAll")
    public Object findAllRoom(){
        List<Room> rooms = roomService.findAll();
        if (rooms==null){
            return Result.error(CodeMsg.DATA_NOT_EXIST);
        }
        return Result.success(rooms);
    }

    @GetMapping("/findById")
    public Object findById(long id){
        Room room = roomService.findById(id);
        if (room==null){
            return Result.error(CodeMsg.DATA_NOT_EXIST);
        }
        return Result.success(room);
    }

    @GetMapping("/findByAddress")
    public Object findByAddress(String address){
        List<Room> rooms = roomService.findByAddress(address);
        if (rooms==null){
            return Result.error(CodeMsg.DATA_NOT_EXIST);
        }
        return Result.success(rooms);
    }
}
