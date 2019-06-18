package com.xiongyayun.athena.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.xiongyayun.athena.db.Pagination;
import com.xiongyayun.athena.service.id.IdFactory;
import com.xiongyayun.athena.user.mapper.UserMapper;
import com.xiongyayun.athena.user.model.User;
import com.xiongyayun.athena.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    @Qualifier("simpleIdFactory")
    private IdFactory idFactory;

    @Override
    public int delete(String userId) {
        User user = new User();
        user.setUserId(userId);
        return userMapper.delete(user);
    }

    @Override
    public User findUserById(String userId) {
        User user = new User();
        user.setUserId(userId);
        return userMapper.selectOne(user);
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        User result = userMapper.selectOne(user);
        if (result != null) {
            Integer status = result.getStatus();
            if (status != 0) {

            }
        }
        return result;
    }

    @Override
    public User findUser(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public Pagination<User> selectAll(User user) {
        PageHelper.startPage(user);
        return new Pagination(userMapper.select(user));
    }

    @Override
    public int add(User user) {
        user.setUserId(idFactory.generate().toString());
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
