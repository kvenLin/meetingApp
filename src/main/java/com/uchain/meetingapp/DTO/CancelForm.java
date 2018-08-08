package com.uchain.meetingapp.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CancelForm {
    @NotNull(message = "meetingId不能为空")
    @ApiModelProperty("会议预约Id")
    private long meetingId;
    @NotNull(message = "取消原因不能为空")
    @ApiModelProperty("取消原因")
    private String reason;
}
