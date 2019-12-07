package com.biz.board;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
	private int boardId;
	private String userId;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private Date regDate;
	private int cnt;
	private List<MultipartFile> uploadFile;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public List<MultipartFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<MultipartFile> uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	@Override
	public String toString() {
		return "BoardVO [boardId=" + boardId + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", regDate=" + regDate + ", cnt=" + cnt + ", uploadFile=" + uploadFile + "]";
	}
	
	
	
}
