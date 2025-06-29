package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书账号定位 Request VO")
@Data
public class XiaohongshuAccountReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "我是一名健身教练，想在小红书上分享健身知识，如何定位我的账号？")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}