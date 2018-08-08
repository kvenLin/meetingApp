package com.uchain.meetingapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Room {
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty("房间的地址")
    @NotNull(message = "地址不能为空")
    private String address;
    @ApiModelProperty("最大可容纳的人数")
    @NotNull(message = "最大可容纳的人数不能为空")
    private Integer maxContain;
}
