package com.sunnao.aibox.module.biz.service.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.controller.admin.manus.vo.ManusReqVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface ManusService {

    List<ResultMessage> jManus(ManusReqVO reqVO);

    /**
     * JManus 智能助手 - SSE 流式响应
     *
     * @param reqVO 请求参数
     * @return SSE 发射器
     */
    SseEmitter jManusStream(ManusReqVO reqVO);

}
