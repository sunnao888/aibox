package com.sunnao.aibox.module.biz.service.xiaohongshu;

import com.sunnao.aibox.module.biz.ai.agent.xiaohongshu.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XiaohongshuServiceImpl implements XiaohongshuService {
    
    private final XiaohongshuTopicAgent topicAgent;
    private final XiaohongshuNoteAgent noteAgent;
    private final XiaohongshuStoreVisitAgent storeVisitAgent;
    private final XiaohongshuProductAgent productAgent;
    private final XiaohongshuTitleAgent titleAgent;
    private final XiaohongshuAccountAgent accountAgent;
    private final XiaohongshuScoreAgent scoreAgent;

    @Override
    public String generateTopic(String userMessage) {
        return topicAgent.chat(userMessage);
    }

    @Override
    public String generateNote(String userMessage) {
        return noteAgent.chat(userMessage);
    }

    @Override
    public String generateStoreVisit(String userMessage) {
        return storeVisitAgent.chat(userMessage);
    }

    @Override
    public String generateProduct(String userMessage) {
        return productAgent.chat(userMessage);
    }

    @Override
    public String generateTitle(String userMessage) {
        return titleAgent.chat(userMessage);
    }

    @Override
    public String generateAccountPosition(String userMessage) {
        return accountAgent.chat(userMessage);
    }

    @Override
    public String scoreNote(String userMessage) {
        return scoreAgent.chat(userMessage);
    }
}