package com.sunnao.aibox.module.system.framework.web.config;

import com.sunnao.aibox.framework.swagger.config.AiBoxSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的 web 组件的 Configuration
 *
 * @author sunnao
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return AiBoxSwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
