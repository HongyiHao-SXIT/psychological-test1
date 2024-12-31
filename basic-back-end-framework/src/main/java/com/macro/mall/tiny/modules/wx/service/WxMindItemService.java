package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxMindItem;

import java.util.List;

/**
 * (WxMindItem)服务接口
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
public interface WxMindItemService extends IService<WxMindItem> {
    /**
     * 根据题目id获取题目明细
     *
     * @param wxMindItem 入参
     * @return 结果
     */
    CommonResult<List<WxMindItem>> getWxMindItemListByMindId(WxMindItem wxMindItem);
}
