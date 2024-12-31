package com.macro.mall.tiny.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.aop.EnumOrDictTrans;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.utils.AuthUtils;
import com.macro.mall.tiny.modules.wx.enumeration.AppointmentStatusEnum;
import com.macro.mall.tiny.modules.wx.mapper.WxAppointmentRepository;
import com.macro.mall.tiny.modules.wx.model.WxAppointment;
import com.macro.mall.tiny.modules.wx.model.WxCounselor;
import com.macro.mall.tiny.modules.wx.service.WxAppointmentService;
import com.macro.mall.tiny.modules.wx.service.WxCounselorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * (WxAppointment)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxAppointmentServiceImpl extends ServiceImpl<WxAppointmentRepository, WxAppointment> implements WxAppointmentService {

    private final WxCounselorService wxCounselorService;


    /**
     * 获取不可用的日期（指定的咨询师）
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxAppointment>> getDisableDateList(WxAppointment wxAppointment) {
        return CommonResult.success(this.list(Wrappers.<WxAppointment>lambdaQuery()
            .eq(WxAppointment::getCounselorUserId, wxAppointment.getCounselorUserId())
            .ne(WxAppointment::getStatus, AppointmentStatusEnum.CANCEL)
            .ge(WxAppointment::getApplicationDate, LocalDate.now())
        ));
    }

    /**
     * 提交预约信息
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @Override
    public CommonResult<Void> commitAppointment(WxAppointment wxAppointment) {
        // 预约日期必须是当前日期之后
        if (wxAppointment.getApplicationDate().isBefore(LocalDate.now())) {
            return CommonResult.failed("预约日期必须是当前日期之后");
        }
        WxCounselor counselor = this.wxCounselorService.getById(wxAppointment.getCounselorId());
        if (Objects.isNull(counselor)) {
            return CommonResult.failed("咨询师不存在");
        }

        // 查询咨询师在预约日期是否有非取消订单
        long count = this.count(Wrappers.<WxAppointment>lambdaQuery()
            .eq(WxAppointment::getCounselorId, wxAppointment.getCounselorId())
            .eq(WxAppointment::getApplicationDate, wxAppointment.getApplicationDate())
            .ne(WxAppointment::getStatus, AppointmentStatusEnum.CANCEL)
        );
        if (count > 0) {
            return CommonResult.failed("当前咨询师在预约日期已有未取消的订单，请选择其他日期");
        }
        // 可以插入了
        this.save(WxAppointment.builder()
            .counselorId(wxAppointment.getCounselorId())
            .counselorUserId(counselor.getUserId())
            .applicationDate(wxAppointment.getApplicationDate())
            .status(AppointmentStatusEnum.APPLICATION)
            .name(wxAppointment.getName())
            .phone(wxAppointment.getPhone())
            .gender(wxAppointment.getGender())
            .userId(AuthUtils.getWxUser().getWxUser().getId())
            .build());
        return CommonResult.success(null);
    }

    /**
     * 获取用户预约列表
     *
     * @param wxAppointment 入参
     * @return 结果
     */
    @Override
    @EnumOrDictTrans
    public CommonResult<List<WxAppointment>> getUserAppointmentList(WxAppointment wxAppointment) {
        List<WxAppointment> wxAppointmentList = this.list(Wrappers.<WxAppointment>lambdaQuery()
            .eq(WxAppointment::getUserId, AuthUtils.getWxUser().getWxUser().getId())
            .eq(Objects.nonNull(wxAppointment.getStatus()), WxAppointment::getStatus, wxAppointment.getStatus())
            .orderByDesc(WxAppointment::getApplicationDate)
        );
        wxAppointmentList.forEach(item -> {
            // 查询咨询师信息
            WxCounselor wxCounselor = this.wxCounselorService.getById(item.getCounselorId());
            item.setWxCounselor(Objects.nonNull(wxCounselor) ? wxCounselor : WxCounselor.builder()
                .imageUrl("")
                .name("咨询师已删除")
                .build());

        });
        return CommonResult.success(wxAppointmentList);
    }

    @Override
    public CommonResult<List<WxAppointment>> getCounselorAppointmentList(WxAppointment wxAppointment) {
        List<WxAppointment> wxAppointmentList = this.list(Wrappers.<WxAppointment>lambdaQuery()
            .eq(WxAppointment::getCounselorUserId, AuthUtils.getWxUser().getWxUser().getId())
            .eq(Objects.nonNull(wxAppointment.getStatus()), WxAppointment::getStatus, wxAppointment.getStatus())
            .orderByDesc(WxAppointment::getApplicationDate)
        );
        return CommonResult.success(wxAppointmentList);
    }
}
