package com.sunnao.aibox.module.infra.api.websocket;

import com.sunnao.aibox.framework.websocket.core.sender.WebSocketMessageSender;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * WebSocket 发送器的 API 实现类
 *
 * @author sunnao
 */
@Component
public class WebSocketSenderApiImpl implements WebSocketSenderApi {

    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, userId, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        webSocketMessageSender.send(sessionId, messageType, messageContent);
    }

}
