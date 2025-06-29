package com.sunnao.aibox.module.biz.ai.agent.xiaohongshu;

import com.sunnao.aibox.module.biz.ai.options.xiaohongshu.XiaohongshuTitleOptions;
import com.sunnao.aibox.module.biz.ai.prompt.xiaohongshu.XiaohongshuTitlePrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class XiaohongshuTitleAgent {
    private final ChatModel chatModel;
    private static ChatClient chatClient;

    public String chat(String userMessage) {
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(XiaohongshuTitlePrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(XiaohongshuTitleOptions.TEMPERATURE)
                        .model(XiaohongshuTitleOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("小红书爆款标题生成完成，用户输入：{}，AI响应长度：{}", userMessage, response.length());
        return response;
    }
}