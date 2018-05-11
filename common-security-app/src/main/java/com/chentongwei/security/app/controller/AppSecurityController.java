package com.chentongwei.security.app.controller;

import com.chentongwei.security.app.social.signup.AppSignUpUtils;
import com.chentongwei.security.core.social.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chentongwei@bshf360.com 2018-05-08 20:42
 */
@RestController
public class AppSecurityController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private AppSignUpUtils appSignUpUtils;

    /**
     * 注册
     * 返回401错误码是为了让前端判断401来标志是注册
     * @param request：请求
     * @return
     */
    @GetMapping("/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        // 跳转到注册页之前就已经放到了session里了，这是SpringSocial做的，可以debug看源码
        Connection<?> connection = providerSignInUtils.getConnectionFromSession((new ServletWebRequest(request)));
        if (null == connection) {
            return new SocialUserInfo();
        }
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());

        appSignUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

        return userInfo;
    }

}
