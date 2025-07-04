package com.sunnao.aibox.module.infra.convert.config;

import com.sunnao.aibox.framework.common.pojo.PageResult;
import com.sunnao.aibox.module.infra.controller.admin.config.vo.ConfigRespVO;
import com.sunnao.aibox.module.infra.controller.admin.config.vo.ConfigSaveReqVO;
import com.sunnao.aibox.module.infra.dal.dataobject.config.ConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<ConfigDO> page);

    List<ConfigRespVO> convertList(List<ConfigDO> list);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(ConfigDO bean);

    @Mapping(source = "key", target = "configKey")
    ConfigDO convert(ConfigSaveReqVO bean);

}
