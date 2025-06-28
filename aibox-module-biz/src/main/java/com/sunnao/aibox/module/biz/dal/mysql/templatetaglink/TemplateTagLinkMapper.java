package com.sunnao.aibox.module.biz.dal.mysql.templatetaglink;

import java.util.*;

import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sunnao.aibox.framework.mybatis.core.mapper.BaseMapperX;
import com.sunnao.aibox.module.biz.dal.dataobject.templatetaglink.TemplateTagLinkDO;
import org.apache.ibatis.annotations.Mapper;
import com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo.*;

/**
 * 模板标签关联 Mapper
 *
 * @author sunnao
 */
@Mapper
public interface TemplateTagLinkMapper extends BaseMapperX<TemplateTagLinkDO> {

    default PageResult<TemplateTagLinkDO> selectPage(TemplateTagLinkPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateTagLinkDO>()
                .eqIfPresent(TemplateTagLinkDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateTagLinkDO::getTagId, reqVO.getTagId())
                .betweenIfPresent(TemplateTagLinkDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateTagLinkDO::getId));
    }

}