package com.macro.mall.tiny.modules.wx.excelConverter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.macro.mall.tiny.modules.wx.enumeration.CounselorStatusEnum;

/**
 * excel枚举类转换
 *
 * @author ztx
 * @version 1
 */
public class WxUserClassProStatusEnumConverter implements Converter<CounselorStatusEnum> {
    @Override
    public Class<CounselorStatusEnum> supportJavaTypeKey() {
        return CounselorStatusEnum.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 导入时转换
     *
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CounselorStatusEnum convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);
    }

    /**
     * 导出时转换
     *
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(CounselorStatusEnum value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.getName());
    }

}
