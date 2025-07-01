
package com.sunnao.aibox.module.biz.ai.agent.manus.manager;

import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户智能体状态
 * 封装用户与智能体交互的核心状态信息
 *
 * @author sunnao
 * @since 2025-06-30
 */
@Data
public class UserAgentState implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 智能体运行状态
     */
    private AgentState state = AgentState.IDLE;

    /**
     * 当前步骤
     */
    private int currentStep = 0;

    /**
     * 对话记忆
     */
    private List<Message> memory = new ArrayList<>();

    /**
     * 执行结果
     */
    private List<ResultMessage> results = new ArrayList<>();

}