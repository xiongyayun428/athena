package com.xiongyayun.athena.service.id.support;

import com.xiongyayun.athena.service.id.IdFactory;
import lombok.Getter;
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
 * <p><b>ID生成工厂的数据库操作</b></p>
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 16:52
 */
@Service
public class JdbcIdFactory implements IdFactory {
	protected Log log = LogFactory.getLog(this.getClass());
	@Setter
	private DataSource dataSource;
	@Setter
	protected String type = null;
	@Setter
	private String prefix;
	@Setter
	private int length = 8;
	@Setter
	private boolean datePrefix;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private long tempLong = 100000000L;

	/**
	 * 重试计数
	 */
	@Getter
	@Setter
	private int retryCounter = 5;
	@Getter
	@Setter
	private String updateSql = "UPDATE ID_FACTORY SET ID = ID + ? WHERE TYPE = ?";
	@Getter
	@Setter
	private String selectSql = "SELECT ID FROM ID_FACTORY WHERE TYPE = ?";
	@Getter
	@Setter
	private String insertSql = "INSERT INTO ID_FACTORY (ID, TYPE) VALUES (0, ?)";

	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
	}

	public void setLength(int length) {
		this.length = length;
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
		return generate(1)[0];
	}

	@Override
	public Object[] generate(int number) {
		long seqNo = internalGenerate(number);
		Object[] object = new String[number];
		for (int i = 1; i <= number; i++) {
			object[(i - 1)] = format(seqNo - number + i);
		}
		return object;
	}

	private long internalGenerate(int number) {
		try {
			if (this.dataSource == null) {
				throw new RuntimeException("you must set dataSource in tableIdFactory.");
			}
			if (this.type == null) {
				throw new RuntimeException("you must setType(String) in tableIdFactory.");
			}
			Connection conn = this.dataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ups = conn.prepareStatement(this.updateSql);
			PreparedStatement qps = conn.prepareStatement(this.selectSql);
			try {
				ups.setInt(1, number);
				ups.setString(2, this.type);
				int rows = 0;
				for (int i = 0; (i < this.retryCounter) && (rows != 1); i++) {
					try {
						rows = ups.executeUpdate();
						if (rows == 0) {
							PreparedStatement ips = conn.prepareStatement(this.insertSql);
							ips.setString(1, this.type);
							ips.executeUpdate();
						}
					} catch (Exception e) {
						log.error("could not update id table retryCounter: " + i, e);
						rows = -1;
					}
				}
				ResultSet rs = qps.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				}
				throw new RuntimeException("jnlno_table_error");
			} catch (SQLException e) {
				log.error("internalGenerate error: " + e.getMessage(), e);
			} finally {
				if (ups != null) {
					ups.close();
				}
				if (qps != null) {
					qps.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			return 0L;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
