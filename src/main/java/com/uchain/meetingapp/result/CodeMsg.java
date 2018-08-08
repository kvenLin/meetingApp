package com.uchain.meetingapp.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  CodeMsg {
    USER_NOT_EXIST(400,"用户不存在"),
    AUTHENTICATION_ERROR(401,"身份认证失败"),
    PASSWORD_ERROR(402,"密码错误"),
    PERMISSION_DENY(403,"越权操作"),
    SERVER_ERROR(500,"系统错误"),
    SEND_EMAIL_ERROR(501,"邮件服务异常,请稍后再试"),
    VERIFY_CODE_ERROR(502,"验证码错误"),
    EMAIL_REGISTERED(503,"邮箱已被注册"),
    BIND_ERROR(504,"参数检验错误: %s"),
    UPDATE_ERROR(505,"数据库更新异常"),
    INSERT_ERROR(506,"数据库插入异常"),
    DELETE_ERROR(507,"数据库删除异常"),
    DATA_NOT_EXIST(508,"数据不存在"),
    EMAIL_PATTERN_ERROR(509,"邮箱格式错误"),
    MEETING_TIME_CONFLICT(510,"会议时间冲突"),
    ;
    private Integer code;
    private String msg;
}
