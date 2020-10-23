package com.xiongyayun.athena.util;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * FileUtil
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/10/23
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(PentahoKettleUtil.class);
	private static final Tika tika = new Tika();

	public static String getFileMimeType(File file) {
		try {
			return tika.detect(file);
		} catch (IOException e) {
			logger.warn("get file MimeType error: " + e.getMessage(), e);
			return "application/octet-stream";
		}
	}

	/**
	 * 文件预览
	 * @param file			文件
	 * @param response		HttpServletResponse对象
	 */
	public static void preview(File file, HttpServletResponse response) {
		previewOrDownload(file, response, false);
	}

	/**
	 * 文件下载
	 * @param file			文件
	 * @param response		HttpServletResponse对象
	 */
	public static void download(File file, HttpServletResponse response) {
		previewOrDownload(file, response, true);
	}

	/**
	 * 文件预览或下载
	 * @param file			文件
	 * @param response		HttpServletResponse对象
	 * @param download		true: 下载  false：预览
	 */
	private static void previewOrDownload(File file, HttpServletResponse response, Boolean download) {
		if (!file.exists()) {
			throw new RuntimeException("file does not exist: " + file.getAbsolutePath());
		}
		response.setContentType(getFileMimeType(file));
		// 创建数据缓冲区
		byte[] buffer = new byte[1024];
		// try的括号中所有实现Closeable的类声明都可以写在里面，最常见的是流操作，socket操作等。括号中可以写多行语句，会自动关闭括号中的资源。
		try (FileInputStream fis = new FileInputStream(file);
			 BufferedInputStream bis = new BufferedInputStream(fis)) {
			if (download != null && download) {
				// 下载
				response.addHeader("Content-Disposition", "attachment; filename=" + new StringBuffer().append(URLEncoder.encode(file.getName(), "UTF-8")));
			} else {
				// 预览
				response.addHeader("Content-Disposition", "inline; filename=" + new StringBuffer().append(URLEncoder.encode(file.getName(), "UTF-8")));
			}
			OutputStream os = response.getOutputStream();
			int len;
			while ((len = bis.read(buffer)) > 0) {
				os.write(buffer, 0, len);
				os.flush();
			}
		} catch (Exception e) {
			logger.error("file error: " + e.getMessage(), e);
		}
	}
}
