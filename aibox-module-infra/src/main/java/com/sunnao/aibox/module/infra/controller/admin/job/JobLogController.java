package com.sunnao.aibox.module.infra.controller.admin.job;

import com.sunnao.aibox.framework.apilog.core.annotation.ApiAccessLog;
import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.framework.excel.core.util.ExcelUtils;
import com.sunnao.aibox.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import com.sunnao.aibox.module.infra.controller.admin.job.vo.log.JobLogRespVO;
import com.sunnao.aibox.module.infra.dal.dataobject.job.JobLogDO;
import com.sunnao.aibox.module.infra.service.job.JobLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.sunnao.aibox.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.sunnao.aibox.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 定时任务日志")
@RestController
@RequestMapping("/infra/job-log")
@Validated
public class JobLogController {

    @Resource
    private JobLogService jobLogService;

    @GetMapping("/get")
    @Operation(summary = "获得定时任务日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<JobLogRespVO> getJobLog(@RequestParam("id") Long id) {
        JobLogDO jobLog = jobLogService.getJobLog(id);
        return success(BeanUtils.toBean(jobLog, JobLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得定时任务日志分页")
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<PageResult<JobLogRespVO>> getJobLogPage(@Valid JobLogPageReqVO pageVO) {
        PageResult<JobLogDO> pageResult = jobLogService.getJobLogPage(pageVO);
        return success(BeanUtils.toBean(pageResult, JobLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出定时任务日志 Excel")
    @PreAuthorize("@ss.hasPermission('infra:job:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportJobLogExcel(@Valid JobLogPageReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<JobLogDO> list = jobLogService.getJobLogPage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "任务日志.xls", "数据", JobLogRespVO.class,
                BeanUtils.toBean(list, JobLogRespVO.class));
    }

}