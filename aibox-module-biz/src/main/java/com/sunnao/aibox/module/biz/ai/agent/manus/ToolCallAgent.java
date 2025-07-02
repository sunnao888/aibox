package com.sunnao.aibox.module.biz.ai.agent.manus;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.sunnao.aibox.module.biz.ai.agent.manus.handler.StreamingResultHandler;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;

@Getter
@Slf4j
public class ToolCallAgent extends ReActAgent {

    // 可用的工具列表
    private final ToolCallback[] availableTools;

    // 工具调用的响应
    private ChatResponse toolCallResponse;

    // 工具调用管理者
    private final ToolCallingManager toolCallingManager;

    // 调用llm传递的一些参数配置
    private final ChatOptions chatOptions = DashScopeChatOptions
            .builder()
            .withInternalToolExecutionEnabled(false)// 关闭Spring ai 工具托管
            .withEnableThinking(false) // 禁用推理
            .withTemperature(0.7)
            .build();

    public ToolCallAgent(ToolCallback[] availableTools, AgentStateManager stateManager, StreamingResultHandler streamingHandler) {
        super(stateManager, streamingHandler);
        this.availableTools = availableTools;
        this.toolCallingManager = ToolCallingManager.builder().build();
    }

    @Override
    public Boolean think() {
        String agentName = config.getName();

        // 校验提示词，有的话添加到记忆和系统提示词
        if (StrUtil.isNotBlank(config.getNextStepPrompt())) {
            stateManager.addMemory(agentName, new SystemMessage(config.getNextStepPrompt()));
        }

        // 调用llm并获取响应
        ChatResponse response = chatClient
                .prompt()
                .system(config.getSystemPrompt())
                .messages(stateManager.getMemory(agentName))
                .options(chatOptions)
                .toolCallbacks(availableTools)
                .call()
                .chatResponse();
        this.toolCallResponse = response;

        AssistantMessage assistantMessage = response.getResult().getOutput();
        String responseText = assistantMessage.getText();
        List<AssistantMessage.ToolCall> toolCalls = assistantMessage.getToolCalls();

        // 把响应添加到结果和记忆中
        if (StrUtil.isNotBlank(responseText) && !responseText.contains("end")) {
            stateManager.getResult(agentName).add(new ResultMessage(MessageType.AGENT, stateManager.getCurrentStep(agentName), responseText));
            stateManager.getMemory(agentName).add(new AssistantMessage(responseText));
        }

        // 需要调用工具，则行动，不需要调用工具，则结束
        return !toolCalls.isEmpty();
    }

    /**
     * 执行工具调用并处理结果
     */
    @Override
    public void act() {
        if (!toolCallResponse.hasToolCalls()) {
            return;
        }
        // 调用工具
        Prompt prompt = new Prompt(stateManager.getMemory(config.getName()), chatOptions);
        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, toolCallResponse);
        // 记录消息上下文，conversationHistory 已经包含了助手消息和工具调用返回的结果
        stateManager.addMemories(config.getName(), toolExecutionResult.conversationHistory());
        // 当前工具调用的结果
        ToolResponseMessage toolResponseMessage = (ToolResponseMessage) CollUtil.getLast(toolExecutionResult.conversationHistory());
        toolResponseMessage.getResponses().forEach(
                response -> {
                    String name = response.name();
                    stateManager.addResult(config.getName(),
                            new ResultMessage(MessageType.SYSTEM, stateManager.getCurrentStep(config.getName()), "调用" + name));
                }
        );
    }
}
