package com.sunnao.aibox.module.biz.service.work.report;

import com.sunnao.aibox.module.biz.ai.agent.work.ReportAgent;
import com.sunnao.aibox.module.biz.ai.options.work.ReportOptions;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.module.biz.service.template.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportAgent reportAgent;

    private final TemplateService templateService;

    @Override
    public String generateReport(ReportGenerateReqVO reqVO) {
        Long templateId = reqVO.getTemplateId();

        // 默认使用默认案例
        String inputCase = ReportOptions.DEFAULT_INPUT_CASE;
        String outputCase = ReportOptions.DEFAULT_OUTPUT_CASE;

        // 只有在模板ID不为空且能成功获取模板时，才覆盖默认值
        if (templateId != null) {
            TemplateDO template = templateService.getTemplate(templateId);
            if (template != null) {
                inputCase = template.getInput();
                outputCase = template.getOutput();
            }
        }

        return reportAgent.chat(reqVO.getType(), inputCase, outputCase, reqVO.getUserMessage());
    }


}
