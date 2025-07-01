package com.sunnao.aibox.module.biz.controller.admin.manus;

import com.sunnao.aibox.module.biz.controller.admin.manus.vo.ManusReqVO;
import com.sunnao.aibox.module.biz.service.manus.ManusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "通用助理")
@RestController
@RequestMapping("/biz/manus")
@Validated
@Slf4j
public class ManusController {

    @Resource
    private ManusService manusService;

    @PostMapping(value = "/jManus", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "JManus 智能助手 - SSE 流式响应")
    public SseEmitter jManus(@RequestBody ManusReqVO reqVO) {
        return manusService.jManusStream(reqVO);
    }
}
