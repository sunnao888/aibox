package com.sunnao.aibox.module.biz.ai.agent.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

@Component
public class JManus extends ToolCallAgent {

    public JManus(ToolCallback[] allTools, ChatModel dashscopeChatModel, AgentStateManager agentStateManager) {
        super(allTools);
        this.setName(AgentName.JMANUS);
        String SYSTEM_PROMPT = """  
                你是JManus，一个全能的AI助手，目标是解决用户提出的任何任务。你有各种各样的工具可以使用，能够高效地完成复杂的请求。
                """;
        this.setSystemPrompt(SYSTEM_PROMPT);
        String NEXT_STEP_PROMPT = """  
                根据用户的需求，主动选择最合适的工具或工具组合。
                遇到复杂任务时，可以把问题拆分开来，分步骤用不同的工具来解决。
                每用完一个工具，都要清楚地说明执行结果，并给出下一步建议。
                如果你想在任何时候结束互动，可以使用 `terminate` 这个工具或函数调用。
                """;
        this.setNextStepPrompt(NEXT_STEP_PROMPT);
        this.setMaxStep(10);
        // 初始化客户端  
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .build();
        this.setChatClient(chatClient);
        this.setState(agentStateManager);
    }
}
