package com.sunnao.aibox.module.biz.service.template;

import cn.hutool.core.collection.CollUtil;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;

import com.sunnao.aibox.module.biz.dal.mysql.template.TemplateMapper;
import com.sunnao.aibox.module.biz.dal.mysql.templatetaglink.TemplateTagLinkMapper;
import com.sunnao.aibox.module.biz.service.templatetaglink.TemplateTagLinkService;

import static com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.convertList;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.diffList;
import static com.sunnao.aibox.module.biz.enums.ErrorCodeConstants.*;

/**
 * 模板 Service 实现类
 *
 * @author sunnao
 */
@Service
@Validated
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private TemplateTagLinkService templateTagLinkService;
    
    @Resource
    private TemplateTagLinkMapper templateTagLinkMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTemplate(TemplateSaveReqVO createReqVO) {
        // 插入模板
        TemplateDO template = BeanUtils.toBean(createReqVO, TemplateDO.class);
        templateMapper.insert(template);
        
        // 关联标签
        if (CollUtil.isNotEmpty(createReqVO.getTagIds())) {
            templateTagLinkService.createTemplateTagLinks(template.getId(), createReqVO.getTagIds());
        }
        
        // 返回
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTemplate(TemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateExists(updateReqVO.getId());
        
        // 更新模板
        TemplateDO updateObj = BeanUtils.toBean(updateReqVO, TemplateDO.class);
        templateMapper.updateById(updateObj);
        
        // 重新关联标签
        templateTagLinkService.deleteTemplateTagLinksByTemplateId(updateReqVO.getId());
        if (CollUtil.isNotEmpty(updateReqVO.getTagIds())) {
            templateTagLinkService.createTemplateTagLinks(updateReqVO.getId(), updateReqVO.getTagIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Long id) {
        // 校验存在
        validateTemplateExists(id);
        
        // 删除标签关联
        templateTagLinkService.deleteTemplateTagLinksByTemplateId(id);
        
        // 删除模板
        templateMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplateListByIds(List<Long> ids) {
        // 校验存在
        validateTemplateExists(ids);
        
        // 删除标签关联
        for (Long id : ids) {
            templateTagLinkService.deleteTemplateTagLinksByTemplateId(id);
        }
        
        // 删除模板
        templateMapper.deleteByIds(ids);
    }

    private void validateTemplateExists(List<Long> ids) {
        List<TemplateDO> list = templateMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(TEMPLATE_NOT_EXISTS);
        }
    }

    private void validateTemplateExists(Long id) {
        if (templateMapper.selectById(id) == null) {
            throw exception(TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public TemplateDO getTemplate(Long id) {
        return templateMapper.selectById(id);
    }

    @Override
    public PageResult<TemplateDO> getTemplatePage(TemplatePageReqVO pageReqVO) {
        return templateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TemplateDO> getRecommendTemplate(TemplateRecommendReqVO reqVO) {
        // 如果没有传入标签ID，直接根据名称和类型查询
        if (CollUtil.isEmpty(reqVO.getTagIds())) {
            return templateMapper.selectList(new LambdaQueryWrapperX<TemplateDO>()
                    .likeIfPresent(TemplateDO::getName, reqVO.getName())
                    .eqIfPresent(TemplateDO::getType, reqVO.getType())
                    .orderByDesc(TemplateDO::getCreateTime));
        }
        
        // 如果传入了标签ID，需要先查询包含这些标签的模板ID
        List<Long> templateIds = templateTagLinkMapper.selectTemplateIdsByTagIds(reqVO.getTagIds());
        
        // 如果没有找到匹配标签的模板，返回空列表
        if (CollUtil.isEmpty(templateIds)) {
            return new ArrayList<>();
        }
        
        // 根据模板ID、名称和类型查询模板
        return templateMapper.selectList(new LambdaQueryWrapperX<TemplateDO>()
                .in(TemplateDO::getId, templateIds)
                .likeIfPresent(TemplateDO::getName, reqVO.getName())
                .eqIfPresent(TemplateDO::getType, reqVO.getType())
                .orderByDesc(TemplateDO::getCreateTime));
    }
}