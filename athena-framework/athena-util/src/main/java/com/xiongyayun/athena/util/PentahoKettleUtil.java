package com.xiongyayun.athena.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import java.util.Iterator;
import java.util.Map;

/**
 * PentahoKettleUtil
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/10/23
 */
public class PentahoKettleUtil {
	private static final Logger logger = LoggerFactory.getLogger(PentahoKettleUtil.class);

	/**
	 * 通过文件方式执行转换
	 * @param ktrFilePath   ktr转换文件
	 * @param variables     变量
	 * @throws Exception    转换报错
	 */
	public static Trans runTransfer(String ktrFilePath, Map<String, String> variables) throws Exception {
		return runTransfer(ktrFilePath, variables, null);
	}

	/**
	 *
	 * 通过文件方式执行转换
	 * @param ktrFilePath   ktr转换文件
	 * @param variables     变量
	 * @param arguments     参数
	 * @throws Exception    转换报错
	 */
	public static Trans runTransfer(String ktrFilePath, Map<String, String> variables, String[] arguments) throws Exception {
		logger.info("execute krt, ktrFilePath={}, variables={}", ktrFilePath, variables);
		try {
			// 初始化
			KettleEnvironment.init();
			EnvUtil.environmentInit();
			// 转换元对象
			TransMeta transMeta = new TransMeta(ktrFilePath);
			// 转换
			Trans trans = new Trans(transMeta);
			if (variables != null) {
				Iterator params = variables.keySet().iterator();
				while (params.hasNext()) {
					String variableName = (String) params.next();
					trans.setVariable(variableName, variables.get(variableName));
				}
			}
			trans.setArguments(arguments);
			// 执行转换
			trans.execute(null);
			// 等待转换执行结束
			trans.waitUntilFinished();
			// 抛出异常
			if (trans.getErrors() > 0) {
				throw new Exception("执行转换发生异常，异常个数[" + trans.getErrors() + "]");
			}
			logger.info("Pentaho Kettle Transfer [{}]: Successful", ktrFilePath);
			return trans;
		} catch (Exception e) {
			throw new Exception("Pentaho Kettle Transfer [" + ktrFilePath + "]: " + e.getMessage(), e);
		}
	}

	/**
	 * 通过文件方式执行job
	 * @param kjbFilePath   job作业文件
	 * @param variables     变量
	 * @throws Exception    转换报错
	 */
	public static Job runJob(String kjbFilePath, Map<String, String> variables) throws Exception {
		return runJob(kjbFilePath, variables, null);
	}

	/**
	 * 通过文件方式执行job
	 * @param kjbFilePath   job作业文件
	 * @param variables     变量
	 * @param arguments
	 * @throws Exception    转换报错
	 */
	public static Job runJob(String kjbFilePath, Map<String, String> variables, String[] arguments) throws Exception {
		logger.info("execute kjb, kjbFilePath={}, variables={}, arguments={}", kjbFilePath, variables, arguments);
		try {
			// 初始化
			KettleEnvironment.init();
			EnvUtil.environmentInit();
			JobMeta jobMeta = new JobMeta(kjbFilePath, null);
			Job job = new Job(null, jobMeta);
			if (variables != null) {
				Iterator params = variables.keySet().iterator();
				while(params.hasNext()) {
					String variableName = (String) params.next();
					job.setVariable(variableName, variables.get(variableName));
				}
			}
			job.setArguments(arguments);
			job.start();
			job.waitUntilFinished();
			if (job.getErrors() > 0) {
				throw new Exception("执行job发生异常，异常个数[" + job.getErrors() + "]");
			}
			logger.info("Pentaho Kettle Job [{}]: Successful", kjbFilePath);
			return job;
		} catch (Exception e) {
			throw new Exception("Pentaho Kettle Transfer [" + kjbFilePath + "]: " + e.getMessage(), e);
		}
	}
}
