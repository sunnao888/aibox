package com.sunnao.aibox.module.biz.ai.agent.manus.manager;

import com.sunnao.aibox.framework.security.core.util.SecurityFrameworkUtils;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.AgentState;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 智能体状态管理器
 * 集中管理所有智能体的用户状态
 *
 * @author sunnao
 */
@Component
public class AgentStateManager {

    // 使用复合键：agentName + userId
    private final ConcurrentHashMap<String, UserAgentState> stateCache = new ConcurrentHashMap<>();

    /**
     * 生成缓存键
     */
    private String generateKey(String agentName) {
        return agentName + ":" + SecurityFrameworkUtils.getLoginUserId();
    }

    /**
     * 获取或创建用户状态
     */
    public UserAgentState getUserState(String agentName) {
        String key = generateKey(agentName);
        return stateCache.computeIfAbsent(key, k -> new UserAgentState());
    }

    /**
     * 获取智能体状态
     */
    public AgentState getState(String agentName) {
        UserAgentState userState = getUserState(agentName);
        return userState.getState();
    }

    /**
     * 设置智能体状态
     */
    public void setState(String agentName, AgentState state) {
        UserAgentState userState = getUserState(agentName);
        userState.setState(state);
    }

    /**
     * 获取当前步骤
     */
    public int getCurrentStep(String agentName) {
        UserAgentState userState = getUserState(agentName);
        return userState.getCurrentStep();
    }

    /**
     * 设置当前步骤
     */
    public void setCurrentStep(String agentName, int currentStep) {
        UserAgentState userState = getUserState(agentName);
        userState.setCurrentStep(currentStep);
    }

    /**
     * 重置当前步骤
     */
    public void resetCurrentStep(String agentName) {
        UserAgentState userState = getUserState(agentName);
        userState.setCurrentStep(0);
    }

    /**
     * 添加结果消息
     */
    public void addResult(String agentName, ResultMessage message) {
        UserAgentState userState = getUserState(agentName);
        userState.getResults().add(message);
    }

    /**
     * 获取结果消息
     */
    public List<ResultMessage> getResult(String agentName) {
        UserAgentState userState = getUserState(agentName);
        return userState.getResults();
    }

    /**
     * 清理结果消息
     */
    public void clearResult(String agentName) {
        UserAgentState userState = getUserState(agentName);
        userState.getResults().clear();
    }

    public List<Message> getMemory(String agentName) {
        UserAgentState userState = getUserState(agentName);
        return userState.getMemory();
    }

    /**
     * 添加记忆
     */
    public void addMemory(String agentName, Message message) {
        UserAgentState userState = getUserState(agentName);
        userState.getMemory().add(message);
    }

    public void clearMemory(String agentName) {
        UserAgentState userState = getUserState(agentName);
        userState.getMemory().clear();
    }

}