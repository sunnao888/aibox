package com.sunnao.aibox.module.biz.ai.agent.manus;

import com.sunnao.aibox.module.biz.ai.agent.manus.config.AgentConfiguration;
import com.sunnao.aibox.module.biz.ai.agent.manus.context.ExecutionContext;
import com.sunnao.aibox.module.biz.ai.agent.manus.handler.StreamingResultHandler;
import com.sunnao.aibox.module.biz.ai.agent.manus.manager.AgentStateManager;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * BaseAgent 测试类
 * 验证重构后的代码结构是否正确
 */
@ExtendWith(MockitoExtension.class)
public class BaseAgentTest {

    @Mock
    private AgentStateManager stateManager;

    @Mock
    private StreamingResultHandler streamingHandler;

    /**
     * 测试用的 BaseAgent 实现
     */
    private static class TestAgent extends BaseAgent {
        
        public TestAgent(AgentStateManager stateManager, StreamingResultHandler streamingHandler) {
            super(stateManager, streamingHandler);
        }

        @Override
        public void step() {
            // 测试实现
        }
    }

    @Test
    public void testAgentConfigurationBuilder() {
        // 测试配置构建器
        AgentConfiguration config = AgentConfiguration.builder()
                .name("TestAgent")
                .systemPrompt("Test system prompt")
                .nextStepPrompt("Test next step prompt")
                .maxStep(5)
                .build();
        
        assert config.getName().equals("TestAgent");
        assert config.getMaxStep() == 5;
    }

    @Test
    public void testExecutionContext() {
        // 测试执行上下文
        ExecutionContext context = ExecutionContext.createNormal();
        assert !context.isStreaming();
        assert context.getCurrentStep() == 0;
        
        context.incrementStep();
        assert context.getCurrentStep() == 1;
    }

    @Test
    public void testAgentCreation() {
        // 测试智能体创建
        TestAgent agent = new TestAgent(stateManager, streamingHandler);
        assert agent.getStateManager() == stateManager;
        assert agent.getStreamingHandler() == streamingHandler;
    }
}
