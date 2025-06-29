package com.sunnao.aibox.module.biz.ai.agent.xiaohongshu;

import com.sunnao.aibox.module.biz.ai.options.xiaohongshu.XiaohongshuNoteOptions;
import com.sunnao.aibox.module.biz.ai.prompt.xiaohongshu.XiaohongshuNotePrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class XiaohongshuNoteAgent {
    private final ChatModel chatModel;
    private static ChatClient chatClient;

    public String chat(String userMessage) {
        if (chatClient == null) {
            chatClient = ChatClient.builder(chatModel).build();
        }

        String response = chatClient.prompt()
                .system(XiaohongshuNotePrompt.PROMPT_TEMPLATE)
                .user(userMessage)
                .options(ChatOptions.builder()
                        .temperature(XiaohongshuNoteOptions.TEMPERATURE)
                        .model(XiaohongshuNoteOptions.MODEL)
                        .build())
                .call()
                .content();

        log.info("小红书笔记生成完成，用户输入：{}，AI响应长度：{}", userMessage, response.length());
        return response;
    }
}