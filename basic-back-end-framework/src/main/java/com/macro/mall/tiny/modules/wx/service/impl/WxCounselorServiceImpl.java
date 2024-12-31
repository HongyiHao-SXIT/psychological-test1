package com.macro.mall.tiny.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.common.utils.AuthUtils;
import com.macro.mall.tiny.modules.wx.enumeration.CounselorStatusEnum;
import com.macro.mall.tiny.modules.wx.mapper.WxCounselorRepository;
import com.macro.mall.tiny.modules.wx.model.WxCounselor;
import com.macro.mall.tiny.modules.wx.service.WxCounselorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (WxCounselor)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxCounselorServiceImpl extends ServiceImpl<WxCounselorRepository, WxCounselor> implements WxCounselorService {


    /**
     * 获取是否是咨询师
     *
     * @param requestDto 入参
     * @return 结果
     */
    @Override
    public CommonResult<Boolean> isCounselor(RequestDto requestDto) {
        // 查询是否存在申请记录
        WxCounselor wxCounselor = this.getOne(Wrappers.<WxCounselor>lambdaQuery()
            .eq(WxCounselor::getUserId, AuthUtils.getWxUser().getWxUser().getId())
            .last("limit 1")
        );
        if (Objects.isNull(wxCounselor)) {
            // 没有申请过
            return CommonResult.success(false);
        }
        // 已经申请过 看是否已通过
        if (CounselorStatusEnum.PASSED.equals(wxCounselor.getStatus())) {
            return CommonResult.success(true);
        }
        // 未通过
        return CommonResult.failed("您的申请审核结果为" + wxCounselor.getStatus().getName());
    }

    /**
     * 上传咨询师申请
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    @Override
    public CommonResult<Void> commitCounselor(WxCounselor wxCounselor) {
        // 检查是否已经提交过申请
        WxCounselor counselor = this.getOne(Wrappers.<WxCounselor>lambdaQuery()
            .eq(WxCounselor::getUserId, AuthUtils.getWxUser().getWxUser().getId())
            .last("limit 1")
        );
        if (Objects.nonNull(counselor)) {
            return CommonResult.failed("您已经提交过申请，请勿重复提交！");
        }
        wxCounselor.setUserId(AuthUtils.getWxUser().getWxUser().getId());
        wxCounselor.setStatus(CounselorStatusEnum.APPLICATION);
        this.save(wxCounselor);
        return CommonResult.success(null);
    }

    /**
     * 获取心理咨询师列表
     *
     * @param wxCounselor 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxCounselor>> getWxCounselorList(WxCounselor wxCounselor) {
        return CommonResult.success(this.list(Wrappers.<WxCounselor>lambdaQuery()
            .eq(WxCounselor::getStatus, CounselorStatusEnum.PASSED)
            .orderByDesc(WxCounselor::getCreatedDatetime)
        ));
    }
}
