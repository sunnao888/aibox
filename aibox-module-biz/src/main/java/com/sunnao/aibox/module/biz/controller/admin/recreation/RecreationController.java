package com.sunnao.aibox.module.biz.controller.admin.recreation;

import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.module.biz.controller.admin.recreation.vo.WritePoemReqVO;
import com.sunnao.aibox.module.biz.service.recreation.RecreationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "生活娱乐")
@RestController
@RequestMapping("/biz/recreation")
@Validated
@RequiredArgsConstructor
public class RecreationController {

    private final RecreationService recreationService;

    @PostMapping("/writePoem")
    @Operation(summary = "生成报告")
    @PreAuthorize("@ss.hasPermission('biz:recreation:generate')")
    public CommonResult<String> writePoem(@Valid @RequestBody WritePoemReqVO reqVO) {
        String report = recreationService.writePoem(reqVO);
        return CommonResult.success(report);
    }
}
