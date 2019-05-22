package com.xyy.athena.user.service;

import com.github.pagehelper.Page;
import com.xyy.athena.db.Pagination;
import com.xyy.athena.user.model.User;

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

    Pagination<User> selectAll();
}
