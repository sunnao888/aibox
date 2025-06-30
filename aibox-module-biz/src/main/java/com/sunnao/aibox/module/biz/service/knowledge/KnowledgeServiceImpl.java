package com.sunnao.aibox.module.biz.service.knowledge;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.sunnao.aibox.module.biz.controller.admin.knowledge.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.knowledge.KnowledgeDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;

import com.sunnao.aibox.module.biz.dal.mysql.knowledge.KnowledgeMapper;

import static com.sunnao.aibox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.convertList;
import static com.sunnao.aibox.framework.common.util.collection.CollectionUtils.diffList;
import static com.sunnao.aibox.module.biz.enums.ErrorCodeConstants.*;

/**
 * 知识库 Service 实现类
 *
 * @author sunnao
 */
@Service
@Validated
public class KnowledgeServiceImpl implements KnowledgeService {

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Override
    public Long createKnowledge(KnowledgeSaveReqVO createReqVO) {
        // 插入
        KnowledgeDO knowledge = BeanUtils.toBean(createReqVO, KnowledgeDO.class);
        knowledgeMapper.insert(knowledge);
        // 返回
        return knowledge.getId();
    }

    @Override
    public void updateKnowledge(KnowledgeSaveReqVO updateReqVO) {
        // 校验存在
        validateKnowledgeExists(updateReqVO.getId());
        // 更新
        KnowledgeDO updateObj = BeanUtils.toBean(updateReqVO, KnowledgeDO.class);
        knowledgeMapper.updateById(updateObj);
    }

    @Override
    public void deleteKnowledge(Long id) {
        // 校验存在
        validateKnowledgeExists(id);
        // 删除
        knowledgeMapper.deleteById(id);
    }

    @Override
        public void deleteKnowledgeListByIds(List<Long> ids) {
        // 校验存在
        validateKnowledgeExists(ids);
        // 删除
        knowledgeMapper.deleteByIds(ids);
        }

    private void validateKnowledgeExists(List<Long> ids) {
        List<KnowledgeDO> list = knowledgeMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
    }

    private void validateKnowledgeExists(Long id) {
        if (knowledgeMapper.selectById(id) == null) {
            throw exception(KNOWLEDGE_NOT_EXISTS);
        }
    }

    @Override
    public KnowledgeDO getKnowledge(Long id) {
        return knowledgeMapper.selectById(id);
    }

    @Override
    public PageResult<KnowledgeDO> getKnowledgePage(KnowledgePageReqVO pageReqVO) {
        return knowledgeMapper.selectPage(pageReqVO);
    }

}