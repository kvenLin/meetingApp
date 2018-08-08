package com.uchain.meetingapp.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterForm {
    @ApiModelProperty("用户姓名")
    private String fullName;
    @NotNull(message = "邮箱号不能为空")
    @ApiModelProperty("邮箱号,必填")
    private String email;
    @NotNull(message = "密码不能为空")
    @ApiModelProperty("密码,必填")
    private String password;
    @ApiModelProperty("手机号")
    private String telephone;
    @NotNull(message = "验证码不能为空")
    @ApiModelProperty("验证码,必填")
    private String verifyCode;
}
