package com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.sunnao.aibox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 模板标签关联分页 Request VO")
@Data
public class TemplateTagLinkPageReqVO extends PageParam {

    @Schema(description = "模板id", example = "4200")
    private Long templateId;

    @Schema(description = "标签id", example = "20762")
    private Long tagId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}