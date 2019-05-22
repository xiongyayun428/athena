package com.xyy.athena.user.model;

import com.xyy.athena.core.model.Model;
import lombok.Data;

import java.util.Date;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-05-22
 */
@Data
public class User extends Model {
    private String userId;

    private String userName;

    private String realName;

    private String nickName;

    private String accessIp;

    private String accessMac;

    private Integer status;

    private String lastVisitIp;

    private Date lastVisitDate;

    private Integer visitCount;

    private Date visitDate;

    private Integer errorTimes;

    private Integer ifAllowDelete;


    private static final long serialVersionUID = 1L;

}