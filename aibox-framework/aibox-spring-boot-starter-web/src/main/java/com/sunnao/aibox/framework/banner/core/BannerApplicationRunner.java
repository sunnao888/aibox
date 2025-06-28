package com.sunnao.aibox.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author sunnao
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("""
                            
                            ----------------------------------------------------------
                            \t\
                            项目启动成功！
                            \t\
                            接口文档: \t{}\s
                            \t\
                            开发文档: \t{}\s
                            \t\
                            视频教程: \t{}\s
                            ----------------------------------------------------------""",
                    "https://doc.iocoder.cn/api-doc/",
                    "https://doc.iocoder.cn",
                    "https://t.zsxq.com/02Yf6M7Qn");

        });
    }

}
