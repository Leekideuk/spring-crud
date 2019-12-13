package com.controller.comment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.comment.CommentService;
import com.biz.comment.CommentVO;
import com.biz.user.UserVO;
import com.common.paging.PageMaker;

@Controller
public class CommentController {
	private CommentService commentService;
	private CommentCriteria commentCriteria;
	
	@Autowired
	public CommentController(CommentService commentService, CommentCriteria commentCriteria) {
		this.commentService = commentService;
		this.commentCriteria = commentCriteria;
	}
	
	// 댓글 리스트 조회
	@RequestMapping(value="getCommentList.do")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getCommentList(int curCommentPage, int boardId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = commentService.getTotalCountInBoard(boardId);
		commentCriteria.setCurPage(curCommentPage);
		PageMaker pageMaker = new PageMaker(count, commentCriteria);
		
		map.put("commentPaging", pageMaker);
		map.put("commentList", commentService.getCommentList(boardId, commentCriteria));
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 댓글 입력
	@RequestMapping(value="insertComment.do")
	@ResponseBody
	public ResponseEntity<Integer> insertComment(@RequestBody CommentVO vo, HttpSession session){
		UserVO user = (UserVO) session.getAttribute("user");
		if(user == null){ return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED); }
		
		vo.setUserId(user.getUserId());
		int page = (int) Math.ceil(commentService.insertComment(vo) / (double)commentCriteria.getPageScale());
		return new ResponseEntity<Integer>(page, HttpStatus.OK);
	}
	
	// 댓글 삭제 처리, deleteFlag = true
	@RequestMapping(value="setDeleteFlagTrue.do")
	@ResponseBody
	public ResponseEntity<Integer> setDeleteFlagTrue(@RequestBody CommentVO vo){
		int page = (int) Math.ceil(commentService.setDeleteFlagTrue(vo) / (double)commentCriteria.getPageScale());
		return new ResponseEntity<Integer>(page, HttpStatus.OK);
	}
}
