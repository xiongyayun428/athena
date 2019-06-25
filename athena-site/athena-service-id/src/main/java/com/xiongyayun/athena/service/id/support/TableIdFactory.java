package com.xiongyayun.athena.service.id.support;

import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ID生成器的表工厂
 *
 * @author: Yayun.Xiong
 * @date 2019-04-14 16:48
 */
@Setter
@Service
public class TableIdFactory extends AbstractIdFactory {
	protected Log log = LogFactory.getLog(this.getClass());
	private DataSource dataSource;
	protected String type = null;
	private String prefix;
	private int length = 8;
	private boolean datePrefix;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private long tempLong = 100000000L;

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
		if (this.dataSource == null) {
			throw new RuntimeException("you must set dataSource in tableIdFactory.");
		}
		if (this.type == null) {
			throw new RuntimeException("you must setType(String) in tableIdFactory.");
		}
		return format(internalGenerate());
	}

	protected synchronized long internalGenerate() {
		try {
			Connection conn = this.dataSource.getConnection();
			try {
				long result = -1L;
				int rows = 0;
				do {
					PreparedStatement qps = conn.prepareStatement("SELECT ID FROM ID_FACTORY WHERE TYPE=?");
					try {
						qps.setString(1, this.type);
						ResultSet rs = qps.executeQuery();
						if (rs.next()) {
							result = rs.getLong(1);
						} else {
							throw new RuntimeException("Please preset id for type:" + this.type);
						}
						rs.close();
					} catch (SQLException sqle) {
						log.error("could not read a id value!", sqle);
						throw sqle;
					} finally {
						qps.close();
					}

					PreparedStatement ups = conn.prepareStatement("UPDATE ID_FACTORY SET ID=? WHERE TYPE=? AND ID=?");
					try {
						long next = result + 1L;
						if (next < 0L)
							next = 0L;
						ups.setLong(1, next);
						ups.setString(2, this.type);
						ups.setLong(3, result);
						rows = ups.executeUpdate();
					} catch (SQLException sqle) {
						log.error("could not update id value!", sqle);
						throw sqle;
					} finally {
						ups.close();
					}
				} while (rows == 0);
				return result;
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
