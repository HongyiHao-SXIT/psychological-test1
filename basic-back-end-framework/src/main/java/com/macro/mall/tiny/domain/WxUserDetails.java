package com.macro.mall.tiny.domain;

import com.macro.mall.tiny.modules.ums.model.UmsResource;
import com.macro.mall.tiny.modules.wx.model.WxUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2023/4/26.
 */
public class WxUserDetails implements UserDetails {
    private WxUser wxUser;
    private List<UmsResource> resourceList;

    public WxUserDetails(WxUser wxUser, List<UmsResource> resourceList) {
        this.wxUser = wxUser;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
            .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return Strings.EMPTY;
    }

    @Override
    public String getUsername() {
        return StringUtils.firstNonBlank(wxUser.getOpenId(),wxUser.getUsername());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public WxUser getWxUser() {
        return this.wxUser;
    }
}
