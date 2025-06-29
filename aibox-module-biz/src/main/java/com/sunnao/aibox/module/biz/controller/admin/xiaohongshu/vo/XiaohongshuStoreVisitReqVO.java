package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "管理后台 - 小红书探店文案 Request VO")
@Data
public class XiaohongshuStoreVisitReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "帮我写一篇关于网红咖啡店的探店文案")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}