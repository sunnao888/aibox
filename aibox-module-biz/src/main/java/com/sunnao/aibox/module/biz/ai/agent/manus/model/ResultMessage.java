package com.sunnao.aibox.module.biz.ai.agent.manus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 步骤数
     */
    private int step;

    /**
     * 结果
     */
    private String result;

}
