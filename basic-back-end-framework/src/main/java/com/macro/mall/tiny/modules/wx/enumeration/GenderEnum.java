package com.macro.mall.tiny.modules.wx.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import com.macro.mall.tiny.common.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum implements BaseEnum<GenderEnum, String> {
    MALE("男", "a"),
    FEMALE("女", "b")
    ;

    private String name;
    @JsonValue
    private String value;

    /**
     * 根据枚举值获取枚举
     *
     * @param value 枚举值
     * @return 枚举
     */
    public GenderEnum getEnumByValue(String value) {
        GenderEnum[] enums = GenderEnum.values();
        for (GenderEnum e : enums) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
