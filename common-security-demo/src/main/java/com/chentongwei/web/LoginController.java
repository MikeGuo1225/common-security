package com.chentongwei.web;

import com.chentongwei.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chentongwei@bshf360.com 2018-03-26 14:57
 */
@RestController
@RequestMapping("/demo")
public class LoginController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping("/login")
    public UserDetails login(String username) {
        System.out.println("login进来了");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails;
    }

}
