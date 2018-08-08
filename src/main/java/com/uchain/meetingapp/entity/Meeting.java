package com.uchain.meetingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Meeting {
    @NotNull(message = "会议Id不能为空")
    @ApiModelProperty("会议Id,更新必填")
    private Long id;
    @NotNull(message = "起始时间不能为空")
    @ApiModelProperty("起始时间,更新必填")
    private Date startTime;
    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty("结束时间,更新必填")
    private Date endTime;
    @NotNull(message = "参与人数不能为空")
    @ApiModelProperty("参会人数,更新必填")
    private Integer memberNum;
    @NotNull(message = "房间id不能为空")
    @ApiModelProperty("房间唯一标识Id,更新必填")
    private Long roomId;
    @ApiModelProperty("会议主题")
    private String theme;
    @ApiModelProperty("会议说明")
    private String description;
    @ApiModelProperty("参与成员")
    private String member;
    private Integer status;//1.已预约,2.已取消
    private String cancelReason;//取消原因
    private long createdBy;
    @JsonIgnore
    private long updatedBy;
}
