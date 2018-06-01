package com.chentongwei.security.validate.config;

import com.chentongwei.security.validate.verification.RedisValidateCodeRepository;
import com.chentongwei.security.validate.verification.SessionValidateCodeRepository;
import com.chentongwei.security.validate.verification.ValidateCodeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码的存取删接口配置类
 *
 * @author chentongwei@bshf360.com 2018-05-29 15:27
 */
@Configuration
public class ValidateCodeRepositoryConfig {

    /**
     * 配置{@link SessionValidateCodeRepository}
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.chentongwei.security.code", value = "repository", havingValue = "session", matchIfMissing = true)
    public ValidateCodeRepository sessionValidateCodeRepository() {
        return new SessionValidateCodeRepository();
    }

    /**
     * 配置{@link RedisValidateCodeRepository}
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "com.chentongwei.security.code", value = "repository", havingValue = "redis")
    public ValidateCodeRepository redisValidateCodeRepository() {
        return new RedisValidateCodeRepository();
    }
}
