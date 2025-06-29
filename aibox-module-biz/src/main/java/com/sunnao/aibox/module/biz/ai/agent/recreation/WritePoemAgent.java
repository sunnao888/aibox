package com.sunnao.aibox.module.biz.ai.agent.recreation;

import com.sunnao.aibox.module.biz.ai.options.recreation.WritePoemOptions;
import com.sunnao.aibox.module.biz.ai.prompt.recreation.WritePoemPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WritePoemAgent {

    private final ChatModel chatModel;

    private static ChatClient chatClient;

    public String chat(String userMessage) {

        // 延迟初始化
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(WritePoemPrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(WritePoemOptions.TEMPERATURE)
                        .model(WritePoemOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("""
                写诗测试
                userMessage: {}
                response: {}
                """, userMessage, response);

        return response;
    }
}
