package com.chentongwei.security.core.controller;

import com.chentongwei.security.core.constant.CoreConstants;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.response.ResponseEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录控制器
 *
 * @author chentongwei@bshf360.com 2018-03-26 11:14
 */
@RestController
public class LoginPageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping(CoreConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /** 若没自定义授权页面，则返回统一格式的JSON，否则跳转到自定义未授权页面 */
        if (StringUtils.isBlank(securityProperties.getAuthorize().getUnAuthorizePage())) {
            logger.info("返回的是状态码401的JSON");
            return new ResponseEntity(
                    HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()
            ).data(null);
        } else {
            redirectStrategy.sendRedirect(request, response, securityProperties.getAuthorize().getUnAuthorizePage());
            logger.info("跳转到自定义授权页面【{}】", securityProperties.getAuthorize().getUnAuthorizePage());
            return null;
        }
    }
}