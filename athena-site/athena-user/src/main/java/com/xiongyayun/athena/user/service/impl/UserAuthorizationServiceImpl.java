package com.xiongyayun.athena.user.service.impl;

import com.xiongyayun.athena.user.mapper.UserAuthorizationMapper;
import com.xiongyayun.athena.user.model.UserAuthorization;
import com.xiongyayun.athena.user.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户授权服务实现类
 *
 * @author: Yayun.Xiong
 * @date 2019-06-16
 */
@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {
    @Autowired
    private UserAuthorizationMapper userAuthorizationMapper;

    /**
     * 授权
     *
     * @param identityType 授权登录类型
     * @param identifier   标识
     * @return
     */
    @Override
    public UserAuthorization select(String identityType, String identifier) {
        UserAuthorization ua = new UserAuthorization();
        ua.setIdentityType(identityType);
        ua.setIdentifier(identifier);
        return userAuthorizationMapper.selectOne(ua);
    }

    @Override
    public UserAuthorization select(UserAuthorization userAuthorization) {
        return userAuthorizationMapper.selectOne(userAuthorization);
    }
}
