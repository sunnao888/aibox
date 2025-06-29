package com.sunnao.aibox.module.biz.service.templatetaglink;

import cn.hutool.core.collection.CollUtil;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo.TemplateTagLinkPageReqVO;
import com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo.TemplateTagLinkSaveReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.templatetaglink.TemplateTagLinkDO;
import com.sunnao.aibox.module.biz.dal.mysql.templatetaglink.TemplateTagLinkMapper;
import com.sunnao.aibox.module.biz.dal.redis.recommend.RecommendTemplateRedisDAO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.convertList;
import static com.sunnao.aibox.module.biz.enums.ErrorCodeConstants.TEMPLATE_TAG_LINK_NOT_EXISTS;

/**
 * 模板标签关联 Service 实现类
 *
 * @author sunnao
 */
@Service
@Validated
public class TemplateTagLinkServiceImpl implements TemplateTagLinkService {

    @Resource
    private TemplateTagLinkMapper templateTagLinkMapper;

    @Resource
    private RecommendTemplateRedisDAO recommendTemplateRedisDAO;


    @Override
    public Long createTemplateTagLink(TemplateTagLinkSaveReqVO createReqVO) {
        // 插入
        TemplateTagLinkDO templateTagLink = BeanUtils.toBean(createReqVO, TemplateTagLinkDO.class);
        templateTagLinkMapper.insert(templateTagLink);

        // 同步redis
        recommendTemplateRedisDAO.addTagTemplateLink(templateTagLink.getTagId(), templateTagLink.getTemplateId());

        // 返回
        return templateTagLink.getId();
    }

    @Override
    public void updateTemplateTagLink(TemplateTagLinkSaveReqVO updateReqVO) {
        // 校验存在
        validateTemplateTagLinkExists(updateReqVO.getId());
        // 更新
        TemplateTagLinkDO updateObj = BeanUtils.toBean(updateReqVO, TemplateTagLinkDO.class);

        recommendTemplateRedisDAO.addTagTemplateLink(updateObj.getTagId(), updateObj.getTemplateId());

        templateTagLinkMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemplateTagLink(Long id) {
        // 校验存在
        validateTemplateTagLinkExists(id);
        // 删除
        templateTagLinkMapper.deleteById(id);
    }

    @Override
    public void deleteTemplateTagLinkListByIds(List<Long> ids) {
        // 校验存在
        validateTemplateTagLinkExists(ids);
        // 删除
        templateTagLinkMapper.deleteByIds(ids);
    }

    private void validateTemplateTagLinkExists(List<Long> ids) {
        List<TemplateTagLinkDO> list = templateTagLinkMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(TEMPLATE_TAG_LINK_NOT_EXISTS);
        }
    }

    private void validateTemplateTagLinkExists(Long id) {
        if (templateTagLinkMapper.selectById(id) == null) {
            throw exception(TEMPLATE_TAG_LINK_NOT_EXISTS);
        }
    }

    @Override
    public TemplateTagLinkDO getTemplateTagLink(Long id) {
        return templateTagLinkMapper.selectById(id);
    }

    @Override
    public PageResult<TemplateTagLinkDO> getTemplateTagLinkPage(TemplateTagLinkPageReqVO pageReqVO) {
        return templateTagLinkMapper.selectPage(pageReqVO);
    }

    @Override
    public void createTemplateTagLinks(Long templateId, List<Long> tagIds) {
        if (CollUtil.isEmpty(tagIds)) {
            return;
        }

        List<TemplateTagLinkDO> links = new ArrayList<>();
        for (Long tagId : tagIds) {
            TemplateTagLinkDO link = TemplateTagLinkDO.builder()
                    .templateId(templateId)
                    .tagId(tagId)
                    .build();
            links.add(link);
            recommendTemplateRedisDAO.addTagTemplateLink(tagId, templateId);
        }

        templateTagLinkMapper.insertBatch(links);
    }

    @Override
    public void deleteTemplateTagLinksByTemplateId(Long templateId) {
        templateTagLinkMapper.deleteByTemplateId(templateId);
    }

    @Override
    public List<Long> getTagIdsByTemplateId(Long templateId) {
        List<TemplateTagLinkDO> links = templateTagLinkMapper.selectByTemplateId(templateId);
        return convertList(links, TemplateTagLinkDO::getTagId);
    }

}