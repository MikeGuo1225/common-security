package com.chentongwei.security.app.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.app.jwt.util.JwtTokenUtil;
import com.chentongwei.security.core.authorize.CoreAuthorizeConfigProvider;
import com.chentongwei.security.core.response.ResponseEntity;
import com.chentongwei.security.validate.authorize.ValidateAuthorizeConfigProvider;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

/**
 * @author chentongwei@bshf360.com 2018-06-08 14:31
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CoreAuthorizeConfigProvider coreAuthorizeConfigProvider;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 排除路径，并且如果是options请求是cors跨域预请求，设置allow对应头信息
        // TODO TODO !!!! 自己项目的url怎么办？目前只能让使用者配置到配置文件，而不能自定义到provider中
        String[] permitUrls = getPermitUrls();
        for (int i = 0, length = permitUrls.length; i < length; i ++) {
            if (antPathMatcher.match(permitUrls[i], request.getRequestURI())
                    || Objects.equals(RequestMethod.OPTIONS.toString(), request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader) || ! authHeader.startsWith("Bearer ")) {
            logger.error("url===>【{}】", request.getRequestURL());
            throw new UnapprovedClientAuthenticationException("不合法的token");
        }

        String authToken = authHeader.substring("Bearer ".length());

        // 判断token是否失效
        if (jwtTokenUtil.isTokenExpired(authToken)) {
            logger.info("token已过期！");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    JSON.toJSONString(
                            new ResponseEntity(602, "token已过期！").data(null)));
            return;
        }

        long tokenExpireTime = jwtTokenUtil.getExpirationDateFromToken(authToken).getTime();
        long surplusExpireTime = (tokenExpireTime - System.currentTimeMillis()) / 1000;
        logger.info("surplusExpireTime:" + surplusExpireTime);
        // token过期时间小于1分钟，自动刷新token
        // TODO 60可配置
        if (surplusExpireTime <= 60) {
            authToken = jwtTokenUtil.refreshToken(authToken);
            response.setHeader("Authorization", "Bearer " + authToken);
        }

        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        if (StringUtils.isBlank(username)) {
            logger.info("username为null！非法！");
            throw new UnapprovedClientAuthenticationException("不合法的token");
        }

        logger.info("checking authentication " + username);

        // TODO JWTUserDetails的问题
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // 避免了每次查询db，直接从jwt取
            UserDetails userDetails = jwtTokenUtil.getUserFromToken(authToken);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
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

}
