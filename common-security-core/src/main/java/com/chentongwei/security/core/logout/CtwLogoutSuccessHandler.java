package com.chentongwei.security.core.logout;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.entity.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理器
 *
 * @author TongWei.Chen 2018-04-06 23:54:04
 */
public class CtwLogoutSuccessHandler implements LogoutSuccessHandler {

    private String logoutSuccessUrl;

    public CtwLogoutSuccessHandler(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (! StringUtils.equals(SecurityConstant.DEFAULT_LOGIN_PAGE_URL, logoutSuccessUrl)) {
            response.sendRedirect(logoutSuccessUrl);
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(new SimpleResponse(HttpStatus.OK.value(), "退出成功", null)));
        }
    }
}
