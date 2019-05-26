package com.xyy.athena.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 存放我们的实体类，与数据库中的属性值基本保持一致
 *
 * @author: 熊亚运
 * @date: 2019-05-23
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建用户
     */
    @Column(name = "create_user")
    private String createUser;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 最后更新用户
     */
    @Column(name = "last_update_user")
    private String lastUpdateUser;
    /**
     * 最后更新时间
     */
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
    /**
     * 默认当前页码
     */
    @JsonIgnore
    private int pageIndex = 1;
    /**
     * 默认每页记录条数
     */
    @JsonIgnore
    private int pageSize = 10;
    /**
     * 默认排序
     */
    private String orderBy = "last_update_time desc, create_time desc";
}