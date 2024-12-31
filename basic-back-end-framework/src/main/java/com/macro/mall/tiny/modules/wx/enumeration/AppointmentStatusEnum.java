package com.macro.mall.tiny.modules.wx.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import com.macro.mall.tiny.common.enumeration.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppointmentStatusEnum implements BaseEnum<AppointmentStatusEnum, String> {
    APPLICATION("已预约", "a"),
    CANCEL("已取消", "b"),
    FINISH("已完成", "c");

    private String name;
    @JsonValue
    private String value;

    /**
     * 根据枚举值获取枚举
     *
     * @param value 枚举值
     * @return 枚举
     */
    public AppointmentStatusEnum getEnumByValue(String value) {
        AppointmentStatusEnum[] enums = AppointmentStatusEnum.values();
        for (AppointmentStatusEnum e : enums) {
            if (e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
