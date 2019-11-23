package com.biz.comment;

import java.util.Date;

public class CommentVO {
	private int	commentId;
	private int boardId;
	private String userId;
	private String contnet;
	private Date regDate;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
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
	public String getContnet() {
		return contnet;
	}
	public void setContnet(String contnet) {
		this.contnet = contnet;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "CommentVO [commentId=" + commentId + ", boardId=" + boardId + ", userId=" + userId + ", contnet="
				+ contnet + "]";
	}
	
	
}
