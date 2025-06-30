package com.sunnao.aibox.module.biz.service.knowledge;

import java.util.*;
import jakarta.validation.*;
import com.sunnao.aibox.module.biz.controller.admin.knowledge.vo.*;
import com.sunnao.aibox.module.biz.dal.dataobject.knowledge.KnowledgeDO;
import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.framework.common.pojo.PageParam;

/**
 * 知识库 Service 接口
 *
 * @author sunnao
 */
public interface KnowledgeService {

    /**
     * 创建知识库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createKnowledge(@Valid KnowledgeSaveReqVO createReqVO);

    /**
     * 更新知识库
     *
     * @param updateReqVO 更新信息
     */
    void updateKnowledge(@Valid KnowledgeSaveReqVO updateReqVO);

    /**
     * 删除知识库
     *
     * @param id 编号
     */
    void deleteKnowledge(Long id);

    /**
    * 批量删除知识库
    *
    * @param ids 编号
    */
    void deleteKnowledgeListByIds(List<Long> ids);

    /**
     * 获得知识库
     *
     * @param id 编号
     * @return 知识库
     */
    KnowledgeDO getKnowledge(Long id);

    /**
     * 获得知识库分页
     *
     * @param pageReqVO 分页查询
     * @return 知识库分页
     */
    PageResult<KnowledgeDO> getKnowledgePage(KnowledgePageReqVO pageReqVO);

}