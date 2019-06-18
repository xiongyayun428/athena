package com.xiongyayun.athena.user.factory.support;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import com.xiongyayun.athena.user.factory.AbstractAuthentication;
import com.xiongyayun.athena.user.factory.AuthenticationFactory;
import com.xiongyayun.athena.user.model.UserAuthorization;
import com.xiongyayun.athena.user.service.UserAuthorizationService;
import com.xiongyayun.athena.user.service.UserRsaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRsaService userRsaService;
    @Autowired
    private UserAuthorizationService userAuthorizationService;
    /**
     * 授权方式
     */
    private static final String GRANT_TYPE = "user_name";
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
        RSA rsa = userRsaService.getRSA("UserName", identifier);
        // 2. 密码是否符合规则
        String password = rsa.decryptStr(credential, KeyType.PrivateKey);
        // 盐值：for_$n(@RenSheng)_$n+="die"
        // 解释：人生自古谁无死
        byte[] salt = "for_$n(@RenSheng)_$n+=\"die\"".getBytes();
        MD5 md5 = new MD5(salt, 2, 1);
        UserAuthorization ua = new UserAuthorization();
        ua.setIdentityType("UserName");
        ua.setIdentifier(identifier);
        // 32位小数
        ua.setCredential(md5.digestHex(identifier + password));
        return userAuthorizationService.select(ua);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AuthenticationFactory.put("UserName", this);
    }
}
