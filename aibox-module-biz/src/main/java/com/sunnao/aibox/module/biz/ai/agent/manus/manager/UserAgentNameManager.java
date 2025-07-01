package com.sunnao.aibox.module.biz.ai.agent.manus.manager;

import com.sunnao.aibox.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存用户当前使用的智能体名称
 *
 * @author sunnao
 * @since 2025-07-01
 */
@Component
public class UserAgentNameManager {

    private final ConcurrentHashMap<Long, String> userAgentNameCache = new ConcurrentHashMap<>();

    /**
     * 绑定用户和智能体名称
     */
    public void bind(String agentName) {
        userAgentNameCache.put(Objects.requireNonNull(SecurityFrameworkUtils.getLoginUserId()), agentName);
    }

    public String get() {
        return userAgentNameCache.get(Objects.requireNonNull(SecurityFrameworkUtils.getLoginUserId()));
    }

}
