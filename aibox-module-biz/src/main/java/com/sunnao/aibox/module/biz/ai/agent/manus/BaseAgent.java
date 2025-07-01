package com.sunnao.aibox.module.biz.ai.agent.manus;

import cn.hutool.core.util.StrUtil;
import com.sunnao.aibox.framework.common.exception.ServiceException;
import com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil;
import com.sunnao.aibox.module.biz.ai.agent.manus.config.AgentConfiguration;
import com.sunnao.aibox.module.biz.ai.agent.manus.context.ExecutionContext;
import com.sunnao.aibox.module.biz.ai.agent.manus.executor.AgentExecutor;
import com.sunnao.aibox.module.biz.ai.agent.manus.handler.StreamingResultHandler;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.enums.ErrorCodeConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * 基础智能体，管理基础属性和定义执行流程，不负责具体实现。
 * 重构后遵循单一职责原则，将配置、执行、流式处理等职责分离
 *
 * @author sunnao
 * @since 2025-06-28
 */
@Getter
@Slf4j
public abstract class BaseAgent implements AgentExecutor {

    /**
     * 智能体配置
     */
    @Setter(AccessLevel.PROTECTED)
    protected AgentConfiguration config;

    /**
     * 和智能体交互的客户端
     */
    @Setter(AccessLevel.PROTECTED)
    protected ChatClient chatClient;

    /**
     * 智能体状态管理器
     */
    @Setter(AccessLevel.PROTECTED)
    protected AgentStateManager stateManager;

    /**
     * 流式结果处理器
     */
    @Setter(AccessLevel.PROTECTED)
    protected StreamingResultHandler streamingHandler;

    /**
     * 构造函数，注入核心依赖
     *
     * @param stateManager     状态管理器
     * @param streamingHandler 流式处理器
     */
    protected BaseAgent(AgentStateManager stateManager, StreamingResultHandler streamingHandler) {
        this.stateManager = stateManager;
        this.streamingHandler = streamingHandler;
    }

    /**
     * 运行智能体执行任务
     *
     * @param userMessage 用户输入的提示词，不能为空或空白字符串
     * @return 执行结果消息列表，包含每个步骤的执行结果
     * @throws ServiceException 当用户提示词为空时抛出 USER_PROMPT_NOT_EXISTS
     * @throws ServiceException 当智能体正在运行时抛出 AGENT_IS_RUNNING
     * @throws ServiceException 当执行过程中发生系统错误时抛出相应错误码
     * @since 2025-06-28
     */
    public List<ResultMessage> run(String userMessage) {
        ExecutionContext context = ExecutionContext.createNormal();
        return execute(userMessage, context);
    }

    /**
     * 运行智能体并通过 SSE 实时推送结果
     *
     * @param userMessage 用户输入的提示词，不能为空或空白字符串
     * @param sseEmitter  SSE 发射器，用于实时推送结果
     * @throws ServiceException 当用户提示词为空时抛出 USER_PROMPT_NOT_EXISTS
     * @throws ServiceException 当智能体正在运行时抛出 AGENT_IS_RUNNING
     * @throws ServiceException 当执行过程中发生系统错误时抛出相应错误码
     * @since 2025-06-28
     */
    public void runWithSseEmitter(String userMessage, SseEmitter sseEmitter) {
        ExecutionContext context = ExecutionContext.createStreaming(sseEmitter);

        // 设置SSE回调
        streamingHandler.setupSseCallbacks(sseEmitter, config.getName(),
                context.getExecutionId(), () -> cleanup(context));

        try {
            execute(userMessage, context);
        } finally {
            streamingHandler.safeComplete(sseEmitter, context.getExecutionId());
        }
    }

    // ========== AgentExecutor 接口实现 ==========

