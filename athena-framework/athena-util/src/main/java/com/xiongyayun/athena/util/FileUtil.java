package com.xiongyayun.athena.util;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

/**
 * FileUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/10/23
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(PentahoKettleUtil.class);
	private static final Tika TIKA = new Tika();

	public static String getFileSize(Long fileSize) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString;
		if (fileSize < 1024L) {
			fileSizeString = df.format(fileSize.longValue()) + "B";
		} else if (fileSize < 1048576L) {
			fileSizeString = df.format(fileSize / 1024.0D) + "K";
		} else if (fileSize < 1073741824L) {
			fileSizeString = df.format(fileSize / 1048576.0D) + "M";
		} else {
			fileSizeString = df.format(fileSize / 1073741824.0D) + "G";
		}
		return fileSizeString;
	}

	public static String getFileMimeType(File file) {
		try {
			return TIKA.detect(file);
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
	public static void preview(File file, HttpServletResponse response) throws FileNotFoundException {
		previewOrDownload(file, response, false);
	}

	/**
	 * 文件下载
	 * @param file			文件
	 * @param response		HttpServletResponse对象
	 */
	public static void download(File file, HttpServletResponse response) throws FileNotFoundException {
		previewOrDownload(file, response, true);
	}

	/**
	 * 文件上传
	 * @param multipartFile		待上传文件
	 * @param destFilePath		目标上传目录
	 * @param destFileName		目标上传文件名，为空时取上传的文件名
	 * @return {@link UploadFile} 上传的文件名
	 * @throws Exception		上传异常
	 */
	public static UploadFile upload(MultipartFile multipartFile, String destFilePath, String destFileName) throws Exception {
		UploadFile uf = new UploadFile();
		uf.setFileSize(multipartFile.getSize());
		if (ObjectUtils.isEmpty(multipartFile)) {
			throw new FileNotFoundException("未选择上传的文件");
		}
		String detectedMediaType = TIKA.detect(multipartFile.getInputStream());
		uf.setMimeType(detectedMediaType);
		System.out.println(detectedMediaType);
		File dirs = new File(destFilePath);
		if (!dirs.exists()) {
			logger.info("目录[{}]创建{}", dirs.getAbsolutePath(), dirs.mkdirs() ? "成功" : "失败");
		}
		Path path;
		if (StringUtils.hasLength(destFileName)) {
			uf.setFileName(destFileName);
			path = Paths.get(dirs.getAbsolutePath() + File.separator + destFileName);
		} else {
			String fileName = multipartFile.getOriginalFilename();
			if (fileName == null) {
				throw new Exception("上传的文件名为空");
			}
			// 判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息
			int unixSep = fileName.lastIndexOf('/');
			int winSep = fileName.lastIndexOf('\\');
			int pos = (Math.max(winSep, unixSep));
			if (pos != -1)  {
				fileName = fileName.substring(pos + 1);
			}
			uf.setFileName(fileName);
			path = Paths.get(dirs.getAbsolutePath() + File.separator + fileName);
		}
		try {
			logger.debug("文件准备上传[{}]", path.toString());
			multipartFile.transferTo(path);
			uf.setFilePath(path.toString());
			logger.info("文件[{}]上传成功", uf.getFileName());
			return uf;
		} catch (Exception e) {
			throw new Exception("文件[" + uf.getFileName() + "]上传失败", e);
		}
	}

	/**
	 * 文件预览或下载
	 * @param file			文件
	 * @param response		HttpServletResponse对象
	 * @param download		true: 下载  false：预览
	 */
	private static void previewOrDownload(File file, HttpServletResponse response, Boolean download) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException("file does not exist: " + file.getAbsolutePath());
		}
		response.setContentType(getFileMimeType(file));
		// 创建数据缓冲区
		byte[] buffer = new byte[1024];
		// try的括号中所有实现Closeable的类声明都可以写在里面，最常见的是流操作，socket操作等。括号中可以写多行语句，会自动关闭括号中的资源。
		try (FileInputStream fis = new FileInputStream(file);
			 BufferedInputStream bis = new BufferedInputStream(fis)) {
			if (download != null && download) {
				// 下载
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			} else {
				// 预览
				response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
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
