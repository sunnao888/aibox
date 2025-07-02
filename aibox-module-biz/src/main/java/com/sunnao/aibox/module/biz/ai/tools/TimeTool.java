package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.time.GetCurrentTimeByTimeZoneIdService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class TimeTool {

    @Resource
    private GetCurrentTimeByTimeZoneIdService timeService;

    @Tool(description = "获取指定城市的时间", name = "查询时间工具")
    public GetCurrentTimeByTimeZoneIdService.Response getCityTime(@ToolParam(description = "时区id, 比如 Asia/Shanghai")
                                                                  String timeZoneId) {
        return timeService.apply(new GetCurrentTimeByTimeZoneIdService.Request(timeZoneId));
    }

}
