package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxMind;

/**
 * (WxMind)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
public interface WxMindService extends IService<WxMind> {
    /**
     * 获取心理题总览数据
     *
     * @param wxMind 入参
     * @return 结果
     */
    CommonResult<WxMind> getMindIntro(WxMind wxMind);
}
