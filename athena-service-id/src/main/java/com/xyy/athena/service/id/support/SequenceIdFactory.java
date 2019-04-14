package com.xyy.athena.service.id.support;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p><b>序列号ID工厂</b></p>
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 17:04
 */
@Service
public class SequenceIdFactory extends RawSequenceIdFactory {
	@Setter
	private String prefix;
	private int length = 8;
	@Setter
	private boolean datePrefix;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private long tempLong = 100000000L;


	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
	}

	public void setLength(int i) {
		this.length = i;
		this.tempLong = Double.valueOf(Math.pow(10.0D, this.length)).longValue();
	}

	protected String format(long l) {
		StringBuffer buf = new StringBuffer();
		if (this.prefix != null) {
			buf.append(this.prefix);
		}
		if (this.datePrefix) {
			buf.append(this.dateFormat.format(new Date()));
		}
		String longStr = Long.valueOf(this.tempLong + l).toString();
		buf.append(longStr.substring(longStr.length() - this.length));
		return buf.toString();
	}

	@Override
	public Object generate() {
		return format(internalGenerate());
	}

}
