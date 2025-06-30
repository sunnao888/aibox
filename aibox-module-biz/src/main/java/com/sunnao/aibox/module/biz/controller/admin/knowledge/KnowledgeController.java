package com.sunnao.aibox.module.biz.controller.admin.knowledge;

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

import com.sunnao.aibox.module.biz.controller.admin.knowledge.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.knowledge.KnowledgeDO;
import com.sunnao.aibox.module.biz.service.knowledge.KnowledgeService;

@Tag(name = "管理后台 - 知识库")
@RestController
@RequestMapping("/biz/knowledge")
@Validated
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    @PostMapping("/create")
    @Operation(summary = "创建知识库")
    @PreAuthorize("@ss.hasPermission('biz:knowledge:create')")
    public CommonResult<Long> createKnowledge(@Valid @RequestBody KnowledgeSaveReqVO createReqVO) {
        return success(knowledgeService.createKnowledge(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新知识库")
    @PreAuthorize("@ss.hasPermission('biz:knowledge:update')")
    public CommonResult<Boolean> updateKnowledge(@Valid @RequestBody KnowledgeSaveReqVO updateReqVO) {
        knowledgeService.updateKnowledge(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除知识库")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('biz:knowledge:delete')")
    public CommonResult<Boolean> deleteKnowledge(@RequestParam("id") Long id) {
        knowledgeService.deleteKnowledge(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除知识库")
                @PreAuthorize("@ss.hasPermission('biz:knowledge:delete')")
    public CommonResult<Boolean> deleteKnowledgeList(@RequestParam("ids") List<Long> ids) {
        knowledgeService.deleteKnowledgeListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得知识库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('biz:knowledge:query')")
    public CommonResult<KnowledgeRespVO> getKnowledge(@RequestParam("id") Long id) {
        KnowledgeDO knowledge = knowledgeService.getKnowledge(id);
        return success(BeanUtils.toBean(knowledge, KnowledgeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得知识库分页")
    @PreAuthorize("@ss.hasPermission('biz:knowledge:query')")
    public CommonResult<PageResult<KnowledgeRespVO>> getKnowledgePage(@Valid KnowledgePageReqVO pageReqVO) {
        PageResult<KnowledgeDO> pageResult = knowledgeService.getKnowledgePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, KnowledgeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出知识库 Excel")
    @PreAuthorize("@ss.hasPermission('biz:knowledge:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportKnowledgeExcel(@Valid KnowledgePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<KnowledgeDO> list = knowledgeService.getKnowledgePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "知识库.xls", "数据", KnowledgeRespVO.class,
                        BeanUtils.toBean(list, KnowledgeRespVO.class));
    }

}