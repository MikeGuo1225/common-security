package com.chentongwei.security.validate.verification;

import com.chentongwei.security.validate.code.ValidateCode;
import com.chentongwei.security.validate.enums.ValidateCodeKeyPreffixEnum;
import com.chentongwei.security.validate.enums.ValidateCodeTypeEnum;
import com.chentongwei.security.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Redis存验证码，实现前后分离
 *
 * @author TongWei.Chen 2018-04-09 21:06:58
 */
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeTypeEnum validateCodeType) {
        redisTemplate.opsForValue().set(getRedisKey(request, validateCodeType), validateCode, 60, TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        return (ValidateCode) redisTemplate.opsForValue().get(getRedisKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        redisTemplate.delete(getRedisKey(request, validateCodeType));
    }

    /**
     * 获取redisKey
     *
     * @return
     */
    private String getRedisKey(ServletWebRequest request, ValidateCodeTypeEnum validateCodeType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return ValidateCodeKeyPreffixEnum.REDIS_KEY_PREFIX.preffixKey() + validateCodeType.toString().toUpperCase() + "_" + deviceId;
    }

}
