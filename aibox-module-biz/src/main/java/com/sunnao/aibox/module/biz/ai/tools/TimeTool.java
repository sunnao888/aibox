package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.time.GetCurrentTimeByTimeZoneIdService;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.UserAgentNameManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.MessageType;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class TimeTool {

    @Resource
    private GetCurrentTimeByTimeZoneIdService timeService;

    @Resource
    private AgentStateManager agentStateManager;

    @Resource
    private UserAgentNameManager userAgentNameManager;

    @Tool(description = "获取指定城市的时间")
    public GetCurrentTimeByTimeZoneIdService.Response getCityTime(@ToolParam(description = "时区id, 比如 Asia/Shanghai")
                                                                  String timeZoneId) {
        agentStateManager.addResult(userAgentNameManager.get(), new ResultMessage(MessageType.SYSTEM, agentStateManager.getCurrentStep(userAgentNameManager.get()), "调用获取城市时间工具，参数：" + timeZoneId));
        return timeService.apply(new GetCurrentTimeByTimeZoneIdService.Request(timeZoneId));
    }

}
