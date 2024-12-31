package com.macro.mall.tiny.modules.wx.controller.mp;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.config.mp.WxMpProperties;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import com.macro.mall.tiny.modules.wx.service.WxUserService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Edward
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wx/redirect/{baseAppid}")
public class WxRedirectController {
    private final WxMpService wxService;
    private final WxMpProperties wxMpProperties;
    private final WxUserService wxUserService;


    @RequestMapping("/greet")
    @ResponseBody
    public String greetUser(@PathVariable String baseAppid, @RequestParam String code, HttpServletResponse response) {
        String appid = new String(Base64Utils.decode(baseAppid.getBytes()));
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        try {
            WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo user = wxService.getOAuth2Service().getUserInfo(accessToken, null);
            Optional<WxMpProperties.MpConfig> mpConfigOptional = this.wxMpProperties.getConfigs().stream().filter(mp -> StringUtils.equals(mp.getAppId(), appid)).findFirst();
            if (!mpConfigOptional.isPresent()) {
                throw new WxErrorException("没有找到appid【" + appid + "】的配置信息!");
            }

            CommonResult<?> commonResult = this.wxUserService.wxLogin(WxUser.builder()
                .openId(new String(Base64Utils.encode(user.getOpenid().getBytes())))
                .avatarUrl(user.getHeadImgUrl())
                .province(user.getProvince())
                .nickName(user.getNickname())
                .gender(String.valueOf(user.getSex()))
                .build());
            if (!commonResult.isSuccess()) {
                throw new WxErrorException(commonResult.getMessage());
            }
            response.sendRedirect(mpConfigOptional.get().getAuthWebUrl() + "?token=" + commonResult.getData());
        } catch (WxErrorException | IOException e) {
            e.printStackTrace();
        }

        return "greet_user";
    }

    /**
     * 微信公众号登录转向
     *
     * @param baseAppid base64加密的appid
     * @param response  返回流
     */
    @GetMapping("/redirectMpLogin")
    public void redirectMpLogin(@PathVariable String baseAppid, HttpServletResponse response) throws IOException {

        String appid = new String(Base64Utils.decode(baseAppid.getBytes()));
        Optional<WxMpProperties.MpConfig> mpConfigOptional = this.wxMpProperties.getConfigs().stream().filter(mp -> StringUtils.equals(mp.getAppId(), appid)).findFirst();
        if (!mpConfigOptional.isPresent()) {
            throw new IOException("没有找到appid【" + appid + "】的配置信息!");
        }
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="
            + appid + "&redirect_uri="
            + mpConfigOptional.get().getRedirectUrl() + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
    }
}
