package com.sunnao.aibox.module.biz.ai.agent.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.handler.StreamingResultHandler;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import lombok.extern.slf4j.Slf4j;

/**
 * ReAct模式的智能体，简单来说就是赋予智能体思考-行动-观察的能力，具体怎么思考，怎么行动，交给子类去实现
 *
 * @author sunnao
 * @since 2025-06-30
 */
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 构造函数
     *
     * @param stateManager 状态管理器
     * @param streamingHandler 流式处理器
     */
    protected ReActAgent(AgentStateManager stateManager, StreamingResultHandler streamingHandler) {
        super(stateManager, streamingHandler);
    }

    /**
     * 思考
     */
    public abstract Boolean think();

    /**
     * 行动
     */
    public abstract void act();

    @Override
    public void step() {
        // 先思考，根据思考结果判断是否需要行动
        if (think()) {
            act();
        }
    }

}
