package com.xiongyayun.athena.core.model.support;

import lombok.Data;

/**
 * Journal
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Data
public class Journal {
    /**
	 *
	 */
	private static final long serialVersionUID = -5604511735164458525L;
	private long id;

	/**
	 * 注释
	 */
	private String annotation;
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
    private String head;
    /**
     * 请求体
     */
    private byte[] requestBody;
    /**
     * 响应体
     */
    private String responseBody;
    /**
     * 响应时间（毫秒）
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
