package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.baidusearch.BaiduSearchService;
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
public class BaiduSearchTool {

    @Resource
    private BaiduSearchService baiduSearchService;

    @Resource
    private AgentStateManager agentStateManager;

    @Resource
    private UserAgentNameManager userAgentNameManager;

    @Tool(description = """
            百度联网搜索工具
            """)
    public BaiduSearchService.Response doSearch(@ToolParam(description = "搜索关键字") String keyword) {
        agentStateManager.addResult(userAgentNameManager.get(),
                new ResultMessage(MessageType.SYSTEM,
                        agentStateManager.getCurrentStep(userAgentNameManager.get()), "调用百度搜索工具，参数: " + keyword));
        BaiduSearchService.Request request = new BaiduSearchService.Request(keyword, 5);
        return baiduSearchService.apply(request);
    }
}
