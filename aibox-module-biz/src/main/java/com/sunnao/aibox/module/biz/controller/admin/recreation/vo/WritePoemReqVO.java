package com.sunnao.aibox.module.biz.controller.admin.recreation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "生活娱乐 - 写诗 Request VO")
@Data
public class WritePoemReqVO {

    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析，提出了3个关键改进建议")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;

}
