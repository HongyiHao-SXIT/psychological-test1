package com.macro.mall.tiny.modules.wx.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.controller.AbstractFrameworkController;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.common.utils.AuthUtils;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import com.macro.mall.tiny.modules.wx.service.WxUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 微信用户controller
 *
 * @author ztx
 * @version 1
 */
@RestController
@RequestMapping("/wxUser")
@RequiredArgsConstructor
public class WxUserController extends AbstractFrameworkController<WxUser, WxUserService> {

    private final WxUserService wxUserService;

    /**
     * 获取微信用户信息
     *
     * @return 微信用户信息
     */
    @GetMapping("/userInfo")
    public CommonResult<WxUser> getWxUser() {
        WxUser wxUser = AuthUtils.getWxUser().getWxUser();
        if (Objects.nonNull(wxUser)) {
            wxUser.setOpenId(null);
            wxUser.setId(null);
        }
        return CommonResult.success(wxUser);
    }

    /**
     * 更新用户信息
     *
     * @param wxUser 用户信息
     * @return 出参
     */
    @PutMapping("/editUser")
    public CommonResult<Void> editUser(@RequestBody WxUser wxUser) {
        this.wxUserService.updateById(WxUser.builder()
            .id(AuthUtils.getWxUser().getWxUser().getId())
            .nickName(wxUser.getNickName())
            .avatarUrl(wxUser.getAvatarUrl())
            .build());
        return CommonResult.success(null);
    }

    /**
     * 登录
     *
     * @param wxUser 账号密码
     * @return 结果
     */
    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody WxUser wxUser) {
        return this.service.login(wxUser);
    }

    /**
     * 注册账号
     *
     * @param appUser 入参
     * @return 结果
     */
    @PostMapping("/register")
    public CommonResult<Void> register(@RequestBody WxUser appUser) {
        return this.service.register(appUser);
    }

    @Override
    protected Wrapper<WxUser> wrappersBuilder(RequestDto requestDto) {
        return Wrappers.<WxUser>lambdaQuery()
            .like(StringUtils.isNotBlank(requestDto.getKeyword()), WxUser::getNickName, requestDto.getKeyword());
    }
}
