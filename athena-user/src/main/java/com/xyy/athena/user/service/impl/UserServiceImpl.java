package com.xyy.athena.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.xyy.athena.db.Pagination;
import com.xyy.athena.user.mapper.UserMapper;
import com.xyy.athena.user.model.User;
import com.xyy.athena.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int delete(String userId) {

        return 0;
    }

    @Override
    public Pagination<User> selectAll(User user) {
        PageHelper.startPage(user);
        return new Pagination(userMapper.selectAll(user));
    }
}
