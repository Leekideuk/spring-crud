package com.biz.board.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biz.board.BoardVO;
import com.common.paging.Criteria;
import com.common.vo.FileVO;
import com.common.vo.SearchVO;

@Repository("boardDAO")
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 새 글 등록. 파일 등록하기 위해 최근에 등록한 boardId 반환.
	public int insertBoard(BoardVO vo){
		System.out.println("===>BoardDAO.insertBoard()");
		mybatis.insert("BoardDAO.insertBoard", vo);
		return vo.getBoardId();
	}
	
	// 파일 등록
	public void insertBoardFile(FileVO vo){
		System.out.println("===>BoardDAO.insertBoardFile()");
		mybatis.insert("BoardDAO.insertBoardFile", vo);
	}
	
	// 글 목록 조회
	public List<BoardVO> getBoardList(SearchVO search, Criteria criteria){
		System.out.println("===>BoardDAO.getBoardList()");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchCondition", search.getSearchCondition());
		map.put("searchKeyword", search.getSearchKeyword());
		map.put("criteria", criteria);
		return mybatis.selectList("BoardDAO.getBoardList", map);
	}
	
	// 글 상세 조회
	public BoardVO getBoard(BoardVO vo){
		System.out.println("===>BoardDAO.getBoard()");
		return mybatis.selectOne("BoardDAO.getBoard", vo.getBoardId());
	}
	
	// 파일 리스트 조회
	public List<FileVO> getBoardFileList(int boardId){
		System.out.println("===>BoardDAO.getBoardFileList()");
		return mybatis.selectList("BoardDAO.getBoardFileList", boardId); 
	}
	
	// 조회수 증가
	public void increaseCnt(BoardVO vo){
		System.out.println("===>BoardDAO.increaseCnt()");
		mybatis.update("BoardDAO.increaseCnt", vo.getBoardId());
	}
	
	// 글 삭제
	public void deleteBoard(BoardVO vo){
		System.out.println("===>BoardDAO.deleteBoard()");
		mybatis.delete("BoardDAO.deleteBoard", vo.getBoardId());
	}
	
	// 파일 조회
	public FileVO getBoardFile(int fileId){
		System.out.println("===>BoardDAO.getFile()");
		return mybatis.selectOne("BoardDAO.getBoardFile", fileId);
	}
	
	// 글 수정
	public void updateBoard(BoardVO vo){
		System.out.println("===>BoardDAO.updateBoard()");
		mybatis.update("BoardDAO.updateBoard", vo);
	}
	
	// 첨부 파일 삭제
	public void deleteBoardFile(int fileId){
		System.out.println("===>BoardDAO.deleteBoardFile()");
		mybatis.delete("BoardDAO.deleteBoardFile", fileId);
	}
	
	// 전체 데이터 수
	public int getTotalCount(){
		System.out.println("===>getTotalCount()");
		return mybatis.selectOne("BoardDAO.getTotalCount");
	}
	
	// 검색 후 전체 데이터 수
	public int getSearchTotalCount(SearchVO search){
		return mybatis.selectOne("BoardDAO.getSearchTotalCount", search);
	}
	
	
	
}
