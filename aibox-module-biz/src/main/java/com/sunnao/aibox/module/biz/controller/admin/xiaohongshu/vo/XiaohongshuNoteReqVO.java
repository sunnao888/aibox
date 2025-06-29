package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书笔记助手 Request VO")
@Data
public class XiaohongshuNoteReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "帮我写一篇关于夏日防晒的小红书笔记")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}