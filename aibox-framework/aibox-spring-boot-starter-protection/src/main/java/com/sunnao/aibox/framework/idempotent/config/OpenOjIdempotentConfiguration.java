package com.sunnao.aibox.framework.idempotent.config;

import com.sunnao.aibox.framework.idempotent.core.aop.IdempotentAspect;
import com.sunnao.aibox.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.sunnao.aibox.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.sunnao.aibox.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.sunnao.aibox.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import com.sunnao.aibox.framework.idempotent.core.redis.IdempotentRedisDAO;
import com.sunnao.aibox.framework.redis.config.OpenOjRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = OpenOjRedisAutoConfiguration.class)
public class OpenOjIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
