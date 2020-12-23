package com.xiongyayun.athena.user.service;

import com.xiongyayun.athena.core.pagination.IPage;
import com.xiongyayun.athena.core.pagination.mybatisplus.Page;
import com.xiongyayun.athena.user.model.User;

import java.io.Serializable;

/**
 * UserService
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     * @return
     */
    int create(User user);

	/**
	 * 根据 UserId 更新用户
	 * @param user
	 * @return
	 */
    int updateById(User user);

	/**
	 * 根据 ID 删除
	 * @param id
	 * @return
	 */
	int deleteById(Serializable id);

	/**
	 * 根据 User 条件，查询全部记录（并翻页）
	 * @param page         分页查询条件
	 * @param user         User实体对象（可以为 null）
	 * @return
	 */
	IPage<User> selectPage(Page page, User user);

	/**
	 * 根据 User 条件，查询全部记录（并翻页）
	 * @param pageIndex		当前页
	 * @param pageSize		每页显示条数
	 * @param user			User实体对象（可以为 null）
	 * @return
	 */
	IPage<User> selectPage(long pageIndex, long pageSize, User user);

	/**
	 * 根据 UserId 查询
	 * @param userId		主键ID
	 * @return
	 */
	User selectById(Serializable userId);

}
