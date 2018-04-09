package com.chentongwei.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 认证服务配置
 * 什么都不用我们做，只需要一个EnableAuthorizationServer注解即可。
 * Demo项目引用了我们这个app项目，所以Demo项目的Application启动后就是一个认证服务器了。
 * 所以它就能够提供OAuth协议的四种授权模式了。
 *
 * @author chentongwei@bshf360.com 2018-04-08 11:13
 */
@Configuration
@EnableAuthorizationServer
public class CtwAuthorizationServerConfig {



}
