package com.xiongyayun.athena.user.service;

import cn.hutool.crypto.asymmetric.RSA;
import com.xiongyayun.athena.user.model.UserRsa;

/**
 * UserRsaService
 *
 * @author Yayun.Xiong
 * @date 2019-06-17
 */
public interface UserRsaService {
    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return
     */
    UserRsa selectByUserId(String userId);

    /**
     * 根据主键ID查询
     *
     * @param id
     * @return
     */
    UserRsa selectById(String id);

    /**
     * 根据实体类查询
     *
     * @param rsa
     * @return
     */
    UserRsa select(UserRsa rsa);

    RSA getRSA(String identityType, String identifier);
}
