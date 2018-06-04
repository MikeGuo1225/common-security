package com.chentongwei.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author chentongwei@bshf360.com 2018-05-17 16:39
 */
public interface AuthorizeConfigManager {

    void config(HttpSecurity httpSecurity) throws Exception;
}
