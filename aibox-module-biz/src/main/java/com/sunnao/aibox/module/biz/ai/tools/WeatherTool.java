package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.weather.WeatherService;
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
public class WeatherTool {

    @Resource
    private WeatherService weatherService;

    @Resource
    private AgentStateManager agentStateManager;

    @Resource
    private UserAgentNameManager userAgentNameManager;

    @Tool(description = "获取指定经纬度的天气预报")
    public WeatherService.Response getWeatherForecastByLocation(@ToolParam(description = "查询的城市名称，比如：上海") String city,
                                                                @ToolParam(description = "查询天气预报的天数,最多查询3天内的天气预报") int days) {

        agentStateManager.addResult(userAgentNameManager.get(), new ResultMessage(MessageType.SYSTEM, agentStateManager.getCurrentStep(userAgentNameManager.get()), "调用获取天气预报工具，参数：城市=" + city + ", 天数=" + days));

        return weatherService.apply(new WeatherService.Request(city, days));
    }


}
