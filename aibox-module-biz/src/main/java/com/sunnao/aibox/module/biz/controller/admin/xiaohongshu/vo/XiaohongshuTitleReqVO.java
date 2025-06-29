package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书爆款标题 Request VO")
@Data
public class XiaohongshuTitleReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "我要写一篇关于减肥经验的笔记，帮我生成爆款标题")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}