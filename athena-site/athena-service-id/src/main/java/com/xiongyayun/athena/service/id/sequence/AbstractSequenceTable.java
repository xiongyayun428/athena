package com.xiongyayun.athena.service.id.sequence;

import com.xiongyayun.athena.service.time.TimeService;
import com.xiongyayun.athena.service.time.support.DefaultTimeService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>抽象序列表</b></p>
 *
 * @author Yayun.Xiong
 * @date 2019-04-14
 */
public abstract class AbstractSequenceTable implements SequenceTable, InitializingBean {
	protected Log log = LogFactory.getLog(this.getClass());
	@Setter
	private DataSource dataSource;
	@Getter
	@Setter
	private String tableName = "SEQ_NO";
	@Getter
	@Setter
	private String idColume = "ID";
	@Getter
	@Setter
	private String typeColume = "TYPE";
	@Getter
	@Setter
	private TimeService timeService = new DefaultTimeService();

	private String insert;
	private Map<Character, String> update;
	private String select;
	private String reset;
	
	
	@Override
	public void afterPropertiesSet() {
		init();
	}

	@Override
	public DataSource getDataSource() {
		if (dataSource == null) {
			throw new RuntimeException("dataSource is null");
		}
		return dataSource;
	}

	protected String getSelectSql() {
		if (this.select == null) {
			this.select = ("SELECT " + this.idColume + ", DATETIME FROM " + this.tableName + " WHERE " + this.typeColume + "=?").toUpperCase();
		}
		return this.select;
	}

	protected String getResetSql() {
		if (this.reset == null) {
			this.reset = ("UPDATE " + this.tableName + " SET " + this.idColume + "=0, DATETIME=? WHERE " + this.typeColume + "=? AND " + this.idColume + "=?").toUpperCase();
		}
		return this.reset;
	}

	protected String getUpdateSql(char computeSymbol) {
		if (update == null) {
			update = new HashMap<>();
		}
		String update = this.update.get(computeSymbol);
		if (update == null) {
			update = ("UPDATE " + this.tableName + " SET " + this.idColume + "=" + this.idColume + computeSymbol + "?, DATETIME=? WHERE " + this.typeColume + "=? AND " + this.idColume + "=?").toUpperCase();
			this.update.put(computeSymbol, update);
		}
		return update;
	}

	protected String getInsertSql() {
		if (this.insert == null) {
			this.insert = ("INSERT INTO " + this.tableName + " (" + this.typeColume + "," + this.idColume + ", datetime) values (?,?,?)").toUpperCase();
		}
		return this.insert;
	}

}
