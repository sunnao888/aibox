package com.sunnao.aibox.module.biz.ai.agent.xiaohongshu;

import com.sunnao.aibox.module.biz.ai.options.xiaohongshu.XiaohongshuStoreVisitOptions;
import com.sunnao.aibox.module.biz.ai.prompt.xiaohongshu.XiaohongshuStoreVisitPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class XiaohongshuStoreVisitAgent {
    private final ChatModel chatModel;
    private static ChatClient chatClient;

    public String chat(String userMessage) {
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(XiaohongshuStoreVisitPrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(XiaohongshuStoreVisitOptions.TEMPERATURE)
                        .model(XiaohongshuStoreVisitOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("小红书探店文案生成完成，用户输入：{}，AI响应长度：{}", userMessage, response.length());
        return response;
    }
}