package com.xiongyayun.athena.service.id.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * <p><b>序列号表</b></p>
 *
 * @author: Yayun.Xiong
 * @date 2019-04-14
 */
public class SequenceTableImpl extends AbstractSequenceTable {

	@Override
	public void init() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getDataSource().getConnection();
			stmt = conn.createStatement();
			try {
				// 查询是否存在表
				stmt.executeQuery("SELECT " + getTypeColume() + ", " + getIdColume() + ", DATETIME FROM " + getTableName() + " WHERE 1=2".toUpperCase());
			} catch (SQLException e) {
				try {
					// 创建表
					String sql = "CREATE TABLE " + getTableName() + "(" + getTypeColume() + " CHAR(50) NOT NULL, " + getIdColume() + " BIGINT, DATETIME TIMESTAMP, PRIMARY KEY(" + getTypeColume() + "))".toUpperCase();
					boolean flag = stmt.execute(sql);
					System.err.println(sql);
					log.info("table '" + getTableName() + "' init " + (flag ? "failure" : "succeed"));
				} catch (SQLException se) {
					log.warn("cannot create sequence table, " + se.getMessage(), se);
				}
			}
		} catch (Throwable e) {
			throw new RuntimeException("init table: '" + getTableName() + "' error", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {}
		}
	}

	@Override
	public void reset() {
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder("UPDATE ").append(getTableName()).append(" SET ").append(getIdColume()).append("=0, DATETIME='").append(new Timestamp(getTimeService().currentTimeMillis())).append("'");
			boolean flag = stmt.execute(sql.toString().toUpperCase());
			System.err.println(sql);
			log.info("table '" + getTableName() + "' reset " + (flag ? "failure" : "succeed"));
			conn.commit();
		} catch (SQLException e) {
			log.warn("cannot reset sequence table, " + e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException se) {}
		} finally {
			try {
				conn.close();
				conn.setAutoCommit(true);
			} catch (Exception e) {}
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	@Override
	public long[] doSelect(Connection conn, String type, boolean dateCutoff) throws SQLException {
		long now = getTimeService().currentTimeMillis();
		long[] result = { -1L, -1L };
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getSelectSql());
			ps.setString(1, type);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				result[0] = rs.getLong(1);
				Timestamp ts = rs.getTimestamp(2);
				if ((ts != null) && (ts.getTime() > now)) {
					log.warn("sequence datetime is later than system datetime.");
					result[1] = ts.getTime();
				} else {
					result[1] = now;
					if ((dateCutoff) && (getTimeService().isCutoff(5, now, ts.getTime()))) {
						int i = doReset(conn, type, now, result[0]);
						if (i == 1) {
							result[0] = 0L;
						} else {
							return doSelect(conn, type, dateCutoff);
						}
					}
				}
			} else {
				result[1] = now;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		return result;
	}

	@Override
	public void doInsert(Connection conn, String type, long start, long timestamp) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(getInsertSql());
		try {
			ps.setString(1, type);
			ps.setLong(2, start);
			ps.setTimestamp(3, new Timestamp(timestamp));
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
	}

	@Override
	public int doReset(Connection conn, String type, long timestamp, long seq) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(getResetSql());
		try {
			ps.setTimestamp(1, new Timestamp(timestamp));
			ps.setString(2, type);
			ps.setLong(3, seq);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
	}

	@Override
	public int doUpdate(Connection conn, String type, int count, long timestamp, long seq, char computeSymbol) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(getUpdateSql(computeSymbol));
		try {
			ps.setLong(1, count);
			ps.setTimestamp(2, new Timestamp(timestamp));
			ps.setString(3, type);
			ps.setLong(4, seq);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			ps.close();
		}
	}

}
