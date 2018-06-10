package com.chentongwei.security.app.authentication;

import com.alibaba.fastjson.JSON;
import com.chentongwei.security.app.enums.JwtRedisEnum;
import com.chentongwei.security.app.jwt.util.JwtTokenUtil;
import com.chentongwei.security.app.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final String randomKey = jwtTokenUtil.getRandomKey();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        logger.info("username：【{}】", username);
        final String token = jwtTokenUtil.generateToken(username, randomKey);

        logger.info("登录成功！");

        response.setHeader("Authorization", "Bearer " + token);
        response.setHeader("randomKey", randomKey);

        // 判断是否开启允许多人同账号同时在线，若不允许的话则先删除之前的
        if (securityProperties.getJwt().isPreventsLogin()) {
            // T掉同账号已登录的用户
            batchDel(username);
        }

        // 存到redis
        redisTemplate.opsForValue().set(JwtRedisEnum.getKey(username, randomKey),
                token,
                securityProperties.getJwt().getExpiration(),
                TimeUnit.SECONDS);

        Map<String, Object> resultMap = new HashMap();
        resultMap.put("authentication", authentication);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(resultMap));

    }

    /**
     * 批量删除redis的key
     *
     * @param username：username
     */
    private void batchDel(String username){
        Set<String> set = redisTemplate.keys(JwtRedisEnum.getKey(username, "*"));
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            logger.info("keyStr【{}】", keyStr);
            redisTemplate.delete(keyStr);
        }
    }
}
