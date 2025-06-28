package com.sunnao.aibox.module.biz.service.template;

import java.util.*;
import jakarta.validation.*;
import com.sunnao.aibox.module.biz.controller.admin.template.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.template.TemplateDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;

/**
 * 模板 Service 接口
 *
 * @author sunnao
 */
public interface TemplateService {

    /**
     * 创建模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTemplate(@Valid TemplateSaveReqVO createReqVO);

    /**
     * 更新模板
     *
     * @param updateReqVO 更新信息
     */
    void updateTemplate(@Valid TemplateSaveReqVO updateReqVO);

    /**
     * 删除模板
     *
     * @param id 编号
     */
    void deleteTemplate(Long id);

    /**
    * 批量删除模板
    *
    * @param ids 编号
    */
    void deleteTemplateListByIds(List<Long> ids);

    /**
     * 获得模板
     *
     * @param id 编号
     * @return 模板
     */
    TemplateDO getTemplate(Long id);

    /**
     * 获得模板分页
     *
     * @param pageReqVO 分页查询
     * @return 模板分页
     */
    PageResult<TemplateDO> getTemplatePage(TemplatePageReqVO pageReqVO);

}