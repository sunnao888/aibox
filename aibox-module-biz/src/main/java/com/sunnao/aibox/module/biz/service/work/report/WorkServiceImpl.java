package com.sunnao.aibox.module.biz.service.work.report;

import com.sunnao.aibox.framework.security.core.util.SecurityFrameworkUtils;
import com.sunnao.aibox.module.biz.ai.agent.work.BrainStormingAgent;
import com.sunnao.aibox.module.biz.ai.agent.work.PPTAgent;
import com.sunnao.aibox.module.biz.ai.agent.work.ReportAgent;
import com.sunnao.aibox.module.biz.ai.options.work.ReportOptions;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.BrainStormingReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.PPTReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.module.biz.dal.redis.recommend.RecommendTemplateRedisDAO;
import com.sunnao.aibox.module.biz.service.template.TemplateService;
import com.sunnao.aibox.module.biz.service.templatetaglink.TemplateTagLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final ReportAgent reportAgent;

    private final BrainStormingAgent brainStormingAgent;

    private final PPTAgent pptAgent;

    private final TemplateService templateService;

    private final TemplateTagLinkService templateTagLinkService;

    private final RecommendTemplateRedisDAO recommendTemplateRedisDAO;

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
                List<Long> tagIds = templateTagLinkService.getTagIdsByTemplateId(templateId);
                recommendTemplateRedisDAO.incrementTagScores(SecurityFrameworkUtils.getLoginUserId(), tagIds);
                recommendTemplateRedisDAO.addUserLastTemplate(SecurityFrameworkUtils.getLoginUserId(), templateId);
            }
        }

        return reportAgent.chat(reqVO.getType(), inputCase, outputCase, reqVO.getUserMessage());
    }

    @Override
    public String brainStorming(BrainStormingReqVO reqVO) {
        return brainStormingAgent.chat(reqVO.getUserMessage());
    }

    @Override
    public String ppt(PPTReqVO reqVO) {
        return pptAgent.chat(reqVO.getUserMessage());
    }
}
