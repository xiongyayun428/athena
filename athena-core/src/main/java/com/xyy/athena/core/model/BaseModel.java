package com.xyy.athena.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Model
 *
 * @author Yayun.Xiong
 * @date 2019-05-22
 */
@Data
public class BaseModel implements Serializable {
    /**
     * 创建用户
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新用户
     */
    private String lastUpdateUser;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
}
