package com.chentongwei.security.app;

import com.chentongwei.security.core.social.CtwSpringSocialConfigurer;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 所有的bean在初始化后都会经过这个类的这两个方法，
 * 此处用于将注册页面给改掉，因为app直接返回json，不会跳转到注册页面
 *
 * @author chentongwei@bshf360.com 2018-05-08 20:38
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "ctwSocialSecurityConfig")) {
            CtwSpringSocialConfigurer configurer = (CtwSpringSocialConfigurer) bean;
            // TODO deviceId加密保证唯一，每次都不进入此方法，有bug ?deviceId=" + UUID.randomUUID().toString()
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
