package com.sunnao.aibox.module.system.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import com.mzt.logapi.service.IParseFunction;
import com.sunnao.aibox.framework.dict.core.DictFrameworkUtils;
import com.sunnao.aibox.module.infra.enums.DictTypeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 是否类型的 {@link IParseFunction} 实现类
 *
 * @author HUIHUI
 */
@Component
@Slf4j
public class BooleanParseFunction implements IParseFunction {

    public static final String NAME = "getBoolean";

    @Override
    public boolean executeBefore() {
        return true; // 先转换值后对比
    }

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }
        return DictFrameworkUtils.parseDictDataLabel(DictTypeConstants.BOOLEAN_STRING, value.toString());
    }

}
