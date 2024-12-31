package com.macro.mall.tiny.modules.wx.controller.portal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门户网页控制器
 *
 * @author ztx
 * @version 1
 */
@Controller
@RequestMapping("/portal")
@RequiredArgsConstructor
public class PortalController {

    @RequestMapping("/index")
    public String portalIndex() {
        return "index";
    }
}
