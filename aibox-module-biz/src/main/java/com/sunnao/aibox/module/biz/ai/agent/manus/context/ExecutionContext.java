package com.sunnao.aibox.module.biz.ai.agent.manus.context;

import lombok.Data;
import lombok.Builder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

/**
 * 智能体执行上下文
 * 封装执行过程中的状态和配置信息
 *
 * @author sunnao
 * @since 2025-07-01
 */
@Data
@Builder
public class ExecutionContext {
    
    /**
     * 执行ID，用于日志追踪
     */
    @Builder.Default
    private String executionId = UUID.randomUUID().toString();
    
    /**
     * SSE发射器，用于流式推送（可选）
     */
    private SseEmitter sseEmitter;
    
    /**
     * 是否为流式执行
     */
    private boolean streaming;
    
    /**
     * 上次推送的结果数量（用于流式执行）
     */
    @Builder.Default
    private int lastResultCount = 0;
    
    /**
     * 执行开始时间
     */
    @Builder.Default
    private long startTime = System.currentTimeMillis();
    
    /**
     * 当前执行步数
     */
    @Builder.Default
    private int currentStep = 0;
    
    /**
     * 是否已完成
     */
    @Builder.Default
    private boolean completed = false;
    
    /**
     * 创建非流式执行上下文
     *
     * @return 执行上下文
     */
    public static ExecutionContext createNormal() {
        return ExecutionContext.builder()
                .streaming(false)
                .build();
    }
    
    /**
     * 创建流式执行上下文
     *
     * @param sseEmitter SSE发射器
     * @return 执行上下文
     */
    public static ExecutionContext createStreaming(SseEmitter sseEmitter) {
        return ExecutionContext.builder()
                .sseEmitter(sseEmitter)
                .streaming(true)
                .build();
    }
    
    /**
     * 获取执行耗时（毫秒）
     *
     * @return 执行耗时
     */
    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
    
    /**
     * 增加步数
     */
    public void incrementStep() {
        this.currentStep++;
    }
    
    /**
     * 更新最后推送的结果数量
     *
     * @param count 结果数量
     */
    public void updateLastResultCount(int count) {
        this.lastResultCount = count;
    }
}
