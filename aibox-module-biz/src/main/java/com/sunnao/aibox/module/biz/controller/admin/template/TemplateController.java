package com.sunnao.aibox.module.biz.controller.admin.template;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import static com.sunnao.aibox.framework.common.pojo.CommonResult.success;

import com.sunnao.aibox.framework.excel.core.util.ExcelUtils;

import com.sunnao.aibox.framework.apilog.core.annotation.ApiAccessLog;
import static com.sunnao.aibox.framework.apilog.core.enums.OperateTypeEnum.*;

import com.sunnao.aibox.module.biz.controller.admin.template.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.module.biz.service.template.TemplateService;

@Tag(name = "管理后台 - 模板")
@RestController
@RequestMapping("/biz/template")
@Validated
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @PostMapping("/create")
    @Operation(summary = "创建模板")
    @PreAuthorize("@ss.hasPermission('biz:template:create')")
    public CommonResult<Long> createTemplate(@Valid @RequestBody TemplateSaveReqVO createReqVO) {
        return success(templateService.createTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新模板")
    @PreAuthorize("@ss.hasPermission('biz:template:update')")
    public CommonResult<Boolean> updateTemplate(@Valid @RequestBody TemplateSaveReqVO updateReqVO) {
        templateService.updateTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:template:delete')")
    public CommonResult<Boolean> deleteTemplate(@RequestParam("id") Long id) {
        templateService.deleteTemplate(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除模板")
                @PreAuthorize("@ss.hasPermission('biz:template:delete')")
    public CommonResult<Boolean> deleteTemplateList(@RequestParam("ids") List<Long> ids) {
        templateService.deleteTemplateListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:template:query')")
    public CommonResult<TemplateRespVO> getTemplate(@RequestParam("id") Long id) {
        TemplateDO template = templateService.getTemplate(id);
        return success(BeanUtils.toBean(template, TemplateRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模板分页")
    @PreAuthorize("@ss.hasPermission('biz:template:query')")
    public CommonResult<PageResult<TemplateRespVO>> getTemplatePage(@Valid TemplatePageReqVO pageReqVO) {
        PageResult<TemplateDO> pageResult = templateService.getTemplatePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TemplateRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模板 Excel")
    @PreAuthorize("@ss.hasPermission('biz:template:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTemplateExcel(@Valid TemplatePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TemplateDO> list = templateService.getTemplatePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模板.xls", "数据", TemplateRespVO.class,
                        BeanUtils.toBean(list, TemplateRespVO.class));
    }

}