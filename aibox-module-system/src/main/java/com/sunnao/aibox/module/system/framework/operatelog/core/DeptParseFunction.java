package com.sunnao.aibox.module.system.framework.operatelog.core;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.mzt.logapi.service.IParseFunction;
import com.sunnao.aibox.module.system.dal.dataobject.dept.DeptDO;
import com.sunnao.aibox.module.system.service.dept.DeptService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 部门名字的 {@link IParseFunction} 实现类
 *
 * @author HUIHUI
 */
@Slf4j
@Component
public class DeptParseFunction implements IParseFunction {

    public static final String NAME = "getDeptById";

    @Resource
    private DeptService deptService;

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }

        // 获取部门信息
        DeptDO dept = deptService.getDept(Convert.toLong(value));
        if (dept == null) {
            log.warn("[apply][获取部门{{}}为空", value);
            return "";
        }
        return dept.getName();
    }

}