    @Override
    public List<ResultMessage> execute(String userMessage, ExecutionContext context) {
        setupLoggingContext(context);

        try {

            validateInput(userMessage);
            initializeExecution(userMessage, context);
            List<ResultMessage> results = executeLoop(context);

            log.info("智能体任务执行完成 - 步数: {}, 结果数量: {}, 耗时: {}ms",
                    context.getCurrentStep(), results.size(), context.getElapsedTime());

            return results;

        } catch (ServiceException e) {
            // 业务异常，直接重新抛出
            log.warn("智能体 {} 执行业务异常: {}", config.getName(), e.getMessage());
            handleExecutionError(context, e.getMessage(), e.getCode());
            throw e;
        } catch (Exception e) {
            // 其他未知异常
            log.error("智能体 {} 执行未知异常", config.getName(), e);
            String errorMsg = "系统内部错误，任务被迫中断: " + e.getMessage();
            handleExecutionError(context, errorMsg, ErrorCodeConstants.AGENT_UNKNOWN_ERROR.getCode());
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AGENT_UNKNOWN_ERROR, e.getMessage());
        } finally {
            cleanup(context);
            clearLoggingContext();
        }
    }

    @Override
    public void validateInput(String userMessage) {
        if (StrUtil.isEmpty(userMessage)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_PROMPT_NOT_EXISTS);
        }
        if (stateManager.getState(config.getName()) == AgentState.RUNNING) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AGENT_IS_RUNNING);
        }

        // 验证配置
        if (config == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AGENT_CONFIG_INVALID, "配置为空");
        }
        config.validate();
    }

    @Override
    public void initializeExecution(String userMessage, ExecutionContext context) {
        String agentName = config.getName();

        // 设置智能体状态
        stateManager.setState(agentName, AgentState.RUNNING);

        // 清理并初始化记忆
        stateManager.clearMemory(agentName);
        stateManager.clearResult(agentName);
        stateManager.resetCurrentStep(agentName);
        stateManager.addMemory(agentName, new UserMessage(userMessage));

        log.debug("智能体 {} 初始化完成", agentName);
    }

    @Override
    public List<ResultMessage> executeLoop(ExecutionContext context) {
        String agentName = config.getName();
        int maxSteps = config.getMaxStep();

        // 缓存状态，减少方法调用
        int currentStep = stateManager.getCurrentStep(agentName);
        AgentState currentState = stateManager.getState(agentName);

        while (currentStep < maxSteps && currentState != AgentState.FINISHED) {
            currentStep++;
            stateManager.setCurrentStep(agentName, currentStep);
            context.incrementStep();

            log.debug("执行智能体 {} 第 {} 步", agentName, currentStep);

            // 执行具体步骤
            step();

            // 处理流式推送
            if (context.isStreaming()) {
                try {
                    List<ResultMessage> currentResults = stateManager.getResult(agentName);
                    streamingHandler.pushNewResults(context, currentResults);
                } catch (IOException e) {
                    log.error("推送流式结果失败", e);
                    throw new RuntimeException("流式推送失败", e);
                }
            }

            // 重新获取状态（可能在step()中被修改）
            currentState = stateManager.getState(agentName);
        }

        // 设置最终状态
        stateManager.setState(agentName, AgentState.FINISHED);
        context.setCompleted(true);

        return stateManager.getResult(agentName);
    }

    @Override
    public void cleanup(ExecutionContext context) {
        if (config != null) {
            String agentName = config.getName();
            try {
                // 清理状态（保留结果用于返回）
                stateManager.setState(agentName, AgentState.IDLE);
                log.debug("智能体 {} 资源清理完成", agentName);
            } catch (Exception e) {
                log.warn("智能体 {} 资源清理时发生异常", agentName, e);
            }
        }
    }

    // ========== 辅助方法 ==========

    /**
     * 具体的步骤执行逻辑，交给子类去实现
     */
    public abstract void step();

    /**
     * 设置日志上下文
     */
    private void setupLoggingContext(ExecutionContext context) {
        MDC.put("executionId", context.getExecutionId());
        if (config != null) {
            MDC.put("agentName", config.getName());
        }
    }

    /**
     * 清理日志上下文
     */
    private void clearLoggingContext() {
        MDC.clear();
    }

    /**
     * 处理执行错误
     */
    private void handleExecutionError(ExecutionContext context, String errorMessage, Integer errorCode) {
        if (config != null) {
            String agentName = config.getName();
            int currentStep = stateManager.getCurrentStep(agentName);

            // 添加错误结果
            ResultMessage errorResult = new ResultMessage(MessageType.SYSTEM, currentStep, errorMessage);
            stateManager.addResult(agentName, errorResult);

            // 推送错误结果（如果是流式执行）
            if (context.isStreaming()) {
                streamingHandler.pushErrorResult(context, errorMessage, currentStep);
            }

            // 设置错误状态
            stateManager.setState(agentName, AgentState.ERROR);
        }
    }

}
