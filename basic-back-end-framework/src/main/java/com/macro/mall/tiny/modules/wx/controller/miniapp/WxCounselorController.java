package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.modules.wx.model.WxCounselor;
import com.macro.mall.tiny.modules.wx.service.WxCounselorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (WxCounselor)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@RestController
@RequestMapping("/wxCounselor")
@RequiredArgsConstructor
public class WxCounselorController extends AbstractFrameworkController<WxCounselor, WxCounselorService> {

    /**
     * 获取是否是咨询师
     *
     * @param requestDto 入参
     * @return 结果
     */
    @GetMapping("/isCounselor")
    public CommonResult<Boolean> isCounselor(RequestDto requestDto) {
        return this.service.isCounselor(requestDto);
    }

    /**
     * 上传咨询师申请
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    @PostMapping("/commitCounselor")
    public CommonResult<Void> commitCounselor(@RequestBody WxCounselor wxCounselor) {
        return this.service.commitCounselor(wxCounselor);
    }

    /**
     * 获取心理咨询师列表
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    @GetMapping("/wxAppointmentList")
    public CommonResult<List<WxCounselor>> getWxCounselorList(WxCounselor wxCounselor) {
        return this.service.getWxCounselorList(wxCounselor);
    }

    @Override
    protected Wrapper<WxCounselor> wrappersBuilder(RequestDto requestDto) {
        return Wrappers.<WxCounselor>lambdaQuery()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()),WxCounselor::getName,requestDto.getKeyword())
            .or()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()),WxCounselor::getIntro,requestDto.getKeyword());
    }
}
