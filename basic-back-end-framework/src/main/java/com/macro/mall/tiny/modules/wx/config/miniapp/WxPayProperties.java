package com.macro.mall.tiny.modules.wx.config.miniapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wxpay pay properties.
 *
 * @author Binary Wang
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {
  /**
   * 设置小程序appid
   */
  private String appId;

  /**
   * 公众号appid
   */
  private String h5Appid;

  /**
   * 微信支付商户号
   */
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  private String mchKey;

  /**
   * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
   */
  private String subAppId;

  /**
   * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
   */
  private String subMchId;

  /**
   * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
   */
  private String keyPath;

  /**
   * 域名
   */
  private String notifyUrl;

  private String apiV3Key;

  private String privateKeyPath;

  private String privateCertPath;

}
