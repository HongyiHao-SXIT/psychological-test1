package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxUserMindRecord;

import java.util.List;

/**
 * (WxUserMindRecord)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
public interface WxUserMindRecordService extends IService<WxUserMindRecord> {
    /**
     * 保存测试结果
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    CommonResult<WxUserMindRecord> saveWxUserMindRecord(WxUserMindRecord wxUserMindRecord);

    /**
     * 根据用户id获取测评记录
     *
     * @param wxUserMindRecord 入参
     * @return 结果
     */
    CommonResult<List<WxUserMindRecord>> getMindRecordByUserId(WxUserMindRecord wxUserMindRecord);
}
