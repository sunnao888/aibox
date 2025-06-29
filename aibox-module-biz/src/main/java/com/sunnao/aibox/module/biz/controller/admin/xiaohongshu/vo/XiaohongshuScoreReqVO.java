package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书笔记评分 Request VO")
@Data
public class XiaohongshuScoreReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "请评分这篇笔记：标题：懒人必备！5分钟搞定的早餐合集...")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}