package com.sunnao.aibox.module.biz.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.sunnao.aibox.framework.excel.core.annotations.DictFormat;
import com.sunnao.aibox.framework.excel.core.convert.DictConvert;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagRespVO;

@Schema(description = "管理后台 - 模板 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TemplateRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20383")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户注册模板")
    @ExcelProperty("模板名称")
    private String name;

    @Schema(description = "模板类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "模板类型", converter = DictConvert.class)
    @DictFormat("biz_template_type")
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

    @Schema(description = "关联的标签列表")
    private List<TagRespVO> tags;

}