package com.sunnao.aibox.module.biz.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 标签 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TagRespVO {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14589")
    @ExcelProperty("用户ID")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "状态(0 禁用 1启用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态(0 禁用 1启用)")
    private Boolean status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}