package com.sunnao.aibox.module.biz.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 知识库新增/修改 Request VO")
@Data
public class KnowledgeSaveReqVO {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25974")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标题不能为空")
    private String title;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "内容不能为空")
    private String context;

}