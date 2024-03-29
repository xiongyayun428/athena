package com.xiongyayun.athena.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiongyayun.athena.core.pagination.IPage;
import com.xiongyayun.athena.components.mybatis.mybatisplus.Page;
import com.xiongyayun.athena.user.mapper.UserMapper;
import com.xiongyayun.athena.user.model.User;
import com.xiongyayun.athena.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 用户服务实现类
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

	/**
	 * 创建用户
	 *
	 * @param user
	 * @return
	 */
	@Override
	public int create(User user) {
		return userMapper.insert(user);
	}

	/**
	 * 根据 UserId 更新用户
	 * @param user
	 * @return
	 */
	@Override
	public int updateById(User user) {
		return userMapper.updateById(user);
	}

	/**
	 * 根据 ID 删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(Serializable id) {
		return userMapper.deleteById(id);
	}

	/**
	 * 根据 User 条件，查询全部记录（并翻页）
	 *
	 * @param page 分页查询条件
	 * @param user User实体对象（可以为 null）
	 * @return
	 */
	@Override
	public IPage<User> selectPage(Page page, User user) {
		return userMapper.selectPage(page, Wrappers.lambdaQuery(user));
	}

	/**
	 * 根据 User 条件，查询全部记录（并翻页）
	 *
	 * @param pageIndex 当前页
	 * @param pageSize  每页显示条数
	 * @param user      User实体对象（可以为 null）
	 * @return
	 */
	@Override
	public IPage<User> selectPage(long pageIndex, long pageSize, User user) {
		return this.selectPage(new Page(pageIndex, pageSize), user);
	}

	/**
	 * 根据 UserId 查询
	 * @param userId		主键ID
	 * @return
	 */
	@Override
	public User selectById(Serializable userId) {
		return this.userMapper.selectById(userId);
	}

}
