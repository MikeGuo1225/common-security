package com.chentongwei.security.app.validate;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.exception.ValidateCodeException;
import com.chentongwei.security.core.validate.code.ValidateCode;
import com.chentongwei.security.core.validate.verification.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Redis存验证码，实现前后分离
 *
 * @author TongWei.Chen 2018-04-09 21:06:58
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(getRedisKey(request, validateCodeType), validateCode, 1800, TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) redisTemplate.opsForValue().get(getRedisKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(getRedisKey(request, validateCodeType));
    }

    /**
     * 获取sessionKey
     *
     * @return
     */
    private String getRedisKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return SecurityConstant.REDIS_KEY_PREFIX + validateCodeType.toString().toUpperCase() + "_" + deviceId;
    }

}
