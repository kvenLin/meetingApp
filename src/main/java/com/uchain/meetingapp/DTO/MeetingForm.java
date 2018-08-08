package com.uchain.meetingapp.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MeetingForm {
    @NotNull(message = "会议起始时间不能为空")
    @ApiModelProperty("会议起始时间")
    private Date startTime;
    @NotNull(message = "会议结束时间不能为空")
    @ApiModelProperty("会议结束时间")
    private Date endTime;
    @NotNull(message = "参与人数不能为空")
    @ApiModelProperty("参会人数")
    private Integer memberNum;
    @NotNull(message = "房间id不能为空")
    @ApiModelProperty("房间id号")
    private long roomId;
    @ApiModelProperty("会议主题")
    private String theme;
    @ApiModelProperty("会议说明")
    private String description;
    @ApiModelProperty("参会人员说明")
    private String member;
}
