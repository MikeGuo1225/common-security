package com.chentongwei.security.browser.authentication;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.entity.SimpleResponse;
import com.chentongwei.security.core.enums.LoginType;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义失败处理器
 *
 * @author chentongwei@bshf360.com 2018-03-26 14:02
 */
@Component("ctwAuthenticationFailureHandler")
public class CtwAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败！");

        if (Objects.equals(securityProperties.getBrowser().getLoginType(), LoginType.JSON)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null)));
        } else {
            response.setContentType("text/html;charset=UTF-8");
            // TODO 失败跳转到自定义页面。
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
