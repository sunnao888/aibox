package com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 模板标签关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateTagLinkRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31960")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "模板id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4200")
    @ExcelProperty("模板id")
    private Long templateId;

    @Schema(description = "标签id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20762")
    @ExcelProperty("标签id")
    private Long tagId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}