package com.chentongwei.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author chentongwei@bshf360.com 2018-04-02 13:01
 */
public class CtwSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    // 后处理器，app用
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    public CtwSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 为什么重写这个方法？
     * 因为SpringSocialConfigurer的configure方法最后一句，
     *   http.authenticationProvider(
     *        new SocialAuthenticationProvider(usersConnectionRepository, socialUsersDetailsService))
     *        .addFilterBefore(postProcess(filter), AbstractPreAuthenticatedProcessingFilter.class);
     * 将我们的过滤器加到了AbstractPreAuthenticatedProcessingFilter之前（addFilterBefore），调用了一个postProcess方法
     * 所以我们重写postProcess方法，将我们自定义的请求路径（默认是/auth）加到filter里，并返回此filter即可。
     *
     * @param object：参数
     * @param <T>：泛型参数
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(this.filterProcessesUrl);

        // app用，browser的话不会进入判断
        if (null != socialAuthenticationFilterPostProcessor) {
            socialAuthenticationFilterPostProcessor.process(filter);
        }

        return (T) filter;
    }

    public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcessor() {
        return socialAuthenticationFilterPostProcessor;
    }

    public void setSocialAuthenticationFilterPostProcessor(SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }
}
