package com.macro.mall.tiny.modules.wx.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.domain.WxUserDetails;
import com.macro.mall.tiny.modules.wx.mapper.WxUserMapper;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import com.macro.mall.tiny.modules.wx.service.WxUserService;
import com.macro.mall.tiny.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 后台角色管理Service实现类
 * Created by macro on 2023/9/30.
 */
@Service
@RequiredArgsConstructor
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 根据openId获取用户详情
     *
     * @param openId 微信标识
     * @return 用户详情
     */
    @Override
    public UserDetails loadWxUserByOpenId(String openId) {
        //获取用户信息
        WxUser wxUser = this.getOne(Wrappers.<WxUser>lambdaQuery()
            .eq(WxUser::getOpenId, openId)
            .or()
            .eq(WxUser::getUsername, openId)
            .last("limit 1")
        );
        if (wxUser != null) {
            return new WxUserDetails(wxUser, new ArrayList<>());
        }
        throw new UsernameNotFoundException("没有找到微信用户信息");
    }

    /**
     * 微信登录数据
     *
     * @param wxMaUserInfo openId base64
     * @return 用户数据
     */
    @Override
    public CommonResult<?> wxLogin(WxUser wxMaUserInfo) {
        WxUser wxUser = this.getOne(Wrappers.<WxUser>lambdaQuery()
            .eq(WxUser::getOpenId, new String(Base64Utils.decode(wxMaUserInfo.getOpenId().getBytes()))));
        if (Objects.isNull(wxUser)) {
            //插入数据
            wxUser = WxUser.builder()
                .gender(wxMaUserInfo.getGender())
                .nickName(wxMaUserInfo.getNickName())
                .openId(new String(Base64Utils.decode(wxMaUserInfo.getOpenId().getBytes())))
                .province(wxMaUserInfo.getProvince())
                .createdDatetime(LocalDateTime.now())
                .avatarUrl(wxMaUserInfo.getAvatarUrl())
                .build();
            this.saveOrUpdate(wxUser);
        }
        UserDetails wxUserDetails = this.loadWxUserByOpenId(wxUser.getOpenId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(wxUserDetails, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtTokenUtil.generateToken(wxUserDetails);
        return CommonResult.success(token);
    }

    /**
     * 登录
     *
     * @param appUser 账号密码
     * @return 结果
     */
    @Override
    public CommonResult<String> login(WxUser appUser) {
        WxUser user = this.getOne(Wrappers.<WxUser>lambdaQuery()
            .eq(WxUser::getUsername, appUser.getUsername())
            .eq(WxUser::getPassword, appUser.getPassword())
            .last("limit 1")
        );
        if (Objects.isNull(user)) {
            return CommonResult.failed("账号或者密码错误");
        }
        WxUserDetails appUserDetails = new WxUserDetails(user, new ArrayList<>());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(appUserDetails, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtTokenUtil.generateToken(appUserDetails);
        return CommonResult.success(token);
    }

    /**
     * 注册账号
     *
     * @param appUser 入参
     * @return 结果
     */
    @Override
    public CommonResult<Void> register(WxUser appUser) {
        if (StringUtils.isAnyEmpty(appUser.getUsername(), appUser.getPassword())) {
            return CommonResult.failed("账号密码不能为空!");
        }
        long count = this.count(Wrappers.<WxUser>lambdaQuery()
            .eq(WxUser::getUsername, appUser.getUsername()));
        if (count > 0) {
            return CommonResult.failed("该账号已被注册!");
        }
        this.save(WxUser.builder()
            .username(appUser.getUsername())
            .password(appUser.getPassword())
            .nickName(RandomUtil.randomNumbers(5))
            .avatarUrl(RandomUtil.randomNumbers(5))
            .build());
        return CommonResult.success(null);
    }
}
