package com.xyy.athena.user.factory.support;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.xyy.athena.user.factory.AbstractAuthentication;
import com.xyy.athena.user.factory.AuthenticationFactory;
import com.xyy.athena.user.model.UserAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 用户名授权登录
 *
 * @author Yayun.Xiong
 * @date 2019-06-16
 */
@Slf4j
@Component
public class UserNameAuthentication extends AbstractAuthentication {
    /**
     * 授权
     *
     * @param identifier 标识
     * @param credential 密码凭证
     * @return
     */
    @Override
    public UserAuthorization auth(String identifier, String credential) {
        Assert.hasText(identifier, "用户名不能为空");
        Assert.hasText(credential, "密码不能为空");
        // 获取用户的RSA
        RSA rsa = new RSA();
        // 2. 密码是否符合规则
        String password = rsa.decryptStr(credential, KeyType.PrivateKey);
        log.info(password);
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AuthenticationFactory.put("UserName", this);
    }
}
