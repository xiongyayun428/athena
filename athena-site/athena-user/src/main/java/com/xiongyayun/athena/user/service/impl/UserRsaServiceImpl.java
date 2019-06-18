package com.xiongyayun.athena.user.service.impl;

import cn.hutool.crypto.asymmetric.RSA;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.user.mapper.UserRsaMapper;
import com.xiongyayun.athena.user.model.UserAuthorization;
import com.xiongyayun.athena.user.model.UserRsa;
import com.xiongyayun.athena.user.service.UserAuthorizationService;
import com.xiongyayun.athena.user.service.UserRsaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserRsaServiceImpl
 *
 * @author Yayun.Xiong
 * @date 2019-06-17
 */
@Slf4j
@Service
public class UserRsaServiceImpl implements UserRsaService {
    @Autowired
    private UserRsaMapper userRsaMapper;
    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @Override
    public UserRsa selectByUserId(String userId) {
        UserRsa rsa = new UserRsa();
        rsa.setUserId(userId);
        return select(rsa);
    }

    @Override
    public UserRsa select(UserRsa rsa) {
        return userRsaMapper.selectOne(rsa);
    }

    @Override
    public UserRsa selectById(String id) {
        return userRsaMapper.selectByPrimaryKey(id);
    }

    @Override
    public RSA getRSA(String identityType, String identifier) {
        UserAuthorization ua = userAuthorizationService.select(identityType, identifier);
        if (ua == null) {
            throw new AthenaRuntimeException("UserNotExist");
        }
        RSA rsa;
        UserRsa ur = selectByUserId(ua.getUserId());
        if (ur == null) {
            // 使用公共的RSA
            rsa = new RSA("RSA",
                    "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJXqE1bf5PvFahW6/vpZdR30hCN33blPAXSA5XhpiWQaVo7AT9nvDcCUgjtAOP1/5/wgdQPplx5RdNSj0pp83M/GlUQ13Z5x+kT5hlnpOKzq5tO25780QTZp2AOV0SY3EEG04VnkZFZoMyjwmxengi33Y1xO7qeN2nklwIgepW4NAgMBAAECgYBJ3yxkdLN7NQ5PYvPeHVLOzfyHFbvWYo4PAK0Spr6i6y+X7D/s3hVivktmMwFiOTLxVHUU1ng24mC1n3xZ0AlmOks4n29w5O1bb3vaqx1zQVKOTM1DG0YCA1K4eUY7izBGEZ+iuiONsiHUMlArwb0MXC13AYhFLvMzoXHOQfikQQJBAPpEfuJ+4SsH0xN6XCC6g+k/GaM6oUU+wzckaUE0aGvQJ6/w17DODTFFmfcg1gO9oTwscyr8HjwWCujtEuc9yHUCQQCZWSJ29mpK6JzDF/ncBkA6GR69u7xANOd2lYNPX+76i5sbK2uojuIh1GETONtRCJBmWPz3Xuk2bm6c7HJZOxw5AkBfGP36BOp81mlPgnRVf2o2jGKqktHLJTE619wjCc4QAbXC9kqPhYyfx2nc7R5Fp54H5oAQumKmxF166vL+6YwtAkAHofYjMbSssdNLqp/XIcCEo8dx7iw6jeePcXWtt7qvYc+AlLKxGxuKT4XdbRENgX2eeoNs1J6LBQgga2xtoJGRAkBRqjkTJ0PrdCuFVf/+3Jqgd3UQlYMpztlDlSQ1LlaaS4jjMV9x73eqfS8qZ4Sv02yX9NcRe0UvvLaJWgGQyOoJ",
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCV6hNW3+T7xWoVuv76WXUd9IQjd925TwF0gOV4aYlkGlaOwE/Z7w3AlII7QDj9f+f8IHUD6ZceUXTUo9KafNzPxpVENd2ecfpE+YZZ6Tis6ubTtue/NEE2adgDldEmNxBBtOFZ5GRWaDMo8JsXp4It92NcTu6njdp5JcCIHqVuDQIDAQAB");
        } else {
            rsa = new RSA(ur.getAlgorithm(), ur.getPrivateKey(), ur.getPublicKey());
        }
        return rsa;
    }
}
