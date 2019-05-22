package com.xyy.athena.user.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
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

    private String createUser;

    private Date createDate;

    private String lastUpdateUser;

    private Date lastUpdateDate;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp == null ? null : accessIp.trim();
    }

    public String getAccessMac() {
        return accessMac;
    }

    public void setAccessMac(String accessMac) {
        this.accessMac = accessMac == null ? null : accessMac.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastVisitIp() {
        return lastVisitIp;
    }

    public void setLastVisitIp(String lastVisitIp) {
        this.lastVisitIp = lastVisitIp == null ? null : lastVisitIp.trim();
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public Integer getIfAllowDelete() {
        return ifAllowDelete;
    }

    public void setIfAllowDelete(Integer ifAllowDelete) {
        this.ifAllowDelete = ifAllowDelete;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", realName=").append(realName);
        sb.append(", nickName=").append(nickName);
        sb.append(", accessIp=").append(accessIp);
        sb.append(", accessMac=").append(accessMac);
        sb.append(", status=").append(status);
        sb.append(", lastVisitIp=").append(lastVisitIp);
        sb.append(", lastVisitDate=").append(lastVisitDate);
        sb.append(", visitCount=").append(visitCount);
        sb.append(", visitDate=").append(visitDate);
        sb.append(", errorTimes=").append(errorTimes);
        sb.append(", ifAllowDelete=").append(ifAllowDelete);
        sb.append(", createUser=").append(createUser);
        sb.append(", createDate=").append(createDate);
        sb.append(", lastUpdateUser=").append(lastUpdateUser);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}