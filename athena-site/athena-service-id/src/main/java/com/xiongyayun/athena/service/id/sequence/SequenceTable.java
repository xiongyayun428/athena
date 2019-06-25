package com.xiongyayun.athena.service.id.sequence;

import com.xiongyayun.athena.service.time.TimeService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p><b>序列号表</b></p>
 *
 * @author: Yayun.Xiong
 * @date 2019-04-14
 */
interface SequenceTable {
	/**
	 * 获取表名
	 * @return
	 */
	String getTableName();

	/**
	 * 获取主键列
	 * @return
	 */
	String getIdColume();

	/**
	 * 获取类型列
	 * @return
	 */
	String getTypeColume();

	/**
	 * 获取数据源
	 * @return
	 */
	DataSource getDataSource();

	/**
	 * 获取时间服务
	 * @return
	 */
	TimeService getTimeService();

	/**
	 * 初始化
	 */
	void init();

	/**
	 * 重置
	 */
	void reset();

	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection() throws SQLException;

	/**
	 * 查询
	 * @param conn
	 * @param type
	 * @param dateCutoff
	 * @return
	 * @throws SQLException
	 */
	long[] doSelect(Connection conn, String type, boolean dateCutoff) throws SQLException;

	/**
	 * 新增
	 * @param conn
	 * @param type
	 * @param start
	 * @param timestamp
	 * @throws SQLException
	 */
	void doInsert(Connection conn, String type, long start, long timestamp) throws SQLException;

	/**
	 * 重置
	 * @param conn
	 * @param type
	 * @param timestamp
	 * @param seq
	 * @return
	 * @throws SQLException
	 */
	int doReset(Connection conn, String type, long timestamp, long seq) throws SQLException;

	/**
	 * 修改
	 * @param conn
	 * @param type
	 * @param count
	 * @param timestamp
	 * @param seq
	 * @param computeSymbol
	 * @return
	 * @throws SQLException
	 */
	int doUpdate(Connection conn, String type, int count, long timestamp, long seq, char computeSymbol) throws SQLException;

}
