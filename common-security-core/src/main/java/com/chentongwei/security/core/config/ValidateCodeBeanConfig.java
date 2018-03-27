package com.chentongwei.security.core.config;

import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.validate.code.ValidateCodeGenerator;
import com.chentongwei.security.core.validate.image.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码bean注册
 *
 * @author chentongwei@bshf360.com 2018-03-27 13:09
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(ValidateCodeGenerator.class)
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

}
