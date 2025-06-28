package com.sunnao.aibox.module.biz.service.work.report;

import com.sunnao.aibox.module.biz.ai.agent.work.ReportAgent;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportAgent reportAgent;

    @Override
    public String generateReport(ReportGenerateReqVO reqVO) {
        return reportAgent.chat(reqVO.getType(), reqVO.getInputCase(), reqVO.getOutputCase(), reqVO.getUserMessage());
    }


}
