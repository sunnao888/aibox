package com.sunnao.aibox.module.biz.dal.mysql.template;

import java.util.*;

import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sunnao.aibox.framework.mybatis.core.mapper.BaseMapperX;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import org.apache.ibatis.annotations.Mapper;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.*;

/**
 * 模板 Mapper
 *
 * @author sunnao
 */
@Mapper
public interface TemplateMapper extends BaseMapperX<TemplateDO> {

    default PageResult<TemplateDO> selectPage(TemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateDO>()
                .likeIfPresent(TemplateDO::getName, reqVO.getName())
                .eqIfPresent(TemplateDO::getType, reqVO.getType())
                .betweenIfPresent(TemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateDO::getCreateTime));
    }

}