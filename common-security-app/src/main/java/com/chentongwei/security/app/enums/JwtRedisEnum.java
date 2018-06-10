package com.chentongwei.security.app.enums;

/**
 * Redis存储jwt的key前缀
 *
 * @author TongWei.Chen 2018-06-09 21:40:23
 */
public enum JwtRedisEnum {

    KEY_PREFIX("jwt:")
    ;

    private String value;


    JwtRedisEnum(String value) {
        this.value = value;
    }

    /**
     * 获取key
     *
     * @param username：xxx@163.com
     * @param randomKey：xxxxxx
     * @return
     */
    public static String getKey(String username, String randomKey) {
        return KEY_PREFIX.value + username + ":" + randomKey;
    }
}
