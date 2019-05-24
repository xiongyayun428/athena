package com.xyy.athena.user.mapper;

import com.github.pagehelper.Page;
import com.xyy.athena.user.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    User selectByPrimaryKey(String userId);

    Page<User> selectAll(User user);

    int updateByPrimaryKey(User record);

}