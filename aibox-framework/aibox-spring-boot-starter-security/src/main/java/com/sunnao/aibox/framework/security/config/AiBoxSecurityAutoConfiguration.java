package com.sunnao.aibox.framework.security.config;

import com.sunnao.aibox.framework.common.biz.system.oauth2.OAuth2TokenCommonApi;
import com.sunnao.aibox.framework.common.biz.system.permission.PermissionCommonApi;
import com.sunnao.aibox.framework.security.core.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import com.sunnao.aibox.framework.security.core.filter.TokenAuthenticationFilter;
import com.sunnao.aibox.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.sunnao.aibox.framework.security.core.handler.AuthenticationEntryPointImpl;
import com.sunnao.aibox.framework.security.core.service.SecurityFrameworkService;
import com.sunnao.aibox.framework.security.core.service.SecurityFrameworkServiceImpl;
import com.sunnao.aibox.framework.web.core.handler.GlobalExceptionHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Spring Security 自动配置类，主要用于相关组件的配置
 * <p>
 * 注意，不能和 {@link AiBoxWebSecurityConfigurerAdapter} 用一个，原因是会导致初始化报错。
 * 参见 <a href="https://stackoverflow.com/questions/53847050/spring-boot-delegatebuilder-cannot-be-null-on-autowiring-authenticationmanager">...</a> 文档。
 *
 * @author sunnao
 */
@AutoConfiguration
@AutoConfigureOrder(-1) // 目的：先于 Spring Security 自动配置，避免一键改包后，org.* 基础包无法生效
@EnableConfigurationProperties(SecurityProperties.class)
public class AiBoxSecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Spring Security 加密器
     * 考虑到安全性，这里采用 BCryptPasswordEncoder 加密器
     *
     * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password Encoding with Spring Security</a>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    /**
     * Token 认证过滤器 Bean
     */
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler,
                                                               OAuth2TokenCommonApi oauth2TokenApi) {
        return new TokenAuthenticationFilter(securityProperties, globalExceptionHandler, oauth2TokenApi);
    }

    @Bean("ss") // 使用 Spring Security 的缩写，方便使用
    public SecurityFrameworkService securityFrameworkService(PermissionCommonApi permissionApi) {
        return new SecurityFrameworkServiceImpl(permissionApi);
    }

    /**
     * 声明调用 {@link SecurityContextHolder#setStrategyName(String)} 方法，
     * 设置使用 {@link TransmittableThreadLocalSecurityContextHolderStrategy} 作为 Security 的上下文策略
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

}
