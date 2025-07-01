package com.sunnao.aibox.module.biz.controller.admin.manus;

import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.module.biz.ai.agent.manus.model.ResultMessage;
import com.sunnao.aibox.module.biz.controller.admin.manus.vo.ManusReqVO;
import com.sunnao.aibox.module.biz.service.manus.ManusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "通用助理")
@RestController
@RequestMapping("/biz/manus")
@Validated
public class ManusController {

    @Resource
    private ManusService manusService;

    @PostMapping("/jManus")
    public CommonResult<List<ResultMessage>> jManus(@RequestBody ManusReqVO reqVO) {
        return CommonResult.success(manusService.jManus(reqVO));
    }
}
