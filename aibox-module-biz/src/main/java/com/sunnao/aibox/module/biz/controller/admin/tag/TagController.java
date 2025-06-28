package com.sunnao.aibox.module.biz.controller.admin.tag;

import com.sunnao.aibox.framework.apilog.core.annotation.ApiAccessLog;
import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.framework.excel.core.util.ExcelUtils;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagPageReqVO;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagRespVO;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagSaveReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.tag.TagDO;
import com.sunnao.aibox.module.biz.service.tag.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.sunnao.aibox.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.sunnao.aibox.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 标签")
@RestController
@RequestMapping("/biz/tag")
@Validated
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping("/create")
    @Operation(summary = "创建标签")
    @PreAuthorize("@ss.hasPermission('biz:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody TagSaveReqVO createReqVO) {
        return success(tagService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新标签")
    @PreAuthorize("@ss.hasPermission('biz:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody TagSaveReqVO updateReqVO) {
        tagService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        tagService.deleteTag(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除标签")
    @PreAuthorize("@ss.hasPermission('biz:tag:delete')")
    public CommonResult<Boolean> deleteTagList(@RequestParam("ids") List<Long> ids) {
        tagService.deleteTagListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:tag:query')")
    public CommonResult<TagRespVO> getTag(@RequestParam("id") Long id) {
        TagDO tag = tagService.getTag(id);
        return success(BeanUtils.toBean(tag, TagRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得标签分页")
    @PreAuthorize("@ss.hasPermission('biz:tag:query')")
    public CommonResult<PageResult<TagRespVO>> getTagPage(@Valid TagPageReqVO pageReqVO) {
        PageResult<TagDO> pageResult = tagService.getTagPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出标签 Excel")
    @PreAuthorize("@ss.hasPermission('biz:tag:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagExcel(@Valid TagPageReqVO pageReqVO,
                               HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagDO> list = tagService.getTagPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "标签.xls", "数据", TagRespVO.class,
                BeanUtils.toBean(list, TagRespVO.class));
    }

    @GetMapping("/all")
    @Operation(summary = "获得全部标签列表")
    @PreAuthorize("@ss.hasPermission('biz:tag:query')")
    public CommonResult<List<TagRespVO>> getAllTag() {
        List<TagDO> list = tagService.getEnableTagList();
        return success(BeanUtils.toBean(list, TagRespVO.class));
    }

}