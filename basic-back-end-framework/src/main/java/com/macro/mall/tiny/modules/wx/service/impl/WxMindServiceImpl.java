package com.macro.mall.tiny.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.mapper.WxMindRepository;
import com.macro.mall.tiny.modules.wx.model.WxMind;
import com.macro.mall.tiny.modules.wx.model.WxMindItem;
import com.macro.mall.tiny.modules.wx.service.WxMindItemService;
import com.macro.mall.tiny.modules.wx.service.WxMindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (WxMind)服务接口实现类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WxMindServiceImpl extends ServiceImpl<WxMindRepository, WxMind> implements WxMindService {


    private final WxMindItemService wxMindItemService;

    /**
     * 获取心理题总览数据
     *
     * @param wxMind 入参
     * @return 结果
     */
    @Override
    public CommonResult<WxMind> getMindIntro(WxMind wxMind) {
        WxMind mind = this.getById(wxMind.getId());
        if (Objects.isNull(mind)) {
            return CommonResult.failed("心理题不存在!");
        }
        // 写上数量
        mind.setItemNum(this.wxMindItemService.count(Wrappers.<WxMindItem>lambdaQuery()
            .eq(WxMindItem::getMindId, wxMind.getId())));
        return CommonResult.success(mind);
    }
}
