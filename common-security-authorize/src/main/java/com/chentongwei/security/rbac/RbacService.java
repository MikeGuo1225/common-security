package com.chentongwei.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chentongwei@bshf360.com 2018-05-18 14:01
 */
public interface RbacService {

    /**
     * 检查用户是否有权限
     *
     * @param request：请求
     * @param authentication：身份
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
