package com.sunnao.aibox.module.biz.controller.admin.xiaohongshu;

import com.sunnao.aibox.framework.common.pojo.CommonResult;
import com.sunnao.aibox.module.biz.controller.admin.xiaohongshu.vo.*;
import com.sunnao.aibox.module.biz.service.xiaohongshu.XiaohongshuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sunnao.aibox.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 小红书")
@RestController
@RequestMapping("/biz/xiaohongshu")
@Validated
@RequiredArgsConstructor
public class XiaohongshuController {

    private final XiaohongshuService xiaohongshuService;

    @PostMapping("/topic")
    @Operation(summary = "小红书选题灵感")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateTopic(@Valid @RequestBody XiaohongshuTopicReqVO reqVO) {
        return success(xiaohongshuService.generateTopic(reqVO.getUserMessage()));
    }

    @PostMapping("/note")
    @Operation(summary = "小红书笔记助手")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateNote(@Valid @RequestBody XiaohongshuNoteReqVO reqVO) {
        return success(xiaohongshuService.generateNote(reqVO.getUserMessage()));
    }

    @PostMapping("/store-visit")
    @Operation(summary = "小红书探店文案")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateStoreVisit(@Valid @RequestBody XiaohongshuStoreVisitReqVO reqVO) {
        return success(xiaohongshuService.generateStoreVisit(reqVO.getUserMessage()));
    }

    @PostMapping("/product")
    @Operation(summary = "小红书种草文案")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateProduct(@Valid @RequestBody XiaohongshuProductReqVO reqVO) {
        return success(xiaohongshuService.generateProduct(reqVO.getUserMessage()));
    }

    @PostMapping("/title")
    @Operation(summary = "小红书爆款标题")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateTitle(@Valid @RequestBody XiaohongshuTitleReqVO reqVO) {
        return success(xiaohongshuService.generateTitle(reqVO.getUserMessage()));
    }

    @PostMapping("/account-position")
    @Operation(summary = "小红书账号定位")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> generateAccountPosition(@Valid @RequestBody XiaohongshuAccountReqVO reqVO) {
        return success(xiaohongshuService.generateAccountPosition(reqVO.getUserMessage()));
    }

    @PostMapping("/score")
    @Operation(summary = "小红书笔记评分")
    @PreAuthorize("@ss.hasPermission('biz:xiaohongshu:generate')")
    public CommonResult<String> scoreNote(@Valid @RequestBody XiaohongshuScoreReqVO reqVO) {
        return success(xiaohongshuService.scoreNote(reqVO.getUserMessage()));
    }
}