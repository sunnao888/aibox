package com.sunnao.aibox.module.biz.controller.admin.work.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "工作效率 - 头脑风暴 Request VO")
@Data
public class BrainStormingReqVO {

    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析，提出了3个关键改进建议")
    private String userMessage;
    
}
