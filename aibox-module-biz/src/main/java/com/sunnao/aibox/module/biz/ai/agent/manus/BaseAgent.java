package com.sunnao.aibox.module.biz.ai.agent.manus;

import cn.hutool.core.util.StrUtil;
import com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.enums.ErrorCodeConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;

/**
 * 基础智能体，管理基础属性和定义执行流程，不负责具体实现。
 *
 * @author sunnao
 * @since 2025-06-28
 */
@Data
@Slf4j
public abstract class BaseAgent {

    // 智能体名称
    private String name;

    // 系统提示词
    private String systemPrompt;
    // 引导ai进行下一步骤的提示词
    private String nextStepPrompt;

    // 流程控制属性
    // 最大步骤数
    private int maxStep = 5;

    // 和智能体交互的客户端，其实这里不一定需要和 spring ai 强绑定，如果后续需要集成其他ai框架，这里需要修改。
    private ChatClient chatClient;

    // 智能体状态管理，需要子类set
    private AgentStateManager state;

    /**
     * 运行智能体
     *
     * @param userMessage 用户提示词
     */
    public List<ResultMessage> run(String userMessage) {

        try {
            // 1. 基础参数校验
            // 1.1 用户提示词不能为空
            if (StrUtil.isEmpty(userMessage)) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_PROMPT_NOT_EXISTS);
            }
            // 1.2 智能体状态校验-运行中的智能体不能再次运行
            if (state.getState(name) == AgentState.RUNNING) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.AGENT_IS_RUNNING);
            }

            // 2. 参数校验通过后进入分布执行流程
            // 2.1 首先设置智能体状态
            state.setState(name, AgentState.RUNNING);

            // 2.2 初始化记忆 1. 先清理记忆中残留的无效消息 2. 更新用户消息到记忆中
            this.cleanup();
            state.addMemory(name, new UserMessage(userMessage));

            // 2.3 进入执行循环 Agent Loop

            while (state.getCurrentStep(name) < maxStep) {
                // 2.3.1 状态校验，因为在真实的场景中，大部分情况下是不会达到最大执行步骤的，当智能体结束任务并且调用了终止工具时，要终止执行。
                if (state.getState(name) == AgentState.FINISHED) {
                    break;
                }
                // 2.3.2 更新参数
                state.setCurrentStep(name, state.getCurrentStep(name) + 1);
                // 2.3.3 执行步骤
                step();
            }

            // 3. 到这里说明智能体的任务已经执行完成，可能是执行成功或者是达到最大的执行步骤。接下来做一些后置操作
            // 3.1 设置智能体状态为已完成
            state.setState(name, AgentState.FINISHED);
            // 3.2 打印一下日志
            log.info("智能体 {} 执行完成，结果 {}", name, state.getResult(name));

            return state.getResult(name);
        } catch (Exception e) {
            log.error("智能体 {} 执行第 {} 步失败", name, state.getCurrentStep(name), e);
            state.addResult(name, new ResultMessage(MessageType.SYSTEM, state.getCurrentStep(name), "系统内部错误，执行流程被迫中断。"));
            return state.getResult(name);
        }
    }

    /**
     * 具体的步骤执行逻辑，交给子类去实现
     */
    public abstract void step();

    /**
     * 清理资源的逻辑，子类可以重写
     */
    public void cleanup() {
        this.state.clearMemory(name);
        this.state.clearResult(name);
        this.state.resetCurrentStep(name);
    }

}
