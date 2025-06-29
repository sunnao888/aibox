package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 小红书选题灵感 Request VO")
@Data
public class XiaohongshuTopicReqVO {
    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "我想做美妆相关的内容，有什么好的选题推荐吗？")
    @NotBlank(message = "用户消息不能为空")
    private String userMessage;
}