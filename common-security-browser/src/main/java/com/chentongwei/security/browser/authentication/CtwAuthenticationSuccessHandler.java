package com.chentongwei.security.browser.authentication;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.entity.SimpleResponse;
import com.chentongwei.security.core.enums.LoginType;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 为什么不实现接口，而是继承SavedRequestAwareAuthenticationSuccessHandler类的方式？
 * 因为SavedRequestAwareAuthenticationSuccessHandler这个类记住了你上一次的请求路径，比如：
 * 你请求user.html。然后被拦截到了登录页，这时候你输入完用户名密码点击登录，会自动跳转到user.html，而不是主页面。
 *
 * 若是前后分离项目则实现接口即可，因为我弄的是通用的权限组件，所以选择了继承
 *
 * @author chentongwei@bshf360.com 2018-03-26 14:09
 */
@Component("ctwAuthenticationSuccessHandler")
public class CtwAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("登录成功！");
        if (ObjectUtils.equals(securityProperties.getBrowser().getLoginType(), LoginType.JSON)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new SimpleResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), authentication)));
        } else {
            // 会帮我们跳转到上一次请求的页面上
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
