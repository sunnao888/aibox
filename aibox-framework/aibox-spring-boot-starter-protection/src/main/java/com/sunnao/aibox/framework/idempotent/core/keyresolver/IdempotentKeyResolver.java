package com.sunnao.aibox.framework.idempotent.core.keyresolver;

import com.sunnao.aibox.framework.idempotent.core.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * 幂等 Key 解析器接口
 *
 * @author sunnao
 */
public interface IdempotentKeyResolver {

    /**
     * 解析一个 Key
     *
     * @param idempotent 幂等注解
     * @param joinPoint  AOP 切面
     * @return Key
     */
    String resolver(JoinPoint joinPoint, Idempotent idempotent);

}
