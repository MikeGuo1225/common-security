package com.chentongwei.security.validate.authorize;

import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import com.chentongwei.security.validate.constants.ValidateCodeConstants;
import com.chentongwei.security.validate.enums.DefaultLoginProcessingUrlEnum;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

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
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(
                getPermitUrls()
        ).permitAll();
    }

    /**
     * 获取所有的无需权限即可访问的urls
     * @return
     */
    public static String[] getPermitUrls() {
        List<String> urls = new LinkedList<>();
        urls.add(ValidateCodeConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*");
        urls.add(ValidateCodeConstants.DEFAULT_UNAUTHENTICATION_URL);
        urls.add(DefaultLoginProcessingUrlEnum.MOBILE.url());
        urls.add(DefaultLoginProcessingUrlEnum.FORM.url());
        return urls.toArray(new String[urls.size()]);
    }
}
