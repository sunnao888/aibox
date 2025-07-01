package com.sunnao.aibox.module.biz.ai.agent.manus;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * ReAct模式的智能体，简单来说就是赋予智能体思考-行动-观察的能力，具体怎么思考，怎么行动，交给子类去实现
 *
 * @author sunnao
 * @since 2025-06-30
 */
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

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
