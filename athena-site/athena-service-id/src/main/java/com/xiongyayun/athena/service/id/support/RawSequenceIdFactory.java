package com.xiongyayun.athena.service.id.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p><b>通过数据语句查询序列号ID</b></p>
 *
 * @author: Yayun.Xiong
 * @date 2019-04-14 17:03
 */
@Service
public class RawSequenceIdFactory extends AbstractIdFactory {
	@Getter
	@Setter
	private DataSource dataSource;
	@Getter
	@Setter
	private String sql;

	@Override
	public Object generate() {
		return internalGenerate();
	}

	protected long internalGenerate() {
		try {
			if (getDataSource() == null) {
				throw new RuntimeException("you must set dataSource in SequenceIdFactory.");
			}
			if (getSql() == null) {
				throw new RuntimeException("you must setSql(String) in SequenceIdFactory.");
			}
			Connection conn = this.dataSource.getConnection();
			conn.setAutoCommit(true);
			PreparedStatement qps = conn.prepareStatement(this.sql);
			try {
				ResultSet rs = qps.executeQuery();
				if (rs.next()) {
					return rs.getLong(1);
				}
				throw new RuntimeException("sequence_error");
			} catch (SQLException e) {
				log.error("internalGenerate idFactory error: " + e.getMessage(), e);
			} finally {
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
