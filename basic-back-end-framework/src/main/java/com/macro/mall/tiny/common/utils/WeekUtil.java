package com.macro.mall.tiny.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.macro.mall.tiny.common.dto.WeekDto;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 星期排班
 *
 * @author ztx
 * @version 1
 */
public class WeekUtil {
    /**
     * 获取排班星期表
     *
     * @return 星期表
     */
    public static List<WeekDto> getWeeks() {
        List<WeekDto> weeks = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weeks.add(
                WeekDto.builder()
                    .localDate(LocalDate.now().plusDays(i))
                    .localDateString(LocalDateTimeUtil.formatNormal(LocalDate.now().plusDays(i)))
                    .shortLocalDateString(LocalDateTimeUtil.format(LocalDate.now().plusDays(i), "MM-dd"))
                    .zhLocalDateString(LocalDate.now().plusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINESE))
                    .build()
            );
        }
        return weeks;
    }
}
