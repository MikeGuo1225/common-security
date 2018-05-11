package com.chentongwei.security.app.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chentongwei.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author chentongwei@bshf360.com 2018-05-10 15:04
 */
public class JwtTokenValidateFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 排除登录、生成token的路径，并且如果是options请求是cors跨域预请求，设置allow对应头信息
        if (Objects.equals("/oauth/token", request.getRequestURI())
                || Objects.equals("/login", request.getRequestURI())
                || Objects.equals(RequestMethod.OPTIONS.toString(), request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");

        String refreshTokenParam = request.getHeader("refreshToken");

        if (header == null || ! header.startsWith("bearer ") || StringUtils.isBlank(refreshTokenParam)) {
            throw new UnapprovedClientAuthenticationException("不合法的token");
        }

        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();

        logger.info(JSON.toJSONString(claims));

        int exp = (int) claims.get("exp");
        logger.debug("过期时间是：", exp);

        long expireSeconds = exp - System.currentTimeMillis() / 1000;
        if (expireSeconds <= 0) {
            // 跳转到登录
            logger.info("token过期了，去登录吧...【{}】", expireSeconds);
            // TODO 状态码？
            response.setStatus(101);
            return;
        }
        // 300s =》5分钟，判断5分钟内过期有操作的话，就刷新token
        if (expireSeconds < 7200) {
            logger.info("token即将过期，刷新一波...【{}】", expireSeconds);
            // 刷新
            MultiValueMap<String, String> params = new LinkedMultiValueMap();
            params.add("grant_type", "refresh_token");
            params.add("refresh_token", refreshTokenParam);

            JSONObject responseEntity = postUrl("http://localhost/oauth/token", params);
            System.out.println(responseEntity);

            String accessToken = responseEntity.get("access_token").toString();
            String refreshToken = responseEntity.get("refresh_token").toString();

            response.setHeader("refreshToken", refreshToken);
            response.setHeader("Authorization", "bearer " + accessToken);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public JSONObject postUrl(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", getHeader());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<JSONObject> response = client.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
        return response.getBody();
    }

    /**
     * 构造Basic Auth认证头信息
     *
     * @return
     */
    private String getHeader() {
        String auth = securityProperties.getOauth2().getClient().getClientId() + ":" + securityProperties.getOauth2().getClient().getClientSecret();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
}
