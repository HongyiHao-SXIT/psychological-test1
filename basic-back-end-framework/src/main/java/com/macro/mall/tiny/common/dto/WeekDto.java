package com.macro.mall.tiny.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 一周实体
 *
 * @author ztx
 * @version 1
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
public class WeekDto {
    private LocalDate localDate;
    private String localDateString;
    private String shortLocalDateString;
    private String zhLocalDateString;
}
