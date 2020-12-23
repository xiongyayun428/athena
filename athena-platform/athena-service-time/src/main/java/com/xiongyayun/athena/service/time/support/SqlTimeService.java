package com.xiongyayun.athena.service.time.support;

import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

/**
 * <p><b>数据库时间戳服务</b></p>
 *
 * @author Yayun.Xiong
 * @since 2015年4月26日
 * @since 1.8
 *
 */
@Setter
@Service
public class SqlTimeService extends DefaultTimeService {
	@Setter
	private String timestampSql = "values(current_timestamp)";
	@Setter
	private DataSource dataSource;

	protected long remoteTimestamp() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (this.dataSource == null) {
				throw new RuntimeException("you must set dataSource in tableIdFactory.");
			}
			conn = this.dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(this.timestampSql);
			if (rs.next()) {
				Timestamp ts = rs.getTimestamp(1);
				return ts.getTime();
			} else {
				throw new IllegalStateException("cannot get timestamp from database.");
			}
		} catch (SQLException e) {
			throw new IllegalStateException("cannot get timestamp from database.", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
	}
}
