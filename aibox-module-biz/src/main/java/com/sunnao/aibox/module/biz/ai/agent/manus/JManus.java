package com.sunnao.aibox.module.biz.ai.agent.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.config.AgentConfiguration;
import com.sunnao.aibox.module.biz.ai.agent.manus.handler.StreamingResultHandler;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentName;
import com.sunnao.aibox.module.biz.ai.prompt.manus.JManusPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

@Component
public class JManus extends ToolCallAgent {

    public JManus(ToolCallback[] allTools, ChatModel dashscopeChatModel,
                  AgentStateManager agentStateManager, StreamingResultHandler streamingHandler) {
        super(allTools, agentStateManager, streamingHandler);

        AgentConfiguration configuration = AgentConfiguration.builder()
                .name(AgentName.JMANUS)
                .systemPrompt(JManusPrompt.SYSTEM_PROMPT)
                .nextStepPrompt(JManusPrompt.NEXT_STEP_PROMPT)
                .maxStep(10)
                .build();

        this.setConfig(configuration);

        // 初始化客户端
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .build();
        this.setChatClient(chatClient);
    }
}
