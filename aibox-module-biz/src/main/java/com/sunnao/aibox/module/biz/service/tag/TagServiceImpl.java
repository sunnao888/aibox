package com.sunnao.aibox.module.biz.service.tag;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.tag.TagDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;

import com.sunnao.aibox.module.biz.dal.mysql.tag.TagMapper;

import static com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.convertList;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.diffList;
import static com.sunnao.aibox.module.biz.enums.ErrorCodeConstants.*;

/**
 * 标签 Service 实现类
 *
 * @author sunnao
 */
@Service
@Validated
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public Long createTag(TagSaveReqVO createReqVO) {
        // 插入
        TagDO tag = BeanUtils.toBean(createReqVO, TagDO.class);
        tagMapper.insert(tag);
        // 返回
        return tag.getId();
    }

    @Override
    public void updateTag(TagSaveReqVO updateReqVO) {
        // 校验存在
        validateTagExists(updateReqVO.getId());
        // 更新
        TagDO updateObj = BeanUtils.toBean(updateReqVO, TagDO.class);
        tagMapper.updateById(updateObj);
    }

    @Override
    public void deleteTag(Long id) {
        // 校验存在
        validateTagExists(id);
        // 删除
        tagMapper.deleteById(id);
    }

    @Override
        public void deleteTagListByIds(List<Long> ids) {
        // 校验存在
        validateTagExists(ids);
        // 删除
        tagMapper.deleteByIds(ids);
        }

    private void validateTagExists(List<Long> ids) {
        List<TagDO> list = tagMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(TAG_NOT_EXISTS);
        }
    }

    private void validateTagExists(Long id) {
        if (tagMapper.selectById(id) == null) {
            throw exception(TAG_NOT_EXISTS);
        }
    }

    @Override
    public TagDO getTag(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public PageResult<TagDO> getTagPage(TagPageReqVO pageReqVO) {
        return tagMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TagDO> getTagListByIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return tagMapper.selectByIds(ids);
    }

    @Override
    public List<TagDO> getEnableTagList() {
        return tagMapper.getEnableTagList();
    }
}