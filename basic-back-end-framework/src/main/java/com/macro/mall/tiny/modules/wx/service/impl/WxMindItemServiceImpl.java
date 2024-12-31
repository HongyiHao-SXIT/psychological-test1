package com.macro.mall.tiny.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.mapper.WxMindItemRepository;
import com.macro.mall.tiny.modules.wx.model.WxMindItem;
import com.macro.mall.tiny.modules.wx.service.WxMindItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (WxMindItem)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMindItemServiceImpl extends ServiceImpl<WxMindItemRepository, WxMindItem> implements WxMindItemService {


    /**
     * 根据题目id获取题目明细
     *
     * @param wxMindItem 入参
     * @return 结果
     */
    @Override
    public CommonResult<List<WxMindItem>> getWxMindItemListByMindId(WxMindItem wxMindItem) {
        return CommonResult.success(this.list(Wrappers.<WxMindItem>lambdaQuery()
            .eq(WxMindItem::getMindId, wxMindItem.getMindId())
            .orderByAsc(WxMindItem::getSeq)
        ));
    }
}
