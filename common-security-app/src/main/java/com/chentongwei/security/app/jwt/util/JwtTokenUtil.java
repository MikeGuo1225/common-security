package com.chentongwei.security.app.jwt.util;

import com.chentongwei.security.app.jwt.JWTUserDetails;
import com.chentongwei.security.app.properties.SecurityProperties;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * Jwt Util
 *
 * @author chentongwei@bshf360.com 2018-06-07 19:51
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_AUTHORITIES = "scope";

    @Autowired
    private SecurityProperties securityProperties;

    public UserDetails getUserFromToken(String token) {
        JWTUserDetails user;
        try {
            final Claims claims = getClaimFromToken(token);
            long userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            List roles = (List) claims.get(CLAIM_KEY_AUTHORITIES);
            Collection<? extends GrantedAuthority> authorities = parseArrayToAuthorities(roles);
//            boolean account_enabled = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_ENABLED);
//            boolean account_non_locked = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_LOCKED);
//            boolean account_non_expired = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_EXPIRED);

            user = new JWTUserDetails(userId, username, "password", true, true, true, true, authorities);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> parseArrayToAuthorities(List roles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority;
        if (CollectionUtils.isNotEmpty(roles)) {
            for (Object role : roles) {
                authority = new SimpleGrantedAuthority(role.toString());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    /**
     * 获取用户id从token中
     *
     * @param token
     * @return
     */
    public long getUserIdFromToken(String token) {
        long userId;
        try {
            final Claims claims = getClaimFromToken(token);
            userId = (Long) claims.get("user_id");
        } catch (Exception e) {
            userId = 0;
        }
        return userId;
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取md5 key从token中
     */
    public String getMd5KeyFromToken(String token) {
        return getPrivateClaimFromToken(token, securityProperties.getJwt().getMd5Key());
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确,不正确会报异常<br>
     */
    public void parseToken(String token) throws JwtException {
        Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 生成token(通过用户名和签名时候用的随机数)
     */
    public String generateToken(String userName, String randomKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(securityProperties.getJwt().getMd5Key(), randomKey);
        return doGenerateToken(claims, userName);
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64(securityProperties.getJwt().getSecret());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + securityProperties.getJwt().getExpiration() * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, generalKey())
                .compact();
    }

    /**
     * 获取混淆MD5签名用的随机字符串
     */
    public String getRandomKey() {
        return getRandomString(6);
    }

    /**
     * 获取随机位数的字符串
     */
    public static String getRandomString(int length) {
        final String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 验证token是否合法
     *
     * @param token：token
     * @param userDetails：用户信息
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && ! isTokenExpired(token));
    }

    /**
     * 刷新token
     *
     * @param token：token
     * @return
     */
    public String refreshToken(String token, String randomKey) {
        String refreshedToken;
        try {
            final Claims claims = getClaimFromToken(token);
            refreshedToken = generateToken(claims.getSubject(), randomKey);
        } catch (Exception e1) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

}