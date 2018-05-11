package com.chentongwei.security.app.util;

import com.chentongwei.security.core.constant.SecurityConstant;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chentongwei@bshf360.com 2018-05-11 16:27
 */
public class PermitUrlsUtil {

    /**
     * 获取所有的无需权限即可访问的urls
     * @return
     */
    public static String[] getPermitUrls() {
        List<String> urls = new LinkedList<>();
        urls.add(SecurityConstant.DEFAULT_LOGIN_PAGE_URL);
        urls.add(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL);
        urls.add(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE);
        urls.add(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_OPENID);
        urls.add(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*");
        urls.add(SecurityConstant.DEFAULT_GET_SOCIAL_USER_INFO);
        urls.add("/social/signUp");
        urls.add("/oauth/token");
        urls.add("/favicon.ico");
        return urls.toArray(new String[urls.size()]);
    }

}
