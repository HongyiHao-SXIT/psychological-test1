package com.macro.mall.tiny.modules.wx.dto;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author ztx
 * @version 1
 */
public class ExcelDto {
    @ExcelProperty(value = {"用户id"}, index = 1)
    private String userId;
    @ExcelProperty(value = {"请假类型"}, index = 2)
    private String leaveType;
}
