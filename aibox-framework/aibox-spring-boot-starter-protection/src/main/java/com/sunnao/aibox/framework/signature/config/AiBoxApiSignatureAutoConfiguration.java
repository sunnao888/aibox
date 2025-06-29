package com.sunnao.aibox.framework.signature.config;

import com.sunnao.aibox.framework.redis.config.AiBoxRedisAutoConfiguration;
import com.sunnao.aibox.framework.signature.core.aop.ApiSignatureAspect;
import com.sunnao.aibox.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author sunnao
 */
@AutoConfiguration(after = AiBoxRedisAutoConfiguration.class)
public class AiBoxApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

}
