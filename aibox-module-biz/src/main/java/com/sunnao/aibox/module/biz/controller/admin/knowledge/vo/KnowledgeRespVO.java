package com.sunnao.aibox.module.biz.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.sunnao.aibox.framework.excel.core.annotations.DictFormat;
import com.sunnao.aibox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 知识库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class KnowledgeRespVO {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25974")
    @ExcelProperty("用户ID")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("内容")
    private String context;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}