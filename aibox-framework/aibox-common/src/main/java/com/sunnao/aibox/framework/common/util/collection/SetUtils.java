package com.sunnao.aibox.framework.common.util.collection;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * Set 工具类
 *
 * @author sunnao
 */
public class SetUtils {

    @SafeVarargs
    public static <T> Set<T> asSet(T... objs) {
        return CollUtil.newHashSet(objs);
    }

}
