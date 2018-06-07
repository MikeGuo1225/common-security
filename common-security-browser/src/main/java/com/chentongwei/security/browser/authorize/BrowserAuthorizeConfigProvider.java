package com.chentongwei.security.browser.authorize;

import com.chentongwei.security.browser.logout.BrowserLogoutSuccessHandler;
import com.chentongwei.security.browser.properties.SecurityProperties;
import com.chentongwei.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 浏览器（前后不分离）的一些核心配置
 *
 * @author chentongwei@bshf360.com 2018-06-04 16:05
 */
@Component
@Order(Integer.MIN_VALUE + 2)
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {

    /**
     * 允许iframe
     */
    private static final int FRAME_DISABLE_ALLOW_STATUS = 1;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 记住我
     */
    // 记住我数据源（使用者配置的）
    @Autowired
    private DataSource dataSource;
    // 记住我自动登录需要用到
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * session
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Override
    public void config(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 退出登录
                .logout()
                    .logoutUrl(securityProperties.getLogout().getLogoutUrl())
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(new BrowserLogoutSuccessHandler(securityProperties.getLogout().getLogoutSuccessUrl()))
                .and()
                // 记住我
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getRememberme().getSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                .sessionManagement()
                    // session失效处理器
                    .invalidSessionStrategy(invalidSessionStrategy)
                    // session最大并发数
                    .maximumSessions(securityProperties.getSession().getMaximumSessions())
                    // 被踢后的处理
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
                .authorizeRequests()
                    .antMatchers(
                            getPermitAllUrl()
                    ).permitAll()
                .and()
        ;
        // 若是0，则放开frame权限
        if (Objects.equals(FRAME_DISABLE_ALLOW_STATUS, securityProperties.getFrame().getDisableStatus())) {
            httpSecurity.headers().frameOptions().disable();
        }
    }

    private String[] getPermitAllUrl() {
        return new String[] {securityProperties.getLogout().getLogoutUrl(), "/*.html", "/favicon.ico", "/**/*.css", "/**/*.js"};
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
