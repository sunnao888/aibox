package com.sunnao.aibox.module.biz.ai.tools;

import com.alibaba.cloud.ai.toolcalling.baidusearch.BaiduSearchService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class BaiduSearchTool {

    @Resource
    private BaiduSearchService baiduSearchService;

    @Tool(description = """
            百度联网搜索工具
            """, name = "百度联网搜索工具")
    public BaiduSearchService.Response doSearch(@ToolParam(description = "搜索关键字") String keyword) {
        BaiduSearchService.Request request = new BaiduSearchService.Request(keyword, 5);
        return baiduSearchService.apply(request);
    }
}
