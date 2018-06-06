package com.chentongwei.security.browser.session.template;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.browser.properties.SecurityProperties;
import com.chentongwei.security.core.response.ResponseEntity;
import org.apache.commons.lang.StringUtils;
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

/**
 * session处理策略，模板方法
 *
 * @author chentongwei@bshf360.com 2018-06-06 13:38
 */
public class AbstractSessionStrategyTemplate {

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
     * @param invalidSessionUrl：session失效URL
     */
    public AbstractSessionStrategyTemplate(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }

        /*
         * session失效后若配置的是跳转页面，则进行跳转，否则返回JSON
         */
        String message = "session已失效";
        if(isConcurrency()){
            message = message + "，可能是您的账号在别处登录导致的";
        }
        if (StringUtils.isNotBlank(securityProperties.getSession().getSessionInvalidUrl())) {
            logger.info(message + ",跳转到" + destinationUrl);
            redirectStrategy.sendRedirect(request, response, destinationUrl);
        }else{
            logger.info(message);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new ResponseEntity(601, message).data(null)));
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
