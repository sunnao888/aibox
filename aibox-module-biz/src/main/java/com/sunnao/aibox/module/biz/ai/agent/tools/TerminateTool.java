package com.sunnao.aibox.module.biz.ai.agent.tools;

import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentName;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class TerminateTool {

    @Resource
    private AgentStateManager agentStateManager;

    @Tool(description = """  
            当请求被满足或者助手无法继续完成任务时，终止互动。
            当你完成所有任务后，调用这个工具来结束工作。
            """)
    public String doTerminate() {
        agentStateManager.setState(AgentName.JMANUS, AgentState.FINISHED);
        agentStateManager.addResult(AgentName.JMANUS, new ResultMessage(MessageType.SYSTEM, agentStateManager.getCurrentStep(AgentName.JMANUS), "调用终止工具，结束本次任务"));
        return "结束任务成功，请回复end";
    }
}
