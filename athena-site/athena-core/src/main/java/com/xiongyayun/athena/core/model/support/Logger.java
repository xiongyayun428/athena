package com.xiongyayun.athena.core.model.support;

import com.xiongyayun.athena.core.model.BaseEntity;
import lombok.Data;

/**
 * Logger
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Data
public class Logger extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5604511735164458525L;
	private long id;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求IP
     */
    private String ip;
    /**
     * 请求方式
     */
    private String httpMethod;
    /**
     * 请求类，方法
     */
    private String classMethod;
    /**
     * 方法参数
     */
    private String args;
    /**
     * 请求参数
     */
    private String reqParams;
    /**
     * 响应参数
     */
    private String respParams;
    /**
     * 响应时间
     */
    private long spendTime;
//    /**
//     * 日志类型（web、service）
//     */
//    private String logType;
//    /**
//     * 用户信息
//     */
//    private User user;

}
