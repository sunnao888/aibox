package com.sunnao.aibox.module.infra.framework.web.config;

import com.sunnao.aibox.framework.swagger.config.OpenOjSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra 模块的 web 组件的 Configuration
 *
 * @author sunnao
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return OpenOjSwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
