package com.xiongyayun.athena.util;

/**
 * UploadFile
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/1/9
 */
public class UploadFile {
	private String fileName;
	private String mimeType;
	//	private String fileType;
	private Long fileSize;
	//	private String fileSuffix;
	private String filePath;

	public String getFileName() {
		return fileName;
	}

	public UploadFile setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getMimeType() {
		return mimeType;
	}

	public UploadFile setMimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

//	public String getFileType() {
//		return fileType;
//	}
//
//	public UploadFile setFileType(String fileType) {
//		this.fileType = fileType;
//		return this;
//	}

	public Long getFileSize() {
		return fileSize;
	}

	public UploadFile setFileSize(Long fileSize) {
		this.fileSize = fileSize;
		return this;
	}

//	public String getFileSuffix() {
//		return fileSuffix;
//	}
//
//	public UploadFile setFileSuffix(String fileSuffix) {
//		this.fileSuffix = fileSuffix;
//		return this;
//	}

	public String getFilePath() {
		return filePath;
	}

	public UploadFile setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
}
