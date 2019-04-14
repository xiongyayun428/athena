package com.xyy.athena.service.id.sequence;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p><b></b></p>
 *
 * @author XYY
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
public abstract class TableSequenceFactory extends AbstractSequenceFactory implements SequenceTableAware {
	@Setter
	private int retryCount = 20;
	@Getter
	private SequenceTable table;
	@Getter
	@Setter
	private boolean dateCutoff;
	@Setter
	private boolean autoCommit = true;

	@Override
	public void setTable(SequenceTable t) {
		this.table = t;
	}

	@Override
	protected synchronized long[] internalGenerate(int number) {
		try {
			Connection conn = this.table.getConnection();
			boolean oldCommit = conn.getAutoCommit();
			try {
				if (!(this.autoCommit)) {
					conn.setAutoCommit(false);
				}
				int rows = 0;
				long[] seq = null;
				for (int i = 0; (i < this.retryCount) && (rows != 1); ++i) {
					try {
						seq = this.table.doSelect(conn, getType(), this.dateCutoff);
						// 不存在值
						if (seq[0] == -1L) {
							this.table.doInsert(conn, getType(), 0L, seq[1]);
							seq[0] = 0L;
						}
						rows = this.table.doUpdate(conn, getType(), number, seq[1], seq[0], '+');
					} catch (SQLException e) {
						this.log.warn("get id retry: " + i + ", cause:" + e);
						if (i == this.retryCount - 1) {
							throw e;
						}
					}
				}
				if (!(this.autoCommit)) {
					conn.commit();
				}
				return seq;
			} catch (SQLException e) {
				log.error("internalGenerate error: " + e.getMessage(), e);
			} finally {
				if (!(this.autoCommit)) {
					conn.setAutoCommit(oldCommit);
				}
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public void internalReverse(int unused) {
		try {
			Connection conn = this.table.getConnection();
			boolean oldCommit = conn.getAutoCommit();
			try {
				if (!(this.autoCommit)) {
					conn.setAutoCommit(false);
				}
				int rows = 0;
				long[] seq;
				for (int i = 0; (i < this.retryCount) && (rows != 1); ++i) {
					try {
						seq = this.table.doSelect(conn, getType(), this.dateCutoff);
						// 不存在值
						if (seq[0] == -1L) {
							return;
						}
						rows = this.table.doUpdate(conn, getType(), unused, seq[1], seq[0], '-');
					} catch (SQLException e) {
						this.log.warn("get id retry: " + i + ", cause:" + e);
						if (i == this.retryCount - 1) {
							throw e;
						}
					}
				}
				if (!(this.autoCommit)) {
					conn.commit();
				}
			} catch (SQLException e) {
				log.error("internalGenerate error: " + e.getMessage(), e);
			} finally {
				if (!(this.autoCommit)) {
					conn.setAutoCommit(oldCommit);
				}
				conn.close();
			}
		} catch (SQLException ee) {
			throw new RuntimeException(ee);
		}
	}
}
