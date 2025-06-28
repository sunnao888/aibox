package com.sunnao.aibox.module.biz.service.template;

import cn.hutool.core.collection.CollUtil;
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

    @Override
    public Long createTemplate(TemplateSaveReqVO createReqVO) {
        // 插入
        TemplateDO template = BeanUtils.toBean(createReqVO, TemplateDO.class);
        templateMapper.insert(template);
        // 返回
        return template.getId();
    }

    @Override
    public void updateTemplate(TemplateSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateExists(updateReqVO.getId());
        // 更新
        TemplateDO updateObj = BeanUtils.toBean(updateReqVO, TemplateDO.class);
        templateMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemplate(Long id) {
        // 校验存在
        validateTemplateExists(id);
        // 删除
        templateMapper.deleteById(id);
    }

    @Override
        public void deleteTemplateListByIds(List<Long> ids) {
        // 校验存在
        validateTemplateExists(ids);
        // 删除
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

}