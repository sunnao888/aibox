package com.sunnao.aibox.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 * @author sunnao
 */
@SpringBootApplication(scanBasePackages = {"${aibox.info.base-package}.server", "${aibox.info.base-package}.module"})
public class AiBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiBoxApplication.class, args);
    }

}
