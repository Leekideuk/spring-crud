package com.biz.board;

public class BoardFileVO {
	private int boardFileId;
	private int boardId;	// 게시글 아이디
	private String fileName;
	private String realName; // filename+날짜,시간
	private long fileSize;
	public int getBoardFileId() {
		return boardFileId;
	}
	public void setBoardFileId(int boardFileId) {
		this.boardFileId = boardFileId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
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
	@Override
	public String toString() {
		return "BoardFileVO [boardFileId=" + boardFileId + ", boardId=" + boardId + ", fileName=" + fileName
				+ ", realName=" + realName + ", fileSize=" + fileSize + "]";
	}
	
	
}
