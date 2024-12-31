package com.macro.mall.tiny.common.utils;

import com.macro.mall.tiny.domain.AdminUserDetails;
import com.macro.mall.tiny.domain.WxUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 微信操作类
 *
 * @author ztx
 * @version 1
 */
public class AuthUtils {
    public static WxUserDetails getWxUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new NullPointerException("当前请求并无已认证的用户，考虑是否该请求没有做认证拦截？");
        }
        Authentication authentication = securityContext.getAuthentication();

        return (WxUserDetails) authentication.getPrincipal();
    }
    public static AdminUserDetails getAdminUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new NullPointerException("当前请求并无已认证的用户，考虑是否该请求没有做认证拦截？");
        }
        Authentication authentication = securityContext.getAuthentication();

        return (AdminUserDetails) authentication.getPrincipal();
    }

    public static Boolean isUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new NullPointerException("当前请求并无已认证的用户，考虑是否该请求没有做认证拦截？");
        }
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal() instanceof WxUserDetails;
    }

    public static Boolean isAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new NullPointerException("当前请求并无已认证的用户，考虑是否该请求没有做认证拦截？");
        }
        Authentication authentication = securityContext.getAuthentication();
        return authentication.getPrincipal() instanceof AdminUserDetails;
    }
}
