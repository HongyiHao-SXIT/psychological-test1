package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.modules.wx.model.WxMindItem;
import com.macro.mall.tiny.modules.wx.service.WxMindItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (WxMindItem)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@RestController
@RequestMapping("/wxMindItem")
@RequiredArgsConstructor
public class WxMindItemController extends AbstractFrameworkController<WxMindItem, WxMindItemService> {

    /**
     * 根据题目id获取题目明细
     *
     * @param wxMindItem 入参
     * @return 结果
     */
    @GetMapping("/wxMindItemListByMindId")
    public CommonResult<List<WxMindItem>> getWxMindItemListByMindId(WxMindItem wxMindItem) {
        return this.service.getWxMindItemListByMindId(wxMindItem);
    }

    @Override
    protected Wrapper<WxMindItem> wrappersBuilder(RequestDto requestDto) {
        return Wrappers.<WxMindItem>lambdaQuery()
            .eq(StringUtils.isNotBlank(requestDto.getMindId()), WxMindItem::getMindId, requestDto.getMindId())
            .orderByAsc(WxMindItem::getSeq)
            .and(StringUtils.isNotBlank(requestDto.getKeyword()), wrapper -> wrapper
                .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMindItem::getTitle, requestDto.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMindItem::getChooseA, requestDto.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMindItem::getChooseB, requestDto.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMindItem::getChooseC, requestDto.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMindItem::getChooseD, requestDto.getKeyword())
            );
    }
}
