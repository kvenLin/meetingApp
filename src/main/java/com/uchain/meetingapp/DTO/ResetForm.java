package com.uchain.meetingapp.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetForm {
    @NotNull(message = "邮箱号不能为空")
    private String email;
    @NotNull(message = "原密码不能为空")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    private String newPassword;

}
