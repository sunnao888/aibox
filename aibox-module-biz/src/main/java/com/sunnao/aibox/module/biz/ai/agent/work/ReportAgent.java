package com.sunnao.aibox.module.biz.ai.agent.work;

import com.sunnao.aibox.module.biz.ai.agent.BaseAgent;
import com.sunnao.aibox.module.biz.ai.options.work.ReportOptions;
import com.sunnao.aibox.module.biz.ai.prompt.work.ReportPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 报告智能体,负责生成日报、周报、月报
 *
 * @author sunnao
 * @since 2025-06-28
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ReportAgent extends BaseAgent {

    private final ChatModel chatModel;

    private static ChatClient chatClient;

    public String chat(String type, String inputCase, String outputCase, String userMessage) {

        // 系统提示词组装
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().build())
                .template(ReportPrompt.PROMPT_TEMPLATE)
                .build();

        String systemPrompt = promptTemplate.render(Map.of(
                ReportOptions.KEY_TYPE, type,
                ReportOptions.KEY_INPUT_CASE, inputCase,
                ReportOptions.KEY_OUTPUT_CASE, outputCase));

        // 延迟初始化
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(ReportOptions.TEMPERATURE)
                        .model(ReportOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("""
                报告生成测试
                sysPrompt:{}
                userMessage: {}
                response: {}
                """, systemPrompt, userMessage, response);

        return response;
    }

}
