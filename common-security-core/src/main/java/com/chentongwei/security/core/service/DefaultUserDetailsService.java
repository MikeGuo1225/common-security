package com.chentongwei.security.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 默认的业务逻辑，没任何作用，只是为了解决项目引入此jar的时候，若没有UserDetailsService的实现的时候启动不会出错。
 *
 * @author chentongwei@bshf360.com 2018-06-01 10:03
 */
public class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
