package com.sunnao.aibox.module.biz.service.work.report;

import com.sunnao.aibox.module.biz.controller.admin.work.vo.BrainStormingReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.MockInterviewReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.PPTReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import jakarta.validation.Valid;

/**
 * 工作效率 - 报告生成 Service 接口
 *
 * @author sunnao
 */
public interface WorkService {

    /**
     * 生成工作报告
     *
     * @param reqVO 报告生成请求参数
     * @return 生成的工作报告全文
     */
    String generateReport(@Valid ReportGenerateReqVO reqVO);

    /**
     * 头脑风暴
     *
     * @param reqVO 头脑风暴请求参数
     * @return 生成的头脑风暴内容
     */
    String brainStorming(@Valid BrainStormingReqVO reqVO);

    /**
     * 生成PPT大纲
     *
     * @param reqVO PPT大纲生成请求参数
     * @return 生成的PPT大纲内容
     */
    String ppt(@Valid PPTReqVO reqVO);

    /**
     * 模拟面试
     *
     * @param reqVO 模拟面试请求参数
     * @return 生成的模拟面试内容
     */
    String mockInterview(@Valid MockInterviewReqVO reqVO);
}
