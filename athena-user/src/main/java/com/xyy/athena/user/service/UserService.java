package com.xyy.athena.user.service;

import com.xyy.athena.db.Pagination;
import com.xyy.athena.user.model.User;

/**
 * UserService
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
public interface UserService {

    Pagination<User> selectAll(User user);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int delete(String userId);

    /**
     * 根据主键ID查询用户信息
     * @param userId
     * @return
     */
    User findUserById(String userId);
}
