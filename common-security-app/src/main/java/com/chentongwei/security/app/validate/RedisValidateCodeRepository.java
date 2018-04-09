package com.chentongwei.security.app.validate;

import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.ValidateCodeType;
import com.chentongwei.security.core.validate.code.ValidateCode;
import com.chentongwei.security.core.validate.verification.ValidateCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Redis存验证码，实现前后分离
 *
 * @author TongWei.Chen 2018-04-09 21:06:58
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(getRedisKey(validateCodeType), validateCode);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) redisTemplate.opsForValue().get(getRedisKey(validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(getRedisKey(validateCodeType));
    }

    /**
     * 获取sessionKey
     *
     * @return
     */
    private String getRedisKey(ValidateCodeType validateCodeType) {
        logger.info("redisKey：", SecurityConstant.REDIS_KEY_PREFIX + validateCodeType.toString().toUpperCase());
        return SecurityConstant.REDIS_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
