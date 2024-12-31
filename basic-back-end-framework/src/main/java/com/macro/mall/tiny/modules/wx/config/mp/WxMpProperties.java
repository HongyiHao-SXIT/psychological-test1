package com.macro.mall.tiny.modules.wx.config.mp;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * wechat mp properties
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    /**
     * 多个公众号配置信息
     */
    private List<MpConfig> configs;

    @Data
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;

        /**
         * 回调基地址
         */
        private String redirectUrl;

        /**
         * 授权后跳转地址
         */
        private String authWebUrl;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
