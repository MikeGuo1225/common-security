package com.chentongwei.security.core.validate.code;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.util.HttpRequestUtil;
import com.chentongwei.security.core.validate.entity.IPUrlLimit;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chentongwei@bshf360.com 2018-03-27 14:03
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要被验证码拦截的url配置信息
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);

        logger.info(JSON.toJSONString(urlMap));
    }


    private static Map<String, IPUrlLimit> countMap = new HashMap<>();

    String urlString = "/hello,/hello2";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");

        String remoteAddr = HttpRequestUtil.getIpAddr(request);

        String requestURI = request.getRequestURI();

        for (String url : urls) {
            String countKey = remoteAddr + ":" + url;
            logger.info("countKey：" + countKey);
        }


        String countKey = remoteAddr + ":" + requestURI;
        logger.info("countKey：" + countKey);

        if (countMap.containsKey(countKey)) {
            IPUrlLimit ipUrlLimit = countMap.get(countKey);
            ipUrlLimit.setCount(countMap.get(countKey).getCount() + 1);
            countMap.put(countKey, ipUrlLimit);
        } else {
            countMap.put(countKey, new IPUrlLimit(0, 30));
        }

        if (countMap.get(countKey).isExpired()) {
            logger.info("countMap超时，重新计算count");
            countMap.remove(countKey);
        } else {
            logger.info("count: " + countMap.get(countKey).getCount());
            if (countMap.get(countKey).getCount() > 5) {
                logger.info("验证码走一波！key为：" + countKey);
                try {
                    validateCodeProcessorHolder.findValidateCodeProcessor(ValidateCodeType.IMAGE).validate(new ServletWebRequest(request, response));
                    logger.info("校验码校验通过");
                } catch (ValidateCodeException ex) {
                    authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
                    return;
                }
            }
        }



        ValidateCodeType type = getValidateCodeType(request);
        if (null != type) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码，验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("校验码校验通过");
            } catch (ValidateCodeException ex) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 将系统中配置的需要校验验证码的url根据校验的类型放入map
     *
     * @param url：url
     * @param type：类型
     */
    protected void addUrlToMap(String url, ValidateCodeType type) {
        if (StringUtils.isNotBlank(url)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
            for (String urlStr : urls) {
                urlMap.put(urlStr, type);
            }
        }
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request：请求
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (! StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
