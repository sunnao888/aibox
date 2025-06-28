package com.sunnao.aibox.module.system.api.dict;

import com.sunnao.aibox.framework.common.biz.system.dict.dto.DictDataRespDTO;
import com.sunnao.aibox.framework.common.util.object.BeanUtils;
import com.sunnao.aibox.module.system.dal.dataobject.dict.DictDataDO;
import com.sunnao.aibox.module.system.service.dict.DictDataService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 字典数据 API 实现类
 *
 * @author sunnao
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public List<DictDataRespDTO> getDictDataList(String dictType) {
        List<DictDataDO> list = dictDataService.getDictDataListByDictType(dictType);
        return BeanUtils.toBean(list, DictDataRespDTO.class);
    }

}
