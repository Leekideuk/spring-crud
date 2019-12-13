package com.biz.comment;

import java.util.Date;

public class CommentVO {
	private int	commentId;		// comment_id
	private int boardId;		// board_id
	private String userId;		// user_id
	private String content;		// content
	private Date regDate;		// regdate
	private int parentId;		// parent_id
	private int order;			// order_c
	private int depth;			// depth
	private boolean deleteFlag;	// delete_flag
	
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Override
	public String toString() {
		return "CommentVO [commentId=" + commentId + ", boardId=" + boardId + ", userId=" + userId + ", content="
				+ content + ", regDate=" + regDate + ", parentId=" + parentId + ", order=" + order + ", depth=" + depth
				+ ", deleteFlag=" + deleteFlag + "]";
	}
	
	
}
