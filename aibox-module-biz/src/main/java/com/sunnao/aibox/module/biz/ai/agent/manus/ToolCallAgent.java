package com.sunnao.aibox.module.biz.ai.agent.manus;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.ToolCallback;

@Data
@Slf4j
public class ToolCallAgent extends ReActAgent {

    // 可用的工具列表
    private final ToolCallback[] availableTools;

    // 调用llm传递的一些参数配置
    private final ChatOptions chatOptions = DashScopeChatOptions.builder().build();

    public ToolCallAgent(ToolCallback[] availableTools) {
        super();
        this.availableTools = availableTools;
    }

    @Override
    public Boolean think() {
        AgentStateManager state = getState();
        // 校验提示词，有的话添加到记忆和系统提示词
        if (StrUtil.isNotBlank(getNextStepPrompt())) {
            setSystemPrompt(String.join(getSystemPrompt(), "\n", getNextStepPrompt()));
            state.addMemory(getName(), new SystemMessage(getNextStepPrompt()));
        }

        // 调用llm并获取响应
        ChatResponse response = getChatClient()
                .prompt()
                .system(getSystemPrompt())
                .messages(state.getMemory(getName()))
                .options(chatOptions)
                .toolCallbacks(availableTools)
                .call()
                .chatResponse();

        AssistantMessage assistantMessage = response.getResult().getOutput();
        String responseText = assistantMessage.getText();

        // 把响应添加到结果和记忆中
        if (StrUtil.isNotBlank(responseText) && !responseText.contains("end")) {
            state.getResult(getName()).add(new ResultMessage(MessageType.AGENT, state.getCurrentStep(getName()), responseText));
            state.getMemory(getName()).add(new AssistantMessage(responseText));
        }
        // 因为思考-行动-观察步骤中思考-行动这一步Spring Ai帮助我们实现了，所以默认不需要我们手动控制是否行动。
        return false;
    }

    @Override
    public void act() {
    }
}
