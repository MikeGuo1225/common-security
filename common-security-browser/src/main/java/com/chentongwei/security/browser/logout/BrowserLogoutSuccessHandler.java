package com.chentongwei.security.browser.logout;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.response.ResponseEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author chentongwei@bshf360.com 2018-06-04 16:27
 */
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String logoutSuccessUrl;

    public BrowserLogoutSuccessHandler(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*
         * 如果配置了退出到的页面，则跳转到自定义的页面上去。否则返回JSON
         */
        if (StringUtils.isNotBlank(logoutSuccessUrl)) {
            logger.info("退出成功，跳转到了【{}】", logoutSuccessUrl);
            response.sendRedirect(logoutSuccessUrl);
        } else {
            logger.info("退出成功，成功的返回了JSON！");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(new ResponseEntity(HttpStatus.OK.value(), "退出成功").data(null)));
        }
    }
}
