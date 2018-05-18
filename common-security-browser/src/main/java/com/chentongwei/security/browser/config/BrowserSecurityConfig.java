package com.chentongwei.security.browser.config;

import com.chentongwei.security.core.authentication.AbstractChannelSecurityConfig;
import com.chentongwei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chentongwei.security.core.authorize.AuthorizeConfigManager;
import com.chentongwei.security.core.constant.SecurityConstant;
import com.chentongwei.security.core.enums.FrameDisableStatus;
import com.chentongwei.security.core.logout.CtwLogoutSuccessHandler;
import com.chentongwei.security.core.properties.SecurityProperties;
import com.chentongwei.security.core.validate.code.ValidateCodeSecurityConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 浏览器的安全配置
 *
 * @author chentongwei@bshf360.com 2018-03-26 10:32
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    // 短信验证配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

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
     * Spring Social相关
     */
    @Autowired
    private SpringSocialConfigurer ctwSocialSecurityConfig;

    /**
     * Session相关
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;


    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单登录以及成功/失败处理
        authenticationConfig(http);
        // 验证码（image+sms+geetest）
        HttpSecurity httpSecurity =
                http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(ctwSocialSecurityConfig)
                    .and()
                // 记住我
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                // session设置
                .sessionManagement()
                    // session失效URL
                    .invalidSessionStrategy(invalidSessionStrategy)
                    // session最大并发数
                    .maximumSessions(securityProperties.getSession().getMaximumSessions())
                    // 多用户同时登陆时是否会踢掉上一个用户，true：不允许同时登陆，false：踢掉上一个
                    .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())
                    // 被踢后的处理
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                    .and()
                .logout()
                    .logoutUrl(securityProperties.getLogout().getLogoutUrl())
                    .logoutSuccessHandler(new CtwLogoutSuccessHandler(securityProperties.getLogout().getLogoutSuccessUrl()))
                    .deleteCookies("JSESSIONID")
                    .and()
                // 权限设置
                .authorizeRequests()
                    // 任何请求都必须经过身份认证，排除如下
                    .antMatchers(
                            getPermitUrls()
                    ).permitAll()
                    // 配置/user请求，只有配置了admin角色后才能访问。
                    .antMatchers(HttpMethod.GET, "/user/**").hasRole("admin")
                    // 任何请求
                    .anyRequest()
                    // 都必须经过身份认证
                    .authenticated()
                    .and()
                  // 先加上这句话，否则登录的时候会出现403错误码，Could not verify the provided CSRF token because your session was not found.
                 .csrf().disable();
        // 若是0，则放开frame权限
        if (Objects.equals(FrameDisableStatus.ALLOW.status(), securityProperties.getBrowser().getFrameDisable())) {
            httpSecurity.headers().frameOptions().disable();
        }

        authorizeConfigManager.config(http.authorizeRequests());

    }

    /**
     * 获取所有的无需权限即可访问的urls
     * @return
     */
    private String[] getPermitUrls() {
        List<String> urls = new LinkedList<>();
        String permitUrls = securityProperties.getAuthorize().getPermitUrls();
        if (StringUtils.isNotEmpty(permitUrls) && StringUtils.isNotBlank(permitUrls)) {
            String[] urlArray = StringUtils.splitByWholeSeparator(permitUrls, ",");
            urls = new LinkedList<>(Arrays.asList(urlArray));
        }

        urls.add(SecurityConstant.DEFAULT_UNAUTHENTICATION_URL);
//        urls.add(SecurityConstant.DEFAULT_LOGIN_PAGE_URL);
//        urls.add(SecurityConstant.DEFAULT_LOGIN_PROCESSING_URL_MOBILE);
//        urls.add(SecurityConstant.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*");
//        urls.add(SecurityConstant.DEFAULT_GET_SOCIAL_USER_INFO);
        urls.add(securityProperties.getBrowser().getLoginPage());
        urls.add(securityProperties.getBrowser().getRegisterPage());
        urls.add(securityProperties.getSession().getSessionInvalidUrl());
        // 若配置了退出登录的Url，则也add到无权限即可访问的list里
        if (! StringUtils.equals(SecurityConstant.DEFAULT_LOGIN_PAGE_URL, securityProperties.getLogout().getLogoutSuccessUrl())) {
            urls.add(securityProperties.getLogout().getLogoutSuccessUrl());
        }
        return urls.toArray(new String[urls.size()]);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}