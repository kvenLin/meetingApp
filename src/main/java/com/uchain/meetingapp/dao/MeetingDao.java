package com.uchain.meetingapp.dao;

import com.uchain.meetingapp.entity.Meeting;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MeetingDao {

    @Insert("insert into meeting(start_time,end_time,member_num,room_id,theme" +
            ",description,member,status,created_by,updated_by) " +
            "values(#{startTime},#{endTime},#{memberNum},#{roomId},#{theme}" +
            ",#{description},#{member},#{status},#{createdBy},#{updatedBy})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Long.class,before = false,statement = "select last_insert_id()")
    int addMeeting(Meeting meeting);
    @Delete("delete from meeting where id=#{id}")
    int deleteMeeting(long id);
    @Select("select id,start_time as startTime,end_time as endTime,member_num as memberNum" +
            ",room_id as roomId,theme,description,member,status,cancel_reason as cancelReason" +
            ",created_by as createdBy,updated_by as updatedBy " +
            "from meeting where id=#{id}")
    Meeting findById(long id);
    @Select("select id,start_time as startTime,end_time as endTime,member_num as memberNum" +
            ",room_id as roomId,theme,description,member,status,cancel_reason as cancelReason" +
            ",created_by as createdBy,updated_by as updatedBy " +
            "from meeting where (start_time>#{startTime} and end_time<#{endTime}) " +
            "or (start_time<#{startTime} and end_time>#{endTime}) " +
            "or (start_time>#{startTime} and end_time<#{endTime})")
    List<Meeting> findByTime(Date startTime, Date endTime);
    @Select("select id,start_time as startTime,end_time as endTime,member_num as memberNum" +
            ",room_id as roomId,theme,description,member,status,cancel_reason as cancelReason" +
            ",created_by as createdBy,updated_by as updatedBy " +
            "from meeting where room_id=#{roomId}")
    List<Meeting> findByRoomId(long roomId);
    @Select("select * from meeting")
    List<Meeting> findAll();
    @Update("update meeting set start_time=#{startTime},end_time=#{endTime},member_num=#{memberNum}," +
            "theme=#{theme},description=#{description},member=#{member},updated_by=#{updatedBy} where id=#{id}")
    int updateMeeting(Meeting meeting);
    @Update("update meeting set cancel_reason=#{param1},status=2,updated_by=#{param3} where id=#{param2}")
    int updateCancel(String reason, long meetingId,long updatedBy);
}
