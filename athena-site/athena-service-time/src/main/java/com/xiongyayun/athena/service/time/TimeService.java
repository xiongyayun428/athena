package com.xiongyayun.athena.service.time;

import com.xiongyayun.athena.service.Service;

/**
 * <p><b>时间服务</b></p>
 *
 * @author: Yayun.Xiong
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
public interface TimeService extends Service {
	int FIRST_DAY_OF_WEEK = 2;
	int MINUTE = 12;
	int HOUR = 11;
	int DAY = 5;
	int WEEK = 3;
	int MONTH = 2;
	int YEAR = 1;

	boolean isCutoff(int i, long lastTimestamp);

	boolean isCutoff(int i, long nowTimestamp, long lastTimestamp);

	/**
	 * 获取当前时间
	 * @return
	 */
	long currentTimeMillis();
}
