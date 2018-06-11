package com.chentongwei.security.core.authorize;

import com.chentongwei.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author chentongwei@bshf360.com 2018-05-31 13:18
 */
@Component
@Order(Integer.MIN_VALUE + 1)
public class CoreAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(
                // 读取配置文件，放开自定义路径的权限。
                getPermitUrls()
        ).permitAll();
    }

    public String[] getPermitUrls() {
        String permitUrls = securityProperties.getAuthorize().getPermitUrls();
        if (StringUtils.isNotEmpty(permitUrls) && StringUtils.isNotBlank(permitUrls)) {
            // 将配置文件读出来的url去除空白
            permitUrls = permitUrls.replace(" ", "");
            String[] urlArray = StringUtils.splitByWholeSeparator(permitUrls, ",");
            return urlArray;
        }
        return new String[] {};
    }
}
