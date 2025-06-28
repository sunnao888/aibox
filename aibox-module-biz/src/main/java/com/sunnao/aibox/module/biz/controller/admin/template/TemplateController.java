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
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagRespVO;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.module.biz.dal.dataobject.tag.TagDO;
import com.sunnao.aibox.module.biz.service.template.TemplateService;
import com.sunnao.aibox.module.biz.service.tag.TagService;
import com.sunnao.aibox.module.biz.service.templatetaglink.TemplateTagLinkService;

@Tag(name = "管理后台 - 模板")
@RestController
@RequestMapping("/biz/template")
@Validated
public class TemplateController {

    @Resource
    private TemplateService templateService;

    @Resource
    private TagService tagService;

    @Resource
    private TemplateTagLinkService templateTagLinkService;

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
        return success(convertToRespVO(template));
    }

    @GetMapping("/page")
    @Operation(summary = "获得模板分页")
    @PreAuthorize("@ss.hasPermission('biz:template:query')")
    public CommonResult<PageResult<TemplateRespVO>> getTemplatePage(@Valid TemplatePageReqVO pageReqVO) {
        PageResult<TemplateDO> pageResult = templateService.getTemplatePage(pageReqVO);
        List<TemplateRespVO> respVOList = pageResult.getList().stream()
                .map(this::convertToRespVO)
                .collect(java.util.stream.Collectors.toList());
        return success(new PageResult<>(respVOList, pageResult.getTotal()));
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
        List<TemplateRespVO> respVOList = list.stream()
                .map(this::convertToRespVO)
                .collect(java.util.stream.Collectors.toList());
        ExcelUtils.write(response, "模板.xls", "数据", TemplateRespVO.class, respVOList);
    }

    private TemplateRespVO convertToRespVO(TemplateDO template) {
        if (template == null) {
            return null;
        }
        
        TemplateRespVO respVO = BeanUtils.toBean(template, TemplateRespVO.class);
        
        // 获取关联的标签
        List<Long> tagIds = templateTagLinkService.getTagIdsByTemplateId(template.getId());
        if (!tagIds.isEmpty()) {
            List<TagDO> tags = tagService.getTagListByIds(tagIds);
            respVO.setTags(BeanUtils.toBean(tags, TagRespVO.class));
        }
        
        return respVO;
    }

}