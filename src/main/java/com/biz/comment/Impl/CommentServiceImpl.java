package com.biz.comment.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.comment.CommentService;
import com.biz.comment.CommentVO;
import com.common.paging.Criteria;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDAO commentDAO;
	
	// 댓글 리스트 조회
	public List<CommentVO> getCommentList(int boardId, Criteria criteria){
		List<CommentVO> commentList = commentDAO.getCommentList(boardId, criteria);
		for(CommentVO comment : commentList){
			if(comment.isDeleteFlag() == true){
				comment.setUserId("삭제");
				comment.setContent("삭제된 댓글 입니다.");
			}
		}
		return commentList;
	}
	
	// 해당 게시물의 전체 댓글 수 조회
	public int getTotalCountInBoard(int boardId){
		return commentDAO.getTotalCountInBoard(boardId);
	}
	
	// 댓글 입력. order 반환. 트랜잭션 처리.
	public int insertComment(CommentVO vo){
		try{
			// 1. 게시글에 댓글 단 경우
			if(0 == vo.getParentId()){	
				vo.setOrder(commentDAO.getMaxOrder(vo)+1);
				
			// 2. 댓글에 댓글 단 경우
			}else{
				CommentVO parent = commentDAO.getComment(vo.getParentId());
				vo.setDepth(parent.getDepth()+1);
				int MaxOrderOfSubtree = commentDAO.getMaxOrderOfSubtree(parent);
				commentDAO.updateOrder(vo.getBoardId(), MaxOrderOfSubtree);
				vo.setOrder(MaxOrderOfSubtree +1);
			}
			commentDAO.insertComment(vo);
			
		}catch(Exception e){
			System.out.println("CommentServiceImpl.insertComment() : "+e);
		}
		return vo.getOrder();
	}
	
	// 댓글 삭제 처리, deleteFlag = true
	public int setDeleteFlagTrue(CommentVO vo){
		commentDAO.setDeleteFlagTrue(vo);
		return commentDAO.getComment(vo.getCommentId()).getOrder();
	}
}
