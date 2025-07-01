package com.sunnao.aibox.module.biz.ai.agent.manus.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunnao.aibox.module.biz.ai.agent.manus.context.ExecutionContext;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * 流式结果处理器
 * 负责处理SSE流式推送逻辑，遵循单一职责原则
 *
 * @author sunnao
 * @since 2025-07-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingResultHandler {

    private final ObjectMapper objectMapper;

    /**
     * 推送新增的结果到SSE客户端
     *
     * @param context 执行上下文
     * @param results 当前所有结果
     * @throws IOException 当推送失败时
     */
    public void pushNewResults(ExecutionContext context, List<ResultMessage> results) throws IOException {
        if (!context.isStreaming() || context.getSseEmitter() == null) {
            return;
        }

        SseEmitter emitter = context.getSseEmitter();
        int lastCount = context.getLastResultCount();

        if (results.size() > lastCount) {
            // 推送新增的结果
            for (int i = lastCount; i < results.size(); i++) {
                ResultMessage newResult = results.get(i);
                pushSingleResult(emitter, newResult, context.getExecutionId());
            }
            context.updateLastResultCount(results.size());
        }
    }

    /**
     * 推送单个结果
     *
     * @param emitter     SSE发射器
     * @param result      结果消息
     * @param executionId 执行ID
     * @throws IOException 当推送失败时
     */
    private void pushSingleResult(SseEmitter emitter, ResultMessage result, String executionId) throws IOException {
        try {
            String jsonData = objectMapper.writeValueAsString(result);
            emitter.send(SseEmitter.event()
                    .name("result")
                    .id(executionId + "-" + result.getStep())
                    .data(jsonData));

            log.debug("推送结果成功 - 执行ID: {}, 步骤: {}, 类型: {}",
                    executionId, result.getStep(), result.getType());

        } catch (IOException e) {
            log.error("推送SSE结果失败 - 执行ID: {}, 步骤: {}", executionId, result.getStep(), e);
            throw e;
        }
    }

    /**
     * 推送错误结果
     *
     * @param context      执行上下文
     * @param errorMessage 错误消息
     * @param currentStep  当前步骤
     */
    public void pushErrorResult(ExecutionContext context, String errorMessage, int currentStep) {
        if (!context.isStreaming() || context.getSseEmitter() == null) {
            return;
        }

        try {
            ResultMessage errorResult = new ResultMessage(MessageType.SYSTEM, currentStep, errorMessage);
            pushSingleResult(context.getSseEmitter(), errorResult, context.getExecutionId());
        } catch (IOException e) {
            log.error("推送错误结果失败 - 执行ID: {}", context.getExecutionId(), e);
        }
    }

    /**
     * 设置SSE连接的回调处理
     *
     * @param emitter         SSE发射器
     * @param agentName       智能体名称
     * @param executionId     执行ID
     * @param cleanupCallback 清理回调
     */
    public void setupSseCallbacks(SseEmitter emitter, String agentName, String executionId, Runnable cleanupCallback) {
        emitter.onTimeout(() -> {
            log.warn("SSE连接超时 - Agent: {}, 执行ID: {}", agentName, executionId);
            if (cleanupCallback != null) {
                cleanupCallback.run();
            }
        });

        emitter.onCompletion(() -> {
            log.debug("SSE连接完成 - Agent: {}, 执行ID: {}", agentName, executionId);
            if (cleanupCallback != null) {
                cleanupCallback.run();
            }
        });

        emitter.onError((ex) -> {
            log.error("SSE连接错误 - Agent: {}, 执行ID: {}", agentName, executionId, ex);
            if (cleanupCallback != null) {
                cleanupCallback.run();
            }
        });
    }

    /**
     * 安全关闭SSE连接
     *
     * @param emitter     SSE发射器
     * @param executionId 执行ID
     */
    public void safeComplete(SseEmitter emitter, String executionId) {
        if (emitter != null) {
            try {
                emitter.complete();
                log.debug("SSE连接已关闭 - 执行ID: {}", executionId);
            } catch (Exception e) {
                log.warn("关闭SSE连接时发生异常 - 执行ID: {}", executionId, e);
            }
        }
    }
}
