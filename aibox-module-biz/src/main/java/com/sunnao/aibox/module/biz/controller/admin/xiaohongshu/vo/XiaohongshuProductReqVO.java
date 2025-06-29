package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书种草文案 Request VO")
@Data
public class XiaohongshuProductReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "帮我写一篇关于戴森吹风机的种草文案")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}