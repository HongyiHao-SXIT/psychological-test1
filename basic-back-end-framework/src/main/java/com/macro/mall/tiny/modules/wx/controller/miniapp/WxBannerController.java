package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.modules.wx.model.WxBanner;
import com.macro.mall.tiny.modules.wx.service.WxBannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (WxBanner)控制器类
 *
 * @author makejava
 * @since 2024-02-10 09:49:41
 */
@Slf4j
@RestController
@RequestMapping("/wxBanner")
@RequiredArgsConstructor
public class WxBannerController extends AbstractFrameworkController<WxBanner, WxBannerService> {
}
