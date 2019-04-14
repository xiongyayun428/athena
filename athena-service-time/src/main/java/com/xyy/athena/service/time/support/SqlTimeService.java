package com.xyy.athena.service.time.support;

import lombok.Setter;

import javax.sql.DataSource;
import java.sql.*;

/**
 * <p><b>数据库时间戳服务</b></p>
 *
 * @author XYY
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
@Setter
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
