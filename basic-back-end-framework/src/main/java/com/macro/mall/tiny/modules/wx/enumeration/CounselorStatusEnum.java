package com.macro.mall.tiny.modules.wx.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import com.macro.mall.tiny.common.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CounselorStatusEnum implements BaseEnum<CounselorStatusEnum, String> {
    APPLICATION("正在申请", "a"),
    PASSED("通过", "b"),
    REFUSED("拒绝", "c"),
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
    public CounselorStatusEnum getEnumByValue(String value) {
        CounselorStatusEnum[] enums = CounselorStatusEnum.values();
        for (CounselorStatusEnum e : enums) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
