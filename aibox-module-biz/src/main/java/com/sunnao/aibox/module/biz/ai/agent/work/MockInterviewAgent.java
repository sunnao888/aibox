package com.sunnao.aibox.module.biz.ai.agent.work;

import com.sunnao.aibox.module.biz.ai.options.work.MockInterviewOptions;
import com.sunnao.aibox.module.biz.ai.options.work.PPTOptions;
import com.sunnao.aibox.module.biz.ai.prompt.work.MockInterviewPrompt;
import com.sunnao.aibox.module.biz.ai.prompt.work.PPTPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

/**
 * 模拟面试智能体
 *
 * @author sunnao
 * @since 2025-06-29
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MockInterviewAgent {

    private final ChatModel chatModel;

    private static ChatClient chatClient;

    public String chat(String userMessage) {

        // 延迟初始化
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(MockInterviewPrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(MockInterviewOptions.TEMPERATURE)
                        .model(MockInterviewOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("""
                模拟面试测试
                userMessage: {}
                response: {}
                """, userMessage, response);

        return response;
    }
}
