package com.sunnao.aibox.framework.quartz.config;

import com.alibaba.ttl.TtlRunnable;
import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务 Configuration
 */
@AutoConfiguration
@EnableAsync
public class AiBoxAsyncAutoConfiguration {

    @Bean
    public BeanPostProcessor threadPoolTaskExecutorBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
                if (!(bean instanceof ThreadPoolTaskExecutor executor)) {
                    return bean;
                }
                // 修改提交的任务，接入 TransmittableThreadLocal
                executor.setTaskDecorator(TtlRunnable::get);
                return executor;
            }

        };
    }

}
