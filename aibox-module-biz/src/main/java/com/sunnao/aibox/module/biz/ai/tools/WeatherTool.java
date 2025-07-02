package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.weather.WeatherService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class WeatherTool {

    @Resource
    private WeatherService weatherService;

    @Tool(description = "获取指定经纬度的天气预报", name = "天气工具")
    public WeatherService.Response getWeatherForecastByLocation(@ToolParam(description = "查询的城市名称，比如：上海") String city,
                                                                @ToolParam(description = "查询天气预报的天数,最多查询3天内的天气预报") int days) {
        return weatherService.apply(new WeatherService.Request(city, days));
    }


}
