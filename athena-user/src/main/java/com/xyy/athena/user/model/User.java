package com.xyy.athena.user.model;

import com.xyy.athena.core.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-05-22
 */
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "nick_name")
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