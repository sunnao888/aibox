package com.sunnao.aibox.module.biz.ai.agent.manus.executor;

import com.sunnao.aibox.module.biz.ai.agent.manus.context.ExecutionContext;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;

import java.util.List;

/**
 * 智能体执行器接口
 * 定义智能体执行的核心方法，支持普通执行和流式执行
 *
 * @author sunnao
 * @since 2025-07-01
 */
public interface AgentExecutor {
    
    /**
     * 执行智能体任务
     *
     * @param userMessage 用户输入消息
     * @param context 执行上下文
     * @return 执行结果列表
     */
    List<ResultMessage> execute(String userMessage, ExecutionContext context);
    
    /**
     * 验证输入参数
     *
     * @param userMessage 用户输入消息
     * @throws com.sunnao.aibox.framework.common.exception.ServiceException 当参数无效时
     */
    void validateInput(String userMessage);
    
    /**
     * 初始化执行环境
     *
     * @param userMessage 用户输入消息
     * @param context 执行上下文
     */
    void initializeExecution(String userMessage, ExecutionContext context);
    
    /**
     * 执行主循环
     *
     * @param context 执行上下文
     * @return 执行结果列表
     */
    List<ResultMessage> executeLoop(ExecutionContext context);
    
    /**
     * 清理执行环境
     *
     * @param context 执行上下文
     */
    void cleanup(ExecutionContext context);
}
