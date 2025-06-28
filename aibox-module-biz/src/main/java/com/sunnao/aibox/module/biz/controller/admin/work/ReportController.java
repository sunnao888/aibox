package com.sunnao.aibox.module.biz.controller.admin.work;

import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.module.biz.controller.admin.work.vo.ReportGenerateReqVO;
import com.sunnao.aibox.module.biz.service.work.report.ReportService;
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

@Tag(name = "工作效率-报告生成")
@RestController
@RequestMapping("/biz/work/report")
@Validated
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "生成报告")
    @PreAuthorize("@ss.hasPermission('biz:report:generate')")
    public CommonResult<String> generateReport(@Valid @RequestBody ReportGenerateReqVO reqVO) {
        String report = reportService.generateReport(reqVO);
        return CommonResult.success(report);
    }
}
