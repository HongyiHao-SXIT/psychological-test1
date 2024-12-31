package com.macro.mall.tiny.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 后台角色管理Service
 * Created by macro on 2023/9/30.
 */
public interface WxUserService extends IService<WxUser> {

    /**
     * 根据openId获取用户详情
     * @param openId 微信标识
     * @return 用户详情
     */
    UserDetails loadWxUserByOpenId(String openId);
    /**
     * 微信登录数据
     *
     * @param wxMaUserInfo openId base64
     * @return 用户数据
     */
    CommonResult<?> wxLogin(@RequestBody WxUser wxMaUserInfo);

    /**
     * 登录
     *
     * @param appUser 账号密码
     * @return 结果
     */
    CommonResult<String> login(WxUser appUser);

    /**
     * 注册账号
     *
     * @param appUser 入参
     * @return 结果
     */
    CommonResult<Void> register(WxUser appUser);

}
