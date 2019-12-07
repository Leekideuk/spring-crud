package com.common.vo;

public class FileVO {
	private int fileId;		//board_file_id
	private int parentId;	// board_id
	private String fileName;	// filename
	private String realName;	// realname
	private long fileSize;	// filesize
	private String path;	// path
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	 public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return "FileVO [fileId=" + fileId + ", parentId=" + parentId + ", fileName=" + fileName + ", realName="
				+ realName + ", fileSize=" + fileSize + ", path=" + path + "]";
	}
	public String sizeToString() {
	       Integer unit = 1024;
	       if (fileSize < unit){
	           return String.format("(%d B)", fileSize);
	       }
	       int exp = (int) (Math.log(fileSize) / Math.log(unit));
	       return String.format("(%.0f %s)", fileSize / Math.pow(unit, exp), "KMGTPE".charAt(exp-1));
	}

}
