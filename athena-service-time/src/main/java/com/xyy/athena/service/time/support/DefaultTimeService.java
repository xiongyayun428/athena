package com.xyy.athena.service.time.support;

import com.xyy.athena.service.time.TimeService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p><b></b></p>
 *
 * @author XYY
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
@Service
public class DefaultTimeService implements TimeService {
	private long timestamp;
	private long localTimestamp;
	@Setter
	private int sychronizeInterval = 60000;
	@Setter
	private TimeZone timeZone;
	@Setter
	private Locale locale;
	@Setter
	private int firstDayOfWeek = 2;

	public final void reset() {
		this.timestamp = remoteTimeMillis();
		this.localTimestamp = localTimeMillis();
	}

	protected long localTimeMillis() {
		return System.currentTimeMillis();
	}

	private long elapse() {
		return System.currentTimeMillis() - this.localTimestamp;
	}

	protected long remoteTimeMillis() {
		return System.currentTimeMillis();
	}

	@Override
	public final synchronized long currentTimeMillis() {
		long e = elapse();
		if (e > this.sychronizeInterval) {
			reset();
		}
		return this.timestamp + elapse();
	}

	@Override
	public final boolean isCutoff(int i, long lastTimestamp) {
		long nowTimestamp = currentTimeMillis();
		return isCutoff(i, nowTimestamp, lastTimestamp);
	}

	@Override
	public final boolean isCutoff(int i, long nowTimestamp, long lastTimestamp) {
		return getUnit(i, nowTimestamp) - getUnit(i, lastTimestamp) > 0;
	}

	private int getUnit(int i, long timestamp) {
		Calendar cal;
		if ((this.timeZone != null) && (this.locale != null)) {
			cal = Calendar.getInstance(this.timeZone, this.locale);
		} else if (this.timeZone != null) {
			cal = Calendar.getInstance(this.timeZone);
		} else if (this.locale != null) {
			cal = Calendar.getInstance(this.locale);
		} else {
			cal = Calendar.getInstance();
		}
		cal.setFirstDayOfWeek(this.firstDayOfWeek);
		cal.setTimeInMillis(timestamp);
		return cal.get(i);
	}

	public static void main(String[] args) {
		DefaultTimeService a = new DefaultTimeService();
		for (int i = 0; i < 10; i++) {
			System.err.println(a.currentTimeMillis());
		}
	}
}
