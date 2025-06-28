package com.sunnao.aibox.module.biz.service.tag;

import java.util.*;
import jakarta.validation.*;
import com.sunnao.aibox.module.biz.controller.admin.tag.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.tag.TagDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;

/**
 * 标签 Service 接口
 *
 * @author sunnao
 */
public interface TagService {

    /**
     * 创建标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTag(@Valid TagSaveReqVO createReqVO);

    /**
     * 更新标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTag(@Valid TagSaveReqVO updateReqVO);

    /**
     * 删除标签
     *
     * @param id 编号
     */
    void deleteTag(Long id);

    /**
    * 批量删除标签
    *
    * @param ids 编号
    */
    void deleteTagListByIds(List<Long> ids);

    /**
     * 获得标签
     *
     * @param id 编号
     * @return 标签
     */
    TagDO getTag(Long id);

    /**
     * 获得标签分页
     *
     * @param pageReqVO 分页查询
     * @return 标签分页
     */
    PageResult<TagDO> getTagPage(TagPageReqVO pageReqVO);

}