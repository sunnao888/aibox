package com.sunnao.aibox.module.biz.controller.admin.work;

import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.BrainStormingReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.MockInterviewReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.PPTReqVO;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import com.sunnao.aibox.module.biz.service.work.report.WorkService;
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

@Tag(name = "工作效率")
@RestController
@RequestMapping("/biz/work")
@Validated
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/report")
    @Operation(summary = "生成报告")
    @PreAuthorize("@ss.hasPermission('biz:work:generate')")
    public CommonResult<String> generateReport(@Valid @RequestBody ReportGenerateReqVO reqVO) {
        String report = workService.generateReport(reqVO);
        return CommonResult.success(report);
    }

    @PostMapping("/brainstorming")
    @Operation(summary = "头脑风暴")
    @PreAuthorize("@ss.hasPermission('biz:work:generate')")
    public CommonResult<String> brainStorming(@Valid @RequestBody BrainStormingReqVO reqVO) {
        String report = workService.brainStorming(reqVO);
        return CommonResult.success(report);
    }

    @PostMapping("/ppt")
    @Operation(summary = "PPT大纲生成")
    @PreAuthorize("@ss.hasPermission('biz:work:generate')")
    public CommonResult<String> ppt(@Valid @RequestBody PPTReqVO reqVO) {
        String report = workService.ppt(reqVO);
        return CommonResult.success(report);
    }

    @PostMapping("/mockInterview")
    @Operation(summary = "模拟面试")
    @PreAuthorize("@ss.hasPermission('biz:work:generate')")
    public CommonResult<String> mockInterview(@Valid @RequestBody MockInterviewReqVO reqVO) {
        String report = workService.mockInterview(reqVO);
        return CommonResult.success(report);
    }



}
