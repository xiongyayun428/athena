package com.xyy.athena.service.id.sequence;

import com.xyy.athena.service.id.IdFactory;
import lombok.Getter;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p><b>序列号格式化</b></p>
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 18:07
 */
public class SequenceFormat {
	@Getter
	private String pattern;
	@Getter
	private String datePattern;
	private Format[] format;

	public static SequenceFormat getInstance(String pattern) {
		return new SequenceFormat(pattern);
	}

	private SequenceFormat(String pattern) {
		this.pattern = pattern;
		compile();
	}

	public String format(IdFactory idf, long id, Date d) {
		StringBuffer buf = new StringBuffer(32);
		for (int i = 0; i < this.format.length; i++) {
			Format f = this.format[i];
			if ((f instanceof FieldFormat)) {
				f.format(idf, buf);
			} else if ((f instanceof NumberFormat)) {
				f.format(Long.valueOf(id), buf);
			} else if ((f instanceof DateTimeFormat)) {
				f.format(d, buf);
			} else {
				f.format(null, buf);
			}
		}
		return buf.toString();
	}

	protected void compile() {
		char[] chars = this.pattern.toCharArray();
		boolean bStart = false;
		StringBuffer buf = new StringBuffer();
		List<Format> formats = new ArrayList<>();
		for (int i = 0; i < chars.length; i++) {
			if ('{' == chars[i]) {
				if (bStart) {
					throw new IllegalArgumentException("invalid pattern: " + this.pattern);
				}
				bStart = true;
				if (buf.length() > 0) {
					formats.add(new StringFormat(buf.toString()));
					buf.setLength(0);
				}
			} else if ('}' == chars[i]) {
				if (!bStart) {
					throw new IllegalArgumentException("invalid pattern: " + this.pattern);
				}
				bStart = false;
				if (buf.length() > 0) {
					formats.add(parse(buf.toString()));
					buf.setLength(0);
				}
			} else {
				buf.append(chars[i]);
			}
		}
		if (bStart) {
			throw new IllegalArgumentException("invalid pattern: " + this.pattern);
		}
		if (buf.length() > 0) {
			formats.add(new StringFormat(buf.toString()));
			buf.setLength(0);
		}
		this.format = formats.toArray(new Format[formats.size()]);
	}

	protected Format parse(String t) {
		char z = t.charAt(0);
		switch (z) {
			case '#':
				return new NumberFormat(t.length());
			case '$':
				return new FieldFormat(t.substring(1));
			default:
				this.datePattern = t;
				return new DateTimeFormat(t);
		}
	}

	interface Format {
		/**
		 * 格式化
		 * @param data
		 * @param buf
		 */
		void format(Object data, StringBuffer buf);
	}

	class DateTimeFormat implements SequenceFormat.Format {
		private DateFormat dateFormat;

		@Override
		public void format(Object data, StringBuffer buf) {
			if ((data != null) && (!(data instanceof Date))) {
				throw new IllegalArgumentException("input is not a date: " + data);
			}
			Date d = (Date) data;
			if (d == null) {
				d = new Date();
			}
			buf.append(this.dateFormat.format(d));
		}

		DateTimeFormat(String format) {
			this.dateFormat = new SimpleDateFormat(format);
		}
	}

	class FieldFormat implements SequenceFormat.Format {
		private String fName;

		@Override
		public void format(Object data, StringBuffer buf) {
			if ((data == null) || (!(data instanceof IdFactory))) {
				throw new IllegalArgumentException("input is not a id factory: " + data);
			}
			Class<? extends Object> c = data.getClass();
			try {
				if (c == null) {
					return;
				}
				Method get = c.getMethod("get" + this.fName, new Class[0]);
				if (get == null) {
					return;
				}
				Object o = get.invoke(data, new Object());
				if (o == null) {
					throw new IllegalArgumentException("field '" + this.fName + "'s value is null: " + data);
				}
				buf.append(o);
			} catch (Exception e) {
				throw new IllegalArgumentException("cannot get field '" + this.fName + "'s value: " + data);
			}
		}

		FieldFormat(String fName) {
			this.fName = (Character.toUpperCase(fName.charAt(0)) + fName.substring(1));
		}
	}

	class NumberFormat implements SequenceFormat.Format {
		private int length;
		private long seed;

		@Override
		public void format(Object data, StringBuffer buf) {
			if ((data == null) || (!(data instanceof Number))) {
				throw new IllegalArgumentException("input is not a number: " + data);
			}
			String str = Long.valueOf(this.seed + ((Number) data).longValue()).toString();
			buf.append(str.substring(str.length() - this.length));
		}

		NumberFormat(int length) {
			this.length = length;
			this.seed = Double.valueOf(Math.pow(10.0D, this.length)).longValue();
		}
	}

	class StringFormat implements SequenceFormat.Format {
		private String val;

		@Override
		public void format(Object data, StringBuffer buf) {
			if (this.val == null) {
				throw new IllegalArgumentException("input is null.");
			}
			buf.append(this.val);
		}

		StringFormat(String value) {
			this.val = value;
		}
	}
}
