package com.chentongwei.security.core.authorize;

import com.chentongwei.security.core.constant.SecurityConstant;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author chentongwei@bshf360.com 2018-05-17 16:36
 */
@Component
/**
 * 优先执行antMatchers里配置的权限
 */
@Order(Integer.MIN_VALUE)
public class CtwAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstant.DEFAULT_LOGIN_PAGE_URL,
                SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                SecurityConstant.DEFAULT_GET_SOCIAL_USER_INFO
        ).permitAll();
    }
}
