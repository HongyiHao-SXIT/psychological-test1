package com.macro.mall.tiny.common.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.dto.OptionDto;
import com.macro.mall.tiny.common.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举类控制器
 *
 * @author ztx
 * @version 1
 */
@RestController()
@RequestMapping("/enum")
@RequiredArgsConstructor
public class EnumController {

    /**
     * 获取枚举类选项列表
     *
     * @param requestDto 请求实体
     * @return 选项列表
     */
    @GetMapping("/options")
    public CommonResult<List<OptionDto>> getEnumOptions(RequestDto requestDto) {
        List<OptionDto> list = new ArrayList<>();

        try {
            Class<?> aClass = Class.forName(requestDto.getKeyword());
            if (!aClass.isEnum()) {
                return CommonResult.failed("【" + requestDto.getKeyword() + "】不是枚举类");
            }
            Object[] enumConstants = aClass.getEnumConstants();
            Class<Enum> enumClass = (Class<Enum>) aClass;
            for (Object object : enumConstants) {
                Method valueMethod = enumClass.getMethod("getValue");
                OptionDto optionDto = OptionDto.builder()
                    .value(valueMethod.invoke(object).toString())
                    .build();
                Method method = enumClass.getMethod("getName");
                optionDto.setLabel(method.invoke(object).toString());
                list.add(optionDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResult.failed("没有找到【" + requestDto.getKeyword() + "】的枚举类");
        }
        return CommonResult.success(list);
    }

}
