package com.xyy.athena.service.id.support;

import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p><b>简单的ID生成工厂</b></p>
 * 不依靠数据库(线程安全)
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 17:10
 */
@Service
public class SimpleIdFactory extends AbstractIdFactory {
	protected Log log = LogFactory.getLog(this.getClass());
	@Setter
	private String prefix;
	@Setter
	private boolean datePrefix;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private int length = 8;
	private int dateLength = 8;
	private long tempLong = 100000000L;
	/**
	 * 当前ID
	 */
	@Setter
	private int currentId = 0;
	private Object semaphore = new Object();


	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
		this.dateLength = format.length();
	}

	public void setLength(int length) {
		this.length = length;
		// Math.pow次幂
		this.tempLong = Double.valueOf(Math.pow(10.0D, this.length)).longValue();
	}

	protected String format(long l) {
		StringBuffer buf = new StringBuffer();
		if (this.prefix != null) {
			buf.append(this.prefix);
		}
		if (this.datePrefix) {
			String dateStr = this.dateFormat.format(new Date());
			if (dateStr.length() != this.dateLength) {
				log.error("unmatch date:" + dateStr);
			}
			buf.append(dateStr);
		}
		String longStr = Long.valueOf(this.tempLong + l).toString();
		buf.append(longStr.substring(longStr.length() - this.length));
		return buf.toString();
	}

	@Override
	public Object generate() {
		synchronized (this.semaphore) {
			this.currentId += 1;
			if (this.currentId >= this.tempLong) {
				this.currentId = 1;
			}
			return format(this.currentId);
		}
	}

	public static void main(String[] args) {
		SimpleIdFactory a = new SimpleIdFactory();
		a.setLength(5);
		a.setDatePrefix(true);
		for (int i = 0; i < 10; i++) {
			System.err.println(a.generate());
		}
//		System.out.println("------------");
//		Object[] b = a.generate(5);
//		System.out.println(b.length);
	}

}
