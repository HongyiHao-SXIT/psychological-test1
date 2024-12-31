package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.modules.wx.model.WxMind;
import com.macro.mall.tiny.modules.wx.model.WxMindItem;
import com.macro.mall.tiny.modules.wx.service.WxMindItemService;
import com.macro.mall.tiny.modules.wx.service.WxMindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (WxMind)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:42
 */
@Slf4j
@RestController
@RequestMapping("/wxMind")
@RequiredArgsConstructor
public class WxMindController extends AbstractFrameworkController<WxMind, WxMindService> {

    private final WxMindItemService wxMindItemService;


    /**
     * 获取心理题总览数据
     *
     * @param wxMind 入参
     * @return 结果
     */
    @GetMapping("/mindIntro")
    public CommonResult<WxMind> getMindIntro(WxMind wxMind) {
        return this.service.getMindIntro(wxMind);
    }


    @Override
    protected CommonResult<WxMind> beforeDelete(WxMind model) {
        // 删除之前看下时候还有子项目
        long count = this.wxMindItemService.count(Wrappers.<WxMindItem>lambdaQuery()
            .eq(WxMindItem::getMindId, model.getId())
        );
        if (count > 0) {
            return CommonResult.failed("不能删除，请先删除子题目");
        }
        return super.beforeDelete(model);
    }

    @Override
    protected Wrapper<WxMind> wrappersBuilder(RequestDto requestDto) {
        return Wrappers.<WxMind>lambdaQuery()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMind::getTitle, requestDto.getKeyword())
            .or()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMind::getIntro, requestDto.getKeyword())
            .or()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxMind::getContent, requestDto.getKeyword());
    }
}
