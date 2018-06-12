
package com.chentongwe.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author chentongwei@bshf360.com 2018-03-26 13:15
 */
@Service
public class MyUserDetailsService implements UserDetailsService/*, SocialUserDetailsService */{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名：" + username);
        return buildUser(username);
    }

    /*@Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("social登录用户id：" + userId);
        return buildUser(userId);
    }*/

    private UserDetails buildUser(String username) {
/**
         * passwordEncoder.encode这步骤应该放到注册接口去做，而这里只需要传一个从db查出来的pwd即可。
         *
         * passwordEncoder.encode("123456")每次打印出来都是不同的，虽然是同一个（123456）密码，
         * 但是他会随机生成一个盐（salt），他会把随机生成的盐混到加密的密码里。Springsecurity验证（matches方法）的时候会将利用此盐解析出pwd，进行匹配。
         * 这样的好处是：如果数据库里面有10个123456密码。但是被破解了1个，那么另外九个是安全的，因为db里存的串是不一样的。
         */

        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是：" + password);
        // 这个User不一定必须用SpringSecurity的，可以写一个自定义实现UserDetails接口的类，然后把是否锁定等判断逻辑写进去。
//        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,ROLE_USER"));
        return new MyUserDetails(password, username, 12, 1, new Date());
    }
}
