package com.chentongwei.security.core.social.qq.config;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * QQ登录判断此用户是否已经在数据库userconnection表中了，
 * 若没在则直接默认帮我们注册一个用户，并save到表中，
 * 这样一来就无需跳转到注册页面了，直接登录成功即可，因为默认为我们注册了，
 * 看源码发现：
 * JdbcUsersConnectionRepository.findUserIdsWithConnection()方法有一个判断connectionSignUp != null会为我们自动save，
 * 所以我们写一类实现ConnectionSignUp接口。并返回唯一的标识即可。
 *
 * @author chentongwei@bshf360.com 2018-04-03 13:59
 */
@Component
public class CtwConnectionSignUp implements ConnectionSignUp {

    /**
     * 返回唯一的标识
     * 这里将openid作为唯一标识
     *
     * @param connection：用户信息
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        return connection.getKey().getProviderUserId();
    }
}
