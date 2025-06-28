package com.sunnao.aibox.module.biz.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "工作效率 - 模板推荐 Request VO")
@Data
public class TemplateRecommendReqVO {

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "程序员")
    private String name;

    @Schema(description = "模板类型", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "标签名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "编程")
    private List<Long> tagIds;

}
