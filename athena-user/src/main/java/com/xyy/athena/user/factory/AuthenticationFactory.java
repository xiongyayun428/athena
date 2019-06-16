package com.xyy.athena.user.factory;

import com.xyy.athena.user.model.UserAuthorization;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * AuthorizationFactory
 *
 * @author Yayun.Xiong
 * @date 2019-06-16
 */
public class AuthenticationFactory {
    private static Map<String, Authentication> cache = new HashMap<>();
    private final static Authentication EMPTY = new EmptyDesc();

    public static void put(@NotBlank String identityType, @NotNull Authentication authentication) {
        cache.put(identityType, authentication);
    }

    public static Authentication get(@NotBlank String identityType) {
        Assert.hasText(identityType, "登录类型不能为空");
        Authentication authentication = cache.get(identityType);
        //没有对应year返回EMPTY
        return Optional.ofNullable(authentication).orElse(EMPTY);
    }

    private static class EmptyDesc implements Authentication {
        /**
         * 授权
         *
         * @param identifier 标识
         * @param credential 密码凭证
         * @return
         */
        @Override
        public UserAuthorization authorization(String identifier, String credential) {
            return null;
        }
//        @Override
//        protected boolean validation(String identifier, String credential) {
//            return false;
//        }
//        @Override
//        public void process() {
//            System.out.println("未知的学习时间year");
//        }
    }

}
