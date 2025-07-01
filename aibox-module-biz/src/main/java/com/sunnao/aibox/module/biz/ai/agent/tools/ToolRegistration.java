package com.sunnao.aibox.module.biz.ai.agent.tools;

import jakarta.annotation.Resource;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolRegistration {

    @Resource
    private TerminateTool terminateTool;

    @Bean
    public ToolCallback[] allTools() {
        return ToolCallbacks.from(
                terminateTool
        );
    }
}
