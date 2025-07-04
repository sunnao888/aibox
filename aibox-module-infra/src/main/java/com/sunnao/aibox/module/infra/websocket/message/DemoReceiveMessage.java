package com.sunnao.aibox.module.infra.websocket.message;

import lombok.Data;

/**
 * 示例：server -> client 同步消息
 *
 * @author sunnao
 */
@Data
public class DemoReceiveMessage {

    /**
     * 接收人的编号
     */
    private Long fromUserId;
    /**
     * 内容
     */
    private String text;

    /**
     * 是否单聊
     */
    private Boolean single;

}
