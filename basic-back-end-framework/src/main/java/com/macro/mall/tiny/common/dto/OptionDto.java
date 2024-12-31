package com.macro.mall.tiny.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 请求实体
 *
 * @author ztx
 * @version 1
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
public class OptionDto {
    private String label;
    private String value;
}
