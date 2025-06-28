package com.sunnao.aibox.module.biz.controller.admin.tag.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.sunnao.aibox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 标签分页 Request VO")
@Data
public class TagPageReqVO extends PageParam {

    @Schema(description = "名称", example = "张三")
    private String name;

    @Schema(description = "状态(0 禁用 1启用)", example = "2")
    private Boolean status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}