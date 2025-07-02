package com.sunnao.aibox.module.biz.service.manus;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ManusService {

    /**
     * JManus 智能助手 - SSE 流式响应
     *
     * @param userMessage 请求参数
     * @return SSE 发射器
     */
    SseEmitter jManusStream(String userMessage);

}
