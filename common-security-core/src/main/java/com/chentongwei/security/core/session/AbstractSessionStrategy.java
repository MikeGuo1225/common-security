package com.chentongwei.security.core.session;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.entity.SimpleResponse;
import com.chentongwei.security.core.enums.SessionInvalidType;
import com.chentongwei.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 通用session处理策略，模板方法
 *
 * @author TongWei.Chen 2018-04-06 17:41:20
 */
public class AbstractSessionStrategy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 跳转的url
     */
    private String destinationUrl;
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param invalidSessionUrl
     */
    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }

        if (Objects.equals(SessionInvalidType.REDIRECT, securityProperties.getSession().getSessionInvalidType())) {
            logger.info("session失效,跳转到"+destinationUrl);
            redirectStrategy.sendRedirect(request, response, destinationUrl);
        }else{
            String message = "session已失效";
            if(isConcurrency()){
                message = message + "，可能是您的账号在别处登录导致的";
            }
            logger.info(message);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new SimpleResponse(601, message, null)));
        }

    }

    /**
     * session失效是否是并发导致的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
