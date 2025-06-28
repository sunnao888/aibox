package com.sunnao.aibox.module.biz.controller.admin.templatetaglink;

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

import com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.templatetaglink.TemplateTagLinkDO;
import com.sunnao.aibox.module.biz.service.templatetaglink.TemplateTagLinkService;

@Tag(name = "管理后台 - 模板标签关联")
@RestController
@RequestMapping("/biz/template-tag-link")
@Validated
public class TemplateTagLinkController {

    @Resource
    private TemplateTagLinkService templateTagLinkService;

    @PostMapping("/create")
    @Operation(summary = "创建模板标签关联")
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:create')")
    public CommonResult<Long> createTemplateTagLink(@Valid @RequestBody TemplateTagLinkSaveReqVO createReqVO) {
        return success(templateTagLinkService.createTemplateTagLink(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新模板标签关联")
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:update')")
    public CommonResult<Boolean> updateTemplateTagLink(@Valid @RequestBody TemplateTagLinkSaveReqVO updateReqVO) {
        templateTagLinkService.updateTemplateTagLink(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除模板标签关联")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:delete')")
    public CommonResult<Boolean> deleteTemplateTagLink(@RequestParam("id") Long id) {
        templateTagLinkService.deleteTemplateTagLink(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除模板标签关联")
                @PreAuthorize("@ss.hasPermission('biz:template-tag-link:delete')")
    public CommonResult<Boolean> deleteTemplateTagLinkList(@RequestParam("ids") List<Long> ids) {
        templateTagLinkService.deleteTemplateTagLinkListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得模板标签关联")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:query')")
    public CommonResult<TemplateTagLinkRespVO> getTemplateTagLink(@RequestParam("id") Long id) {
        TemplateTagLinkDO templateTagLink = templateTagLinkService.getTemplateTagLink(id);
        return success(BeanUtils.toBean(templateTagLink, TemplateTagLinkRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模板标签关联分页")
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:query')")
    public CommonResult<PageResult<TemplateTagLinkRespVO>> getTemplateTagLinkPage(@Valid TemplateTagLinkPageReqVO pageReqVO) {
        PageResult<TemplateTagLinkDO> pageResult = templateTagLinkService.getTemplateTagLinkPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TemplateTagLinkRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出模板标签关联 Excel")
    @PreAuthorize("@ss.hasPermission('biz:template-tag-link:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTemplateTagLinkExcel(@Valid TemplateTagLinkPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TemplateTagLinkDO> list = templateTagLinkService.getTemplateTagLinkPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "模板标签关联.xls", "数据", TemplateTagLinkRespVO.class,
                        BeanUtils.toBean(list, TemplateTagLinkRespVO.class));
    }

}