package com.chentongwei.security.core.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author TongWei.Chen 2018-06-03 12:36:07
 */
@Component
@Order(Integer.MAX_VALUE - 10)
public class AnyRequestAuthenticationAuthorizeConfigProvider implements AuthorizeConfigProvider {

   @Override
   public void config(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.authorizeRequests().anyRequest().authenticated();
   }
}
