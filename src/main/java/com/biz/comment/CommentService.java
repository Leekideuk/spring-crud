package com.biz.comment;

import java.util.List;

import com.common.paging.Criteria;

public interface CommentService {
	// 댓글 리스트 조회
	List<CommentVO> getCommentList(int BoardId, Criteria criteria);
	
	// 해당 게시물의 전체 댓글 수 조회
	int getTotalCountInBoard(int boardId);
	
	// 댓글 입력. order 반환.
	int insertComment(CommentVO vo);
	
	// 댓글 삭제 처리, deleteFlag = true. order 반환.
	int setDeleteFlagTrue(CommentVO vo);
}
