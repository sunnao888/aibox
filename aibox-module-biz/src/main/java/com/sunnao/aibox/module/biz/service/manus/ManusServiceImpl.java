package com.sunnao.aibox.module.biz.service.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.JManus;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.UserAgentNameManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentName;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ManusServiceImpl implements ManusService {

    @Resource
    private JManus jManus;

    @Resource
    private UserAgentNameManager userAgentNameManager;

    @Override
    public SseEmitter jManusStream(String userMessage) {

        // 绑定用户和智能体名称
        userAgentNameManager.bind(AgentName.JMANUS);

        // 创建 SSE 发射器，设置超时时间为 10 分钟
        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);

        // 异步执行智能体任务
        CompletableFuture.runAsync(() -> {
            try {
                // 使用 BaseAgent 的新 SSE 方法执行任务
                jManus.runWithSseEmitter(userMessage, emitter);
                // 完成 SSE 连接
                emitter.complete();
            } catch (Exception e) {
                log.error("JManus 流式任务执行失败", e);
                try {
                    emitter.send(SseEmitter.event()
                            .name("error")
                            .data("{\"message\":\"任务执行失败: " + e.getMessage() + "\"}"));
                } catch (IOException ioException) {
                    log.error("发送错误事件失败", ioException);
                }
                emitter.completeWithError(e);
            }
        });

        // 设置连接关闭和超时的回调
        emitter.onCompletion(() -> log.info("SSE 连接正常关闭"));
        emitter.onTimeout(() -> log.warn("SSE 连接超时"));
        emitter.onError(throwable -> log.error("SSE 连接发生错误", throwable));

        return emitter;
    }
}
