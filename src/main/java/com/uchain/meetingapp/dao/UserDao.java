package com.uchain.meetingapp.dao;

import com.uchain.meetingapp.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Insert("insert into user(full_name,role,email,password,telephone,register_ip,last_login_ip) " +
            "values(#{fullName},#{role},#{email},#{password},#{telephone},#{registerIp},#{lastLoginIp})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Long.class,before = false,statement = "select last_insert_id()")
    int addUser(User user);
    @Select("select id,email,password,full_name as fullName,role,telephone" +
            ",register_ip as registerIp,last_login_ip as lastLoginIp " +
            "from user where email=#{email}")
    User findByEmail(String email);
    @Delete("delete from user where email=#{email}")
    int deleteUser(String email);
    @Update("update user set password=#{param1} where email=#{param2}")
    int updatePassword(String password,String email);
    @Update("update user set telephone=#{param1} where email=#{param2}")
    int updateTelephone(String telephone,String email);
    @Update("update user set full_name=#{param1} where email=#{param2}")
    int updateFullName(String fullName,String email);
    @Update("update user set last_login_ip=#{param1} where email=#{param2}")
    int updateLoginIp(String loginIp,String email);

}
