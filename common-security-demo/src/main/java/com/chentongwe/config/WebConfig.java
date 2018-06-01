package com.chentongwe.config;

//import com.chentongwe.security.QQConnectView;
//import com.chentongwei.security.app.jwt.JwtTokenValidateFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chentongwei@bshf360.com 2018-03-29 19:01
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

   /* @Bean({"connect/qqConnect", "connect/qqConnected"})
    public View qqConnectedView() {
        return new QQConnectView();
    }*/

    /**
     * 配置过滤器
     * @return
     */
   /* @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter());
        registration.addUrlPatterns("*//*");
        registration.setOrder(-10);
        return registration;
    }*/

//    @Bean
//    public Filter loginFilter() {
//        return new JwtTokenValidateFilter();
//    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CorsFilter timeFilter = new CorsFilter();
        registrationBean.setFilter(timeFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        registrationBean.setOrder(-1000);
        return registrationBean;
    }
}
