package com.chentongwei.security.validate.code;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.core.response.ResponseEntity;
import com.chentongwei.security.validate.authorize.ipurl.IPUtil;
import com.chentongwei.security.validate.authorize.ipurl.IPUrlLimit;
import com.chentongwei.security.validate.enums.DefaultLoginProcessingUrlEnum;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import com.chentongwei.security.validate.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 验证码过滤器，入口类
 *
 * @author chentongwei@bshf360.com 2018-05-28 16:22
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
    private Map<String, ValidateCodeTypeEnum> validateUrlMap = new HashMap<>();

    /**
     * 存放ip访问url次数
     */
    private static Map<String, IPUrlLimit> countMap = new HashMap<>();

    /**
     * ip一段时间内访问url多少次数后弹出验证码的url
     */
    private String ipValidateUrl;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        /**
         * 将系统中配置的需要校验验证码的url根据校验的类型放入map
         * 图片
         * 短信
         * 极验
         */

        // 图片验证码
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeTypeEnum.IMAGE);

        // 验证码
        addUrlToMap(securityProperties.getCode().getGeetest() .getUrl(), ValidateCodeTypeEnum.GEETEST);

        // 短信验证码
        validateUrlMap.put(DefaultLoginProcessingUrlEnum.MOBILE.url(), ValidateCodeTypeEnum.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeTypeEnum.SMS);

        // 获取需要验证访问次数的URL
        ipValidateUrl = securityProperties.getAuthorize().getIpValidateUrl();
        logger.info("ipValidateUrl：【{}】" , ipValidateUrl);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeTypeEnum type = getValidateCodeType(request);
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
        // 检查ip访问url次数限制
        boolean visitCountFlag = visitCountValidate(request, response);
        if (! visitCountFlag) {
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 将系统中配置的需要校验验证码的url根据校验的类型放入map
     *
     * @param url：url
     * @param type：类型
     */
    private void addUrlToMap(String url, ValidateCodeTypeEnum type) {
        if (StringUtils.isNotBlank(url)) {
            // 去空白
            url = url.replace(" ", "");
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(url, ",");
            for (String urlStr : urls) {
                validateUrlMap.put(urlStr, type);
            }
        }
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request：请求
     * @return
     */
    private ValidateCodeTypeEnum getValidateCodeType(HttpServletRequest request) {
        ValidateCodeTypeEnum result = null;
        Set<String> urls = validateUrlMap.keySet();
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                result = validateUrlMap.get(url);
            }
        }
        return result;
    }

    /**
     * 检查ip访问url次数限制
     *
     * @param request：请求
     * @param response：响应
     */
    private boolean visitCountValidate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (StringUtils.isNotBlank(ipValidateUrl) && ipValidateUrl.indexOf(requestURI) != -1) {
            String ipAddr = IPUtil.getIpAddr(request);
            String countKey = ipAddr + ":" + requestURI;
            logger.info("countKey：" + countKey);
            if (null != countMap.get(countKey) && countMap.get(countKey).isExpired()) {
                logger.info("countMap超时，重新计算count");
                countMap.remove(countKey);
            }
            if (countMap.containsKey(countKey)) {
                countMap.put(countKey, countMap.get(countKey).count(1));
            } else {
                countMap.put(countKey, new IPUrlLimit(securityProperties.getAuthorize().getIpValidateSeconds()));
            }

            logger.info("count: " + countMap.get(countKey).getCount());
            if (countMap.get(countKey).getCount() > securityProperties.getAuthorize().getIpValidateCount()) {
                logger.info("验证码走一波！key为：" + countKey);

                ResponseEntity result = new ResponseEntity(
                        100000, "您访问的太快啦，休息几秒钟吧！"
                ).data(null);

                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSON.toJSONString(result));
                return false;
            }
        }
        return true;
    }

}
