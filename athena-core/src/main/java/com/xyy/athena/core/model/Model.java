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
public class Model implements Serializable {
    private String createUser;

    private Date createTime;

    private String lastUpdateUser;

    private Date lastUpdateTime;
}
