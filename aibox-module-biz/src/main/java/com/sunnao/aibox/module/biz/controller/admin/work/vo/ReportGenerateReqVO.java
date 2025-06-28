package com.sunnao.aibox.module.biz.controller.admin.work.vo;

import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "工作效率 - 报告生成 Request VO")
@Data
public class ReportGenerateReqVO {

    @Schema(description = "报告类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "日报")
    @NotBlank(message = "报告类型不能为空")
    @DiffLogField(name = "报告类型")
    private String type;

    @Schema(description = "模板id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "今天完成了项目A的需求分析")
    @DiffLogField(name = "模板id")
    private Long templateId;

    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析，提出了3个关键改进建议")
    @NotBlank(message = "用户消息不能为空")
    @DiffLogField(name = "用户消息")
    private String userMessage;

}
