package com.sunnao.aibox.module.biz.ai.tools;

import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.UserAgentNameManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class TerminateTool {

    @Resource
    private AgentStateManager agentStateManager;

    @Resource
    private UserAgentNameManager userAgentNameManager;

    @Tool(description = """  
            当请求被满足或者助手无法继续完成任务时，终止互动。
            当你完成所有任务后，调用这个工具来结束工作。
            """, name = "终止工具")
    public String doTerminate() {
        agentStateManager.setState(userAgentNameManager.get(), AgentState.FINISHED);
        return "结束任务成功";
    }
}
