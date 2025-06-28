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

    @Schema(description = "输入示例", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析")
    @NotBlank(message = "输入示例不能为空")
    @DiffLogField(name = "输入示例")
    private String inputCase;

    @Schema(description = "输出示例", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析，提出了3个关键改进建议")
    @NotBlank(message = "输出示例不能为空")
    @DiffLogField(name = "输出示例")
    private String outputCase;

    @Schema(description = "用户消息", requiredMode = Schema.RequiredMode.REQUIRED, example = "今天完成了项目A的需求分析，提出了3个关键改进建议")
    @NotBlank(message = "用户消息不能为空")
    @DiffLogField(name = "用户消息")
    private String userMessage;

}
