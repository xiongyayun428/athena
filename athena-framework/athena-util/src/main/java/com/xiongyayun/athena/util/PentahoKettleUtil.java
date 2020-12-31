package com.xiongyayun.athena.util;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.KettleLoggingEventListener;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * PentahoKettleUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/10/23
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
		return runTransfer(new File(ktrFilePath), variables);
	}

	public static Trans runTransfer(File ktrFile, Map<String, String> variables) throws Exception {
		return runTransfer(ktrFile, variables, null);
	}

	public static Trans runTransfer(String ktrFile, Map<String, String> variables, String[] arguments) throws Exception {
		return runTransfer(new File(ktrFile), variables, arguments);
	}

	/**
	 * 通过文件方式执行转换
	 * @param ktrFile		ktr转换文件
	 * @param variables     变量
	 * @param arguments     参数
	 * @throws Exception    转换报错
	 */
	public static Trans runTransfer(File ktrFile, Map<String, String> variables, String[] arguments) throws Exception {
		logger.info("execute krt, ktrFilePath={}, variables={}", ktrFile.getAbsolutePath(), variables);
		if (!ktrFile.exists()) {
			throw new FileNotFoundException("Pentaho Kettle Transfer [" + ktrFile.getAbsolutePath() + "]: File Not Found");
		}
		try {
			// 初始化
			KettleEnvironment.init();
			EnvUtil.environmentInit();
			// 转换元对象
			TransMeta transMeta = new TransMeta(ktrFile.getAbsolutePath());
			// 转换
			Trans trans = new Trans(transMeta);
			if (variables != null) {
				for (String variableName : variables.keySet()) {
					trans.setVariable(variableName, variables.get(variableName));
				}
			}
			trans.setArguments(arguments);
			// 添加运行日志监听
			trans.setLogLevel(LogLevel.BASIC);
			KettleLoggingEventListener kettleLoggingEventListener = printLog();

			// 执行转换
			trans.execute(null);
			// 等待转换执行结束
			trans.waitUntilFinished();
			// 删除运行日志监听
			KettleLogStore.getAppender().removeLoggingEventListener(kettleLoggingEventListener);
			// 抛出异常
			if (trans.getErrors() > 0) {
				throw new Exception("执行转换发生异常，异常个数[" + trans.getErrors() + "]");
			}
			logger.info("Pentaho Kettle Transfer [{}]: Successful", ktrFile.getAbsolutePath());
			return trans;
		} catch (Exception e) {
			throw new Exception("Pentaho Kettle Transfer [" + ktrFile.getAbsolutePath() + "]: " + e.getMessage(), e);
		}
	}

	/**
	 * 通过文件方式执行job
	 * @param kjbFilePath   job作业文件
	 * @param variables     变量
	 * @throws Exception    转换报错
	 */
	public static Job runJob(String kjbFilePath, Map<String, String> variables) throws Exception {
		return runJob(new File(kjbFilePath), variables);
	}

	public static Job runJob(File kjbFile, Map<String, String> variables) throws Exception {
		return runJob(kjbFile, variables, null);
	}

	public static Job runJob(String kjbFilePath, Map<String, String> variables, String[] arguments) throws Exception {
		return runJob(new File(kjbFilePath), variables, arguments);
	}

	/**
	 * 通过文件方式执行job
	 * @param kjbFile		job作业文件
	 * @param variables     变量
	 * @param arguments		参数
	 * @throws Exception    转换报错
	 */
	public static Job runJob(File kjbFile, Map<String, String> variables, String[] arguments) throws Exception {
		logger.info("execute kjb, kjbFilePath={}, variables={}, arguments={}", kjbFile.getAbsolutePath(), variables, arguments);
		if (!kjbFile.exists()) {
			throw new FileNotFoundException("Pentaho Kettle Job [" + kjbFile.getAbsolutePath() + "]: File Not Found");
		}
		try {
			// 初始化
			KettleEnvironment.init();
			EnvUtil.environmentInit();
			JobMeta jobMeta = new JobMeta(kjbFile.getAbsolutePath(), null);
			Job job = new Job(null, jobMeta);
			if (variables != null) {
				for (String variableName : variables.keySet()) {
					job.setVariable(variableName, variables.get(variableName));
				}
			}
			job.setArguments(arguments);
			// 添加运行日志监听
			job.setLogLevel(LogLevel.BASIC);
			KettleLoggingEventListener kettleLoggingEventListener = printLog();
			job.start();
			job.waitUntilFinished();
			// 删除运行日志监听
			KettleLogStore.getAppender().removeLoggingEventListener(kettleLoggingEventListener);
			if (job.getErrors() > 0) {
				throw new Exception("执行job发生异常，异常个数[" + job.getErrors() + "]");
			}
			logger.info("Pentaho Kettle Job [{}]: Successful", kjbFile.getAbsolutePath());
			return job;
		} catch (Exception e) {
			throw new Exception("Pentaho Kettle Job [" + kjbFile.getAbsolutePath() + "]: " + e.getMessage(), e);
		}
	}

	private static KettleLoggingEventListener printLog() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		KettleLoggingEventListener kettleLoggingEventListener = kettleLoggingEvent -> {
			// kettleLoggingEvent为kettle运行时输出的日志。
			logger.debug(sdf.format(kettleLoggingEvent.getTimeStamp()) + " [" + kettleLoggingEvent.getLevel().getDescription() + "] " +  kettleLoggingEvent.getMessage());
		};
		KettleLogStore.getAppender().addLoggingEventListener(kettleLoggingEventListener);
		return kettleLoggingEventListener;
	}
}
