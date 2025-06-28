package com.sunnao.aibox.module.biz.service.templatetaglink;

import java.util.*;
import jakarta.validation.*;
import com.sunnao.aibox.module.biz.controller.admin.templatetaglink.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.templatetaglink.TemplateTagLinkDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;

/**
 * 模板标签关联 Service 接口
 *
 * @author sunnao
 */
public interface TemplateTagLinkService {

    /**
     * 创建模板标签关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplateTagLink(@Valid TemplateTagLinkSaveReqVO createReqVO);

    /**
     * 更新模板标签关联
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplateTagLink(@Valid TemplateTagLinkSaveReqVO updateReqVO);

    /**
     * 删除模板标签关联
     *
     * @param id 编号
     */
    void deleteTemplateTagLink(Long id);

    /**
    * 批量删除模板标签关联
    *
    * @param ids 编号
    */
    void deleteTemplateTagLinkListByIds(List<Long> ids);

    /**
     * 获得模板标签关联
     *
     * @param id 编号
     * @return 模板标签关联
     */
    TemplateTagLinkDO getTemplateTagLink(Long id);

    /**
     * 获得模板标签关联分页
     *
     * @param pageReqVO 分页查询
     * @return 模板标签关联分页
     */
    PageResult<TemplateTagLinkDO> getTemplateTagLinkPage(TemplateTagLinkPageReqVO pageReqVO);

    /**
     * 为模板关联标签
     *
     * @param templateId 模板ID
     * @param tagIds 标签ID列表
     */
    void createTemplateTagLinks(Long templateId, List<Long> tagIds);

    /**
     * 删除模板的所有标签关联
     *
     * @param templateId 模板ID
     */
    void deleteTemplateTagLinksByTemplateId(Long templateId);

    /**
     * 根据模板ID获取标签ID列表
     *
     * @param templateId 模板ID
     * @return 标签ID列表
     */
    List<Long> getTagIdsByTemplateId(Long templateId);

}