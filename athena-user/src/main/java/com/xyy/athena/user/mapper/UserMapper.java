package com.xyy.athena.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * UserMapper
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE user_id = '11111'")
    Map select();
}
