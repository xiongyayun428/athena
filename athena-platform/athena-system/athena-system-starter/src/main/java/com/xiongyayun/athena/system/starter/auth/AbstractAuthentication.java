//package com.xiongyayun.athena.user.factory;
//
//import com.xiongyayun.athena.components.common.exception.AthenaRuntimeException;
//import com.xiongyayun.athena.user.model.UserAuthorization;
//import com.xiongyayun.athena.user.service.UserAuthorizationService;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * AbstractAuthorization
// *
// * @author Yayun.Xiong
// * @date 2019-06-16
// */
//public abstract class AbstractAuthentication implements Authentication, InitializingBean {
//    @Autowired
//    private UserAuthorizationService userAuthorizationService;
//    /**
//     * 授权
//     *
//     * @param identifier   标识
//     * @param credential   密码凭证
//     * @return
//     */
//    @Override
//    public UserAuthorization authorization(String identifier, String credential) {
//		UserAuthorization userAuthorization = auth(identifier, credential);
//        if (userAuthorization != null) {
//			UserAuthorization existUserAuthorization = userAuthorizationService.select(userAuthorization.getIdentityType(), userAuthorization.getIdentifier());
//            if (existUserAuthorization != null && existUserAuthorization.getCredential().equals(userAuthorization.getCredential())) {
//                return existUserAuthorization;
//            }
//        }
//        throw new AthenaRuntimeException("UserNameOrPasswordError");
//    }
//
//    /**
//     * 身份验证
//     * @param identifier
//     * @param credential
//     * @return
//     */
//    protected abstract UserAuthorization auth(String identifier, String credential);
//}
