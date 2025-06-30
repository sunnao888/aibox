package com.sunnao.aibox.module.biz.dal.mysql.knowledge;

import java.util.*;

import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sunnao.aibox.framework.mybatis.core.mapper.BaseMapperX;
import com.sunnao.aibox.module.biz.dal.dataobject.knowledge.KnowledgeDO;
import org.apache.ibatis.annotations.Mapper;
import com.sunnao.aibox.module.biz.controller.admin.knowledge.vo.*;

/**
 * 知识库 Mapper
 *
 * @author sunnao
 */
@Mapper
public interface KnowledgeMapper extends BaseMapperX<KnowledgeDO> {

    default PageResult<KnowledgeDO> selectPage(KnowledgePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<KnowledgeDO>()
                .likeIfPresent(KnowledgeDO::getTitle, reqVO.getTitle())
                .eqIfPresent(KnowledgeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(KnowledgeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(KnowledgeDO::getId));
    }

}