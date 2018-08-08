package com.uchain.meetingapp.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoomForm {
    @ApiModelProperty("会议室地址")
    @NotNull(message = "会议室地址不能为空")
    private String address;
    @ApiModelProperty("最大容纳人数")
    @NotNull(message = "最大容纳人数不能为空")
    private Integer maxContain;
}
