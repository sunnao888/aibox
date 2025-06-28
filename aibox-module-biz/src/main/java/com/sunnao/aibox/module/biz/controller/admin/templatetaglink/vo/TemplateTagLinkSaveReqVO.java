package com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 模板标签关联新增/修改 Request VO")
@Data
public class TemplateTagLinkSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31960")
    private Long id;

    @Schema(description = "模板id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4200")
    @NotNull(message = "模板id不能为空")
    private Long templateId;

    @Schema(description = "标签id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20762")
    @NotNull(message = "标签id不能为空")
    private Long tagId;

}