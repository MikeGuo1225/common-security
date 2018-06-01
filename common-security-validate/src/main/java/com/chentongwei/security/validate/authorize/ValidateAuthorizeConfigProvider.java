package com.chentongwei.security.validate.authorize;

import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import com.chentongwei.security.validate.constants.ValidateCodeConstants;
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
public class ValidateAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                ValidateCodeConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*"
        ).permitAll();
    }
}
