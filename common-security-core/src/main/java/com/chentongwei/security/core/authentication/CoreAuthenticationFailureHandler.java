package com.chentongwei.security.core.authentication;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.enums.LoginTypeEnum;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.response.ResponseEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
public class CoreAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("验证失败！");

        if (Objects.equals(securityProperties.getAuthentication().getLoginType(), LoginTypeEnum.JSON)) {
            ResponseEntity result = new ResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()
            ).data(null);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(result));
        } else {
            response.setContentType("text/html;charset=UTF-8");
            // 判断是否有自定义错误页面,有则失败跳转到自定义页面。
            if (StringUtils.isNotBlank(securityProperties.getAuthentication().getLoginErrorPage())) {
                // 将异常信息存到session对象里，若前后不分离的话，则可以从中取出异常信息展示到页面上。
                // 若前后分离，建议采取JSON的方式。
                request.getSession().setAttribute("exception", exception);
                redirectStrategy.sendRedirect(request, response, securityProperties.getAuthentication().getLoginErrorPage());
            } else {
                super.onAuthenticationFailure(request, response, exception);
            }
        }
    }
}
