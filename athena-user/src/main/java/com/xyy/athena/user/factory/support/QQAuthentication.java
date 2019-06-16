package com.xyy.athena.user.factory.support;

import com.xyy.athena.user.factory.AbstractAuthentication;
import com.xyy.athena.user.factory.AuthenticationFactory;
import com.xyy.athena.user.model.UserAuthorization;
import org.springframework.stereotype.Component;

/**
 * QQ授权登录
 *
 * @author Yayun.Xiong
 * @date 2019-06-16
 */
@Component
public class QQAuthentication extends AbstractAuthentication {
    /**
     * 授权
     *
     * @param identifier 标识
     * @param credential 密码凭证
     * @return
     */
    @Override
    public UserAuthorization auth(String identifier, String credential) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AuthenticationFactory.put("QQ", this);
    }
}
