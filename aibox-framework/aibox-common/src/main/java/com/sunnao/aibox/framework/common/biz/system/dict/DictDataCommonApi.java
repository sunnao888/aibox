package com.sunnao.aibox.framework.common.biz.system.dict;

import com.sunnao.aibox.framework.common.biz.system.dict.dto.DictDataRespDTO;

import java.util.List;

/**
 * 字典数据 API 接口
 *
 * @author sunnao
 */
public interface DictDataCommonApi {

    /**
     * 获得指定字典类型的字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<DictDataRespDTO> getDictDataList(String dictType);

}
