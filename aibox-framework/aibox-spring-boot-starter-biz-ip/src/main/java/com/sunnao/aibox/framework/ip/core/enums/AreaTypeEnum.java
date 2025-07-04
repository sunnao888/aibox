package com.sunnao.aibox.framework.ip.core.enums;

import com.sunnao.aibox.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 区域类型枚举
 *
 * @author sunnao
 */
@AllArgsConstructor
@Getter
public enum AreaTypeEnum implements ArrayValuable<Integer> {

    COUNTRY(1, "国家"),
    PROVINCE(2, "省份"),
    CITY(3, "城市"),
    DISTRICT(4, "地区"), // 县、镇、区等
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(AreaTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名字
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }
}
