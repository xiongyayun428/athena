package com.xyy.athena.service.id.sequence;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * <p><b>序列号抽象工厂</b></p>
 *
 * @author XYY
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
public abstract class AbstractSequenceFactory implements SequenceFactory {
	protected Log log = LogFactory.getLog(this.getClass());
	@Getter
	@Setter
	private String type;
	private SequenceFormat format;

	@Override
	public SequenceFormat getFormat() {
		return format;
	}

	public void setPattern(String pattern) {
		this.format = SequenceFormat.getInstance(pattern);
	}

	@Override
	public Object generate() {
		return generate(1)[0];
	}

	@Override
	public Object[] generate(int count) {
		if (this.type == null) {
			throw new RuntimeException("you must setType(String) in SequenceFactory.");
		}
		long[] seq = internalGenerate(count);
		if (count == 1) {
			if (getFormat() == null) {
				return new Object[] { Long.valueOf(seq[0]) };
			}
			return new Object[] { format(seq[0], seq[1]) };
		}
		if (getFormat() == null) {
			Object[] ret = new Long[count];
			for (int i = 0; i < count; i++) {
				ret[i] = Long.valueOf(seq[0] + i);
			}
			return ret;
		}
		Object[] ret = new String[count];
		for (int i = 0; i < count; i++) {
			ret[i] = format(seq[0] + i, seq[1]);
		}
		return ret;
	}
	
	/**
	 * 回收未使用完成的ID
	 * @author XYY
	 */
	protected abstract void reverse();

	private String format(long l, long timestamp) {
		return getFormat().format(this, l, timestamp < 0L ? new Date() : new Date(timestamp));
	}

	protected abstract long[] internalGenerate(int count);
}
