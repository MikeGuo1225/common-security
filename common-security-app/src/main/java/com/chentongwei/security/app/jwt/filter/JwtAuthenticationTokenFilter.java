package com.chentongwei.security.app.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.app.enums.JwtRedisEnum;
import com.chentongwei.security.app.enums.JwtUrlEnum;
import com.chentongwei.security.app.jwt.util.JwtTokenUtil;
import com.chentongwei.security.app.properties.SecurityProperties;
import com.chentongwei.security.core.authorize.CoreAuthorizeConfigProvider;
import com.chentongwei.security.core.response.ResponseEntity;
import com.chentongwei.security.validate.authorize.ValidateAuthorizeConfigProvider;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * JWT过滤器
 *
 * @author chentongwei@bshf360.com 2018-06-08 14:31
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CoreAuthorizeConfigProvider coreAuthorizeConfigProvider;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("请求路径：【{}】，请求方式为：【{}】", request.getRequestURI(), request.getMethod());

        if (antPathMatcher.match("/favicon.ico", request.getRequestURI())) {
            logger.info("jwt不拦截此路径：【{}】，请求方式为：【{}】", request.getRequestURI(), request.getMethod());
            filterChain.doFilter(request, response);
            return;
        }

        if (! securityProperties.getJwt().isPreventsGetMethod()) {
            if (Objects.equals(RequestMethod.GET.toString(), request.getMethod())) {
                logger.info("jwt不拦截此路径因为开启了不拦截GET请求：【{}】，请求方式为：【{}】", request.getRequestURI(), request.getMethod());
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 排除路径，并且如果是options请求是cors跨域预请求，设置allow对应头信息
        String[] permitUrls = getPermitUrls();
        for (int i = 0, length = permitUrls.length; i < length; i ++) {
            if (antPathMatcher.match(permitUrls[i], request.getRequestURI())
                    || Objects.equals(RequestMethod.OPTIONS.toString(), request.getMethod())) {
                logger.info("jwt不拦截此路径：【{}】，请求方式为：【{}】", request.getRequestURI(), request.getMethod());
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 获取Authorization
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader) || ! authHeader.startsWith("Bearer ")) {
            logger.error("Authorization的开头不是Bearer，Authorization===>【{}】", authHeader);
            responseEntity(response, HttpStatus.UNAUTHORIZED.value(), "暂无权限！");
            return;
        }

        // 截取token
        String authToken = authHeader.substring("Bearer ".length());

        // 判断token是否失效
        if (jwtTokenUtil.isTokenExpired(authToken)) {
            logger.info("token已过期！");
            responseEntity(response, HttpStatus.UNAUTHORIZED.value(), "token已过期！");
            return;
        }

        String randomKey = jwtTokenUtil.getMd5KeyFromToken(authToken);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        // 验证token是否合法
        if (StringUtils.isBlank(username) || StringUtils.isBlank(randomKey)) {
            logger.info("username【{}】或randomKey【{}】可能为null！", username, randomKey);
            responseEntity(response, HttpStatus.UNAUTHORIZED.value(), "暂无权限！");
            return;
        }

        // 验证token是否存在（过期了也会消失）
        Object token = redisTemplate.opsForValue().get(JwtRedisEnum.getTokenKey(username, randomKey));
        if (Objects.isNull(token)) {
            logger.info("Redis里没查到key【{}】对应的value！", JwtRedisEnum.getTokenKey( username, randomKey));
            responseEntity(response, HttpStatus.UNAUTHORIZED.value(), "token已过期！");
            return;
        }

        // 判断传来的token和存到redis的token是否一致
        if (! Objects.equals(token.toString(), authToken)) {
            logger.error("前端传来的token【{}】和redis里的token【{}】不一致！", authToken, token.toString());
            responseEntity(response, HttpStatus.UNAUTHORIZED.value(), "暂无权限！");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authenticationStr = redisTemplate.opsForValue().get(JwtRedisEnum.getAuthenticationKey(username, randomKey));
            Authentication authentication = JSON.parseObject(authenticationStr, Authentication.class);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // token过期时间
        long tokenExpireTime = jwtTokenUtil.getExpirationDateFromToken(authToken).getTime();
        // token还剩余多少时间过期
        long surplusExpireTime = (tokenExpireTime - System.currentTimeMillis()) / 1000;
        logger.info("surplusExpireTime:" + surplusExpireTime);

        /*
         * 退出登录不刷新token，因为假设退出登录操作，刷新token了，这样清除的是旧的token，相当于根本没退出成功
         */
        if (! Objects.equals(request.getRequestURL(), JwtUrlEnum.LOGOUT.url())) {
            // token过期时间小于等于多少秒，自动刷新token
            if (surplusExpireTime <= securityProperties.getJwt().getAutoRefreshTokenExpiration()) {
                // 1.删除之前的token
                redisTemplate.delete(JwtRedisEnum.getTokenKey(username, randomKey));

                //2.重新生成token
                // 重新生成randomKey，放到header以及redis
                String newRandomKey = jwtTokenUtil.getRandomKey();
                // 重新生成token，放到header以及redis
                String newAuthToken = jwtTokenUtil.refreshToken(authToken, newRandomKey);
                response.setHeader("Authorization", "Bearer " + newAuthToken);
                response.setHeader("randomKey", newRandomKey);
                redisTemplate.opsForValue().set(JwtRedisEnum.getTokenKey(username, newRandomKey),
                        newAuthToken,
                        securityProperties.getJwt().getExpiration(),
                        TimeUnit.SECONDS);
                logger.info("重新生成token【{}】和randomKey【{}】", newAuthToken, newRandomKey);

                // 取出老的authenticate放到redis里
                String authentication = redisTemplate.opsForValue().get(JwtRedisEnum.getAuthenticationKey(username, randomKey));

                redisTemplate.opsForValue().set(JwtRedisEnum.getAuthenticationKey(username, newRandomKey),
                        authentication,
                        securityProperties.getJwt().getExpiration(),
                        TimeUnit.SECONDS);

                // 删除旧的认证信息,这里设置50s后自动到期，是因为在实际应用中有可能从authentication里获取用户唯一标识
                redisTemplate.opsForValue().set(
                        JwtRedisEnum.getAuthenticationKey(username, randomKey),
                        authentication,
                        50,
                        TimeUnit.SECONDS);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String[] getPermitUrls() {

        /** 核心模块相关的URL */
        String[] corePermitUrls = coreAuthorizeConfigProvider.getPermitUrls();
        /** 验证模块相关的URL */
        String[] validatePermitUrls = ValidateAuthorizeConfigProvider.getPermitUrls();

        /** 返回的数组 */
        return (String[])ArrayUtils.addAll(corePermitUrls, validatePermitUrls);
    }

    private void responseEntity(HttpServletResponse response, Integer status, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        try {
            response.getWriter().write(
                    JSON.toJSONString(
                            new ResponseEntity(status, msg).data(null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
