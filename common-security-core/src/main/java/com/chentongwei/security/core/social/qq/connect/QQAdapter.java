package com.chentongwei.security.core.social.qq.connect;

import com.chentongwei.security.core.social.qq.api.QQ;
import com.chentongwei.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ适配器
 *
 * @author chentongwei@bshf360.com 2018-04-02 11:05
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试QQAPI是否可用，也就是QQ服务是否能正常接收返回消息，
     * 我这里认为ＱＱ服务器永远是可通的，所以直接返回true，否则还需要httpclient发送请求验证是否通。
     *
     * @param qq：social
     * @return
     */
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {

        QQUserInfo userInfo = qq.getUserInfo();
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        /**
         * QQ服务没这个东西，微博才会用，好比个人主页
         */
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    /**
     * QQ用不到，微博能用到
     *
     * @param qq：参数
     * @param message：参数
     */
    @Override
    public void updateStatus(QQ qq, String message) {

    }
}
