package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxAppointment;

import java.util.List;

/**
 * (WxAppointment)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:40
 */
public interface WxAppointmentService extends IService<WxAppointment> {

    /**
     * 获取不可用的日期（指定的咨询师）
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    CommonResult<List<WxAppointment>> getDisableDateList(WxAppointment wxAppointment);
    /**
     * 提交预约信息
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    CommonResult<Void> commitAppointment(WxAppointment wxAppointment);

    /**
     * 获取用户预约列表
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    CommonResult<List<WxAppointment>> getUserAppointmentList(WxAppointment wxAppointment);

    CommonResult<List<WxAppointment>> getCounselorAppointmentList(WxAppointment wxAppointment);


}
