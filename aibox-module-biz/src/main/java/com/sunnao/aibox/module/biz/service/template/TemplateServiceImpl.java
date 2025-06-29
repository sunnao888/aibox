package com.sunnao.aibox.module.biz.service.template;

import cn.hutool.core.collection.CollUtil;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sunnao.aibox.framework.security.core.util.SecurityFrameworkUtils;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.TemplatePageReqVO;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.TemplateRecommendReqVO;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.TemplateSaveReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.module.biz.dal.mysql.template.TemplateMapper;
import com.sunnao.aibox.module.biz.dal.mysql.templatetaglink.TemplateTagLinkMapper;
import com.sunnao.aibox.module.biz.dal.redis.recommend.RecommendTemplateRedisDAO;
import com.sunnao.aibox.module.biz.service.templatetaglink.TemplateTagLinkService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sunnao.aibox.module.biz.enums.ErrorCodeConstants.TEMPLATE_NOT_EXISTS;

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

    @Resource
    private RecommendTemplateRedisDAO recommendTemplateRedisDAO;

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

        // 同步redis
        recommendTemplateRedisDAO.addTemplateTypeLink(template.getType(), template.getId());

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

        // 同步redis
        recommendTemplateRedisDAO.addTemplateTypeLink(updateObj.getType(), updateObj.getId());
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

        // 如果没有传入标签ID，返回推荐模板
        if (CollUtil.isEmpty(reqVO.getTagIds())) {

            // 查找用户最常用的5个标签
            List<Long> userLastTags = recommendTemplateRedisDAO.getUserLastTags(SecurityFrameworkUtils.getLoginUserId(), 10);
            // 根据标签ID列表，推荐指定数量的模板
            List<String> templateIds = recommendTemplateRedisDAO.recommendTemplates(userLastTags, reqVO.getType(), 5);
            if (CollUtil.isEmpty(templateIds)) {
                return templateMapper.selectList(new LambdaQueryWrapperX<TemplateDO>()
                        .eqIfPresent(TemplateDO::getType, reqVO.getType())
                        .orderByDesc(TemplateDO::getCreateTime));
            }

            // 根据推荐的模板id查询模板
            return templateMapper.selectList(new LambdaQueryWrapperX<TemplateDO>()
                    .in(TemplateDO::getId, templateIds)
                    .orderByDesc(TemplateDO::getCreateTime));
        }

        // 如果传入了标签ID，走普通数据库查询
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
                        .orderByDesc(TemplateDO::getCreateTime))
                .stream()
                .limit(10)
                .collect(java.util.stream.Collectors.toList());
    }
}