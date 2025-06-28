package com.sunnao.aibox.module.biz.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.sunnao.aibox.framework.excel.core.annotations.DictFormat;
import com.sunnao.aibox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateRespVO {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20383")
    @ExcelProperty("用户ID")
    private Long id;

    @Schema(description = "模板类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "模板类型", converter = DictConvert.class)
    @DictFormat("biz_template_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer type;

    @Schema(description = "输入用例", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("输入用例")
    private String input;

    @Schema(description = "输出用例", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("输出用例")
    private String output;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}