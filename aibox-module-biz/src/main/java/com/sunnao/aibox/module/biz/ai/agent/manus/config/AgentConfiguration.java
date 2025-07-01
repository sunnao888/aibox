package com.sunnao.aibox.module.biz.ai.agent.manus.config;

import lombok.Data;
import lombok.Builder;

/**
 * 智能体配置类
 * 封装智能体的基础配置信息，遵循单一职责原则
 *
 * @author sunnao
 * @since 2025-07-01
 */
@Data
@Builder
public class AgentConfiguration {
    
    /**
     * 默认最大执行步数
     */
    public static final int DEFAULT_MAX_STEPS = 5;
    
    /**
     * 最小允许的最大步数
     */
    public static final int MIN_MAX_STEPS = 1;
    
    /**
     * 最大允许的最大步数
     */
    public static final int MAX_MAX_STEPS = 50;
    
    /**
     * 智能体名称
     */
    private String name;
    
    /**
     * 系统提示词
     */
    private String systemPrompt;
    
    /**
     * 引导AI进行下一步骤的提示词
     */
    private String nextStepPrompt;
    
    /**
     * 最大步骤数
     */
    @Builder.Default
    private int maxStep = DEFAULT_MAX_STEPS;
    
    /**
     * 设置最大步数，带参数验证
     *
     * @param maxStep 最大步数
     * @throws IllegalArgumentException 当参数超出允许范围时
     */
    public void setMaxStep(int maxStep) {
        if (maxStep < MIN_MAX_STEPS || maxStep > MAX_MAX_STEPS) {
            throw new IllegalArgumentException(
                String.format("maxStep must be between %d and %d, but got %d", 
                    MIN_MAX_STEPS, MAX_MAX_STEPS, maxStep)
            );
        }
        this.maxStep = maxStep;
    }
    
    /**
     * 验证配置的完整性
     *
     * @throws IllegalStateException 当配置不完整时
     */
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Agent name cannot be null or empty");
        }
        if (systemPrompt == null || systemPrompt.trim().isEmpty()) {
            throw new IllegalStateException("System prompt cannot be null or empty");
        }
    }
}
