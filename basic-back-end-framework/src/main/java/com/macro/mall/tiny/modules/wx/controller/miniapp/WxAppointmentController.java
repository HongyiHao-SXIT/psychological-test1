package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.macro.mall.tiny.common.aop.EnumOrDictTrans;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.modules.wx.model.WxAppointment;
import com.macro.mall.tiny.modules.wx.service.WxAppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (WxAppointment)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:37
 */
@Slf4j
@RestController
@RequestMapping("/wxAppointment")
@RequiredArgsConstructor
public class WxAppointmentController extends AbstractFrameworkController<WxAppointment, WxAppointmentService> {


    /**
     * 获取不可用的日期（指定的咨询师）
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @GetMapping("/disableDateList")
    public CommonResult<List<WxAppointment>> getDisableDateList(WxAppointment wxAppointment) {
        return this.service.getDisableDateList(wxAppointment);
    }

    /**
     * 提交预约信息
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @PostMapping("/commitAppointment")
    public CommonResult<Void> commitAppointment(@RequestBody WxAppointment wxAppointment) {
        return this.service.commitAppointment(wxAppointment);
    }

    /**
     * 获取用户预约列表
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @GetMapping("/userAppointmentList")
    public CommonResult<List<WxAppointment>> getUserAppointmentList(WxAppointment wxAppointment) {
        return this.service.getUserAppointmentList(wxAppointment);
    }


    /**
     * 获取咨询师预约列表
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @GetMapping("/counselorAppointmentList")
    @EnumOrDictTrans
    public CommonResult<List<WxAppointment>> getCounselorAppointmentList(WxAppointment wxAppointment) {
        return this.service.getCounselorAppointmentList(wxAppointment);
    }

}
