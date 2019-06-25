package com.xiongyayun.athena.user.service;

import com.xiongyayun.athena.user.model.UserAuthorization;

/**
 * 用户授权服务
 *
 * @author: Yayun.Xiong
 * @date 2019-06-16
 */
public interface UserAuthorizationService {
    /**
     * 授权
     * @param identityType  授权登录类型
     * @param identifier    标识
     * @return
     */
    UserAuthorization select(String identityType, String identifier);

    UserAuthorization select(UserAuthorization userAuthorization);
}
