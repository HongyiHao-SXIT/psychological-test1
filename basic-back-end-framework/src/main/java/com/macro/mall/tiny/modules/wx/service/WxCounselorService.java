package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.modules.wx.model.WxCounselor;

import java.util.List;

/**
 * (WxCounselor)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
public interface WxCounselorService extends IService<WxCounselor> {

    /**
     * 获取是否是咨询师
     *
     * @param requestDto 入参
     * @return 结果
     */
    CommonResult<Boolean> isCounselor(RequestDto requestDto);

    /**
     * 上传咨询师申请
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    CommonResult<Void> commitCounselor(WxCounselor wxCounselor);


    /**
     * 获取心理咨询师列表
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    CommonResult<List<WxCounselor>> getWxCounselorList(WxCounselor wxCounselor);
}
