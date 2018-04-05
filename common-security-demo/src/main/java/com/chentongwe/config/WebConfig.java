package com.chentongwe.config;

import com.chentongwe.security.QQConnectView;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chentongwei@bshf360.com 2018-03-29 19:01
 */
@Configuration
public class WebConfig {

    @Bean({"connect/qqConnect", "connect/qqConnected"})
    public View qqConnectedView() {
        return new QQConnectView();
    }

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
