package com.biz.comment.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biz.comment.CommentVO;
import com.common.paging.Criteria;

@Repository("commentDAO")
public class CommentDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 댓글 리스트 조회
	public List<CommentVO> getCommentList(int boardId, Criteria criteria){
		System.out.println("===> CommentDAO.getCommentList()");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardId", boardId);
		map.put("criteria", criteria);
		return mybatis.selectList("CommentDAO.getCommentList", map);
	}
	
	// 해당 게시물의 전체 댓글 수 조회
	public int getTotalCountInBoard(int boardId){
		System.out.println("===> CommentDAO.getTotalCountInBoard()");
		return mybatis.selectOne("CommentDAO.getTotalCountInBoard", boardId);
	}
	
	// 댓글 입력
	public void insertComment(CommentVO vo){
		System.out.println("===> CommentDAO.insertComment()");
		mybatis.insert("CommentDAO.insertComment", vo);
	}
	
	// 전체 댓글 중 최대 order 조회
	public int getMaxOrder(CommentVO vo){
		System.out.println("===> CommentDAO.getMaxOrder()");
		return mybatis.selectOne("CommentDAO.getMaxOrder", vo);
	}
	
	// vo를 기준으로 서브트리를 형성한 댓글 중 최대 order 조회. vo 포함.
	public int getMaxOrderOfSubtree(CommentVO vo){
		System.out.println("===> CommentDAO.getMaxOrderOfSubtreet()");
		return mybatis.selectOne("CommentDAO.getMaxOrderOfSubtree", vo);
	}
	
	// 부모 댓글 조회
	public CommentVO getComment(int commentId){
		System.out.println("===> CommentDAO.getComment()");
		return mybatis.selectOne("CommentDAO.getComment", commentId);
	}
	
	// 해당 order 보다 큰 order +=1
	public void updateOrder(int boardId, int order){
		System.out.println("===> CommentDAO.updateOrder()");
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boardId", boardId);
		map.put("order", order);
		mybatis.update("CommentDAO.updateOrder", map);
	}
	
	// 댓글 삭제 처리, deleteFlag = true
	public void setDeleteFlagTrue(CommentVO vo){
		System.out.println("===> CommentDAO.setDeleteFlagTrue()");
		mybatis.update("CommentDAO.setDeleteFlagTrue", vo);
	}
	
	
	
	
	
	
	
}
