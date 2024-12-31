package com.macro.mall.tiny.modules.wx.controller.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.config.miniapp.WxMaConfiguration;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import com.macro.mall.tiny.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{base64Appid}")
@RequiredArgsConstructor
@Slf4j
public class WxMaUserController {


    /**
     *
     */
    private final WxUserService wxUserService;

    /**
     * 登陆接口（获取到openId及sessionKey）
     *
     * @param base64Appid base64 appid字符串
     * @param code        小程序授权码
     * @return 微信session
     */
    @GetMapping("/login")
    public CommonResult<WxMaJscode2SessionResult> login(@PathVariable String base64Appid, String code) {
        if (StringUtils.isBlank(code)) {
            return CommonResult.failed("empty jscode");
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(new String(Base64Utils.decode(base64Appid.getBytes())));
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            return CommonResult.success(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return CommonResult.failed(e.toString());
        }
    }

    /**
     * 微信登录数据
     *
     * @param wxMaUserInfo openId base64
     * @return 用户数据
     */
    @PostMapping("/wxLogin")
    public CommonResult<?> wxLogin(@RequestBody WxUser wxMaUserInfo) {
        return this.wxUserService.wxLogin(wxMaUserInfo);
    }
    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public CommonResult<String> phone(@PathVariable String base64Appid, String sessionKey, String signature,
                                      String rawData, String encryptedData, String iv, String code) throws WxErrorException {
        String appid = new String(Base64Utils.decode(base64Appid.getBytes()));
        final WxMaService wxMaService = WxMaConfiguration.getMaService(appid);

        if (!wxMaService.switchover(appid)) {
            return CommonResult.failed(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(), encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return CommonResult.success(phoneNoInfo.getPhoneNumber());
    }
}
