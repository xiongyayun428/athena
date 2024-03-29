package com.xiongyayun.athena.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xiongyayun.athena.core.pagination.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiongyayun.athena.user.mapper.UserMapper;
import com.xiongyayun.athena.user.model.User;
import com.xiongyayun.athena.user.service.UserService;
import com.xiongyayun.athena.components.mybatis.mybatisplus.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/6/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user, Wrappers.<User>lambdaUpdate()
                .eq(User::getUserId, user.getUserId())
        );
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateById(user);
//        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return 0;
//        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteById(Long userId) {
        return userMapper.deleteById(userId);
    }

    @Override
    public int delete(User user) {
        return userMapper.delete(Wrappers.lambdaQuery(user));
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return null;
//        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> selectUser(User user, int pageNum, int pageSize) {
        return null;
//        PageHelper.startPage(pageNum, pageSize);
//        return userMapper.select(user);
    }

    @Override
    public List<User> selectList(User user) {
        return userMapper.selectList(Wrappers.lambdaQuery(user).orderByAsc(User::getLastUpdateTime));
    }

    @Override
    public IPage<User> selectPage(User user, int pageIndex, int pageSize) {
        Page<User> page = new Page(pageIndex, pageSize);
		page.addOrder(OrderItem.asc("user_id"));
        return userMapper.selectPage(page, Wrappers.lambdaQuery(user)
//                .or()
//                .likeLeft(User::getUserName, user.getUserName())
                .orderByAsc(User::getLastUpdateTime)
        );
    }

    @Override
    public User selectById(Long userId) {
        return userMapper.selectById(userId);
    }
}
