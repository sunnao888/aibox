package com.sunnao.aibox.module.infra.service.config;

import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.module.infra.controller.admin.config.vo.ConfigPageReqVO;
import com.sunnao.aibox.module.infra.controller.admin.config.vo.ConfigSaveReqVO;
import com.sunnao.aibox.module.infra.dal.dataobject.config.ConfigDO;
import jakarta.validation.Valid;

/**
 * 参数配置 Service 接口
 *
 * @author sunnao
 */
public interface ConfigService {

    /**
     * 创建参数配置
     *
     * @param createReqVO 创建信息
     * @return 配置编号
     */
    Long createConfig(@Valid ConfigSaveReqVO createReqVO);

    /**
     * 更新参数配置
     *
     * @param updateReqVO 更新信息
     */
    void updateConfig(@Valid ConfigSaveReqVO updateReqVO);

    /**
     * 删除参数配置
     *
     * @param id 配置编号
     */
    void deleteConfig(Long id);

    /**
     * 获得参数配置
     *
     * @param id 配置编号
     * @return 参数配置
     */
    ConfigDO getConfig(Long id);

    /**
     * 根据参数键，获得参数配置
     *
     * @param key 配置键
     * @return 参数配置
     */
    ConfigDO getConfigByKey(String key);

    /**
     * 获得参数配置分页列表
     *
     * @param reqVO 分页条件
     * @return 分页列表
     */
    PageResult<ConfigDO> getConfigPage(ConfigPageReqVO reqVO);

}
