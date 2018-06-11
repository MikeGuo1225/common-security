package com.chentongwei.security.app.controller;

import com.chentongwei.security.app.enums.JwtRedisEnum;
import com.chentongwei.security.app.jwt.util.JwtTokenUtil;
import com.chentongwei.security.core.response.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 退出
 *
 * @author TongWei.Chen 2018-06-09 12:55:21
 */
@RestController
public class LogoutController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 退出登录
     *
     * @param request：请求
     * @return
     */
    @GetMapping("/jwtLogout")
    public ResponseEntity logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        // 为嘛无需判断authHeader是否为null？ 因为Jwt过滤器已经判断过了。

        String authToken = authHeader.substring("Bearer ".length());
        String randomKey = jwtTokenUtil.getMd5KeyFromToken(authToken);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        redisTemplate.delete(JwtRedisEnum.getTokenKey(username, randomKey));

        redisTemplate.delete(JwtRedisEnum.getAuthenticationKey(username, randomKey));

        logger.info("删除【{}】成功", JwtRedisEnum.getTokenKey(username, randomKey));
        logger.info("退出成功");

        return new ResponseEntity(HttpStatus.OK.value(), "退出成功！").data(null);
    }
}
