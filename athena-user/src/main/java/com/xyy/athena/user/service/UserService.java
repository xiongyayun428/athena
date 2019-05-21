package com.xyy.athena.user.service;

/**
 * UserService
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
public interface UserService {

//    int add();

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int delete(String userId);
}
