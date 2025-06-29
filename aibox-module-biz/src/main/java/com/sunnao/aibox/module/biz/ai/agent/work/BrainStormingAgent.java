package com.sunnao.aibox.module.biz.ai.agent.work;

import com.sunnao.aibox.module.biz.ai.options.work.ReportOptions;
import com.sunnao.aibox.module.biz.ai.prompt.work.BrainStormingPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

/**
 * 头脑风暴智能体
 *
 * @author sunnao
 * @since 2025-06-29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BrainStormingAgent {

    private final ChatModel chatModel;

    private static ChatClient chatClient;

    public String chat(String userMessage) {

        // 延迟初始化
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(BrainStormingPrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(ReportOptions.TEMPERATURE)
                        .model(ReportOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("""
                头脑风暴测试
                userMessage: {}
                response: {}
                """, userMessage, response);

        return response;
    }
}
