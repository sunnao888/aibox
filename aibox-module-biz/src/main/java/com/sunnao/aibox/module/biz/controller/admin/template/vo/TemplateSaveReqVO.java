package com.sunnao.aibox.module.biz.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 模板新增/修改 Request VO")
@Data
public class TemplateSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "20383")
    private Long id;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户注册模板")
    @NotEmpty(message = "模板名称不能为空")
    private String name;

    @Schema(description = "模板类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "模板类型不能为空")
    private Integer type;

    @Schema(description = "输入用例", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "输入用例不能为空")
    private String input;

    @Schema(description = "输出用例", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "输出用例不能为空")
    private String output;

    @Schema(description = "标签ID列表", example = "[1, 2, 3]")
    private List<Long> tagIds;

}