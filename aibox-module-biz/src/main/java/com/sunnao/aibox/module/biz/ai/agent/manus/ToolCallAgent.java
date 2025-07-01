package com.sunnao.aibox.module.biz.ai.agent.manus;

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
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.ToolCallback;

@Getter
@Slf4j
public class ToolCallAgent extends ReActAgent {

    // 可用的工具列表
    private final ToolCallback[] availableTools;

    // 调用llm传递的一些参数配置
    private final ChatOptions chatOptions = DashScopeChatOptions.builder().build();

    public ToolCallAgent(ToolCallback[] availableTools, AgentStateManager stateManager, StreamingResultHandler streamingHandler) {
        super(stateManager, streamingHandler);
        this.availableTools = availableTools;
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

        AssistantMessage assistantMessage = response.getResult().getOutput();
        String responseText = assistantMessage.getText();

        // 把响应添加到结果和记忆中
        if (StrUtil.isNotBlank(responseText) && !responseText.contains("end")) {
            stateManager.getResult(agentName).add(new ResultMessage(MessageType.AGENT, stateManager.getCurrentStep(agentName), responseText));
            stateManager.getMemory(agentName).add(new AssistantMessage(responseText));
        }
        // 因为思考-行动-观察步骤中思考-行动这一步Spring Ai帮助我们实现了，所以默认不需要我们手动控制是否行动。
        return false;
    }

    @Override
    public void act() {
    }
}
