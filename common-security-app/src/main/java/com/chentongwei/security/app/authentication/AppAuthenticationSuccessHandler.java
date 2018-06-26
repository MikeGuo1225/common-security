package com.chentongwei.security.app.authentication;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.app.enums.JwtRedisEnum;
import com.chentongwei.security.app.jwt.util.JwtTokenUtil;
import com.chentongwei.security.app.properties.SecurityProperties;
import com.chentongwei.security.core.response.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 认证成功后处理器
 *
 * @author chentongwei@bshf360.com 2018-03-26 14:09
 */
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final String randomKey = jwtTokenUtil.getRandomKey();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        logger.info("username：【{}】", username);
        final String token = jwtTokenUtil.generateToken(username, randomKey);

        logger.info("登录成功！");

        // 判断是否开启允许多人同账号同时在线，若不允许的话则先删除之前的
        if (securityProperties.getJwt().isPreventsLogin()) {
            // T掉同账号已登录的用户token信息
            batchDel(JwtRedisEnum.getTokenKey(username, "*"));
            // 删除同账号已登录的用户认证信息
            batchDel(JwtRedisEnum.getAuthenticationKey(username, "*"));
        }

        // 存到redis
        redisTemplate.opsForValue().set(JwtRedisEnum.getTokenKey(username, randomKey),
                token,
                securityProperties.getJwt().getExpiration(),
                TimeUnit.SECONDS);

        // 将认证信息存到redis，方便后期业务用，也方便 JwtAuthenticationTokenFilter用
        redisTemplate.opsForValue().set(JwtRedisEnum.getAuthenticationKey(username, randomKey),
                JSON.toJSONString(authentication),
                securityProperties.getJwt().getExpiration(),
                TimeUnit.SECONDS
        );
        response.setHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new ResponseEntity(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()).data(authentication)));

    }

    /**
     * 批量删除redis的key
     *
     * @param key：redis-key
     */
    private void batchDel(String key){
        Set<String> set = redisTemplate.keys(key);
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            logger.info("keyStr【{}】", keyStr);
            redisTemplate.delete(keyStr);
        }
    }
}
