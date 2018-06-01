/*
package com.chentongwe.web;

import com.alibaba.fastjson.JSON;
import com.chentongwe.entity.User;
import com.chentongwe.security.MyUserDetailsService;
import com.chentongwei.security.app.social.signup.AppSignUpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * @author chentongwei@bshf360.com 2018-03-26 14:57
 *//*

@RestController
@RequestMapping("/demo")
public class LoginController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/regist")
    public void regist(User user, HttpServletRequest request) {

        //不管是注册还是绑定用户，都会拿到用户的唯一标志
        // 假设getUsername就是唯一标志

        // 跳转到注册页之前就已经放到了session里了，这是SpringSocial做的，可以debug看源码
        Connection<?> connection = providerSignInUtils.getConnectionFromSession((new ServletWebRequest(request)));
        System.out.println(JSON.toJSONString(connection));

        String userId = user.getUsername();
        appSignUpUtils.doPostSignUp(request, userId);
    }

    @RequestMapping("/login")
    public UserDetails login(String username) {
        System.out.println("login进来了");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails;
    }

}*/
