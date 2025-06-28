package com.sunnao.aibox.module.biz.dal.mysql.tag;

import com.sunnao.aibox.framework.common.enums.CommonStatusEnum;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.mybatis.core.mapper.BaseMapperX;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.TagPageReqVO;
import com.sunnao.aibox.module.biz.dal.dataobject.tag.TagDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 标签 Mapper
 *
 * @author sunnao
 */
@Mapper
public interface TagMapper extends BaseMapperX<TagDO> {

    default PageResult<TagDO> selectPage(TagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TagDO>()
                .likeIfPresent(TagDO::getName, reqVO.getName())
                .eqIfPresent(TagDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TagDO::getId));
    }

    default List<TagDO> getEnableTagList() {
        LambdaQueryWrapperX<TagDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(TagDO::getStatus, CommonStatusEnum.ENABLE.getStatus());
        return selectList(lambdaQueryWrapperX);
    }
}