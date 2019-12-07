package com.biz.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.common.paging.Criteria;
import com.common.vo.FileVO;
import com.common.vo.SearchVO;

public interface BoardService {
	// 글 등록
	void insertBoard(BoardVO board, String basePath);
	
	// 글 목록 조회
	List<BoardVO> getBoardList(SearchVO search, Criteria criteria);
	
	// 글 상세 조회
	Map<String, Object> getBoard(BoardVO vo);
	
	// 글 수정 조회
	Map<String, Object> getUpdateBoard(BoardVO vo);
	
	// 글 수정
	void updateBoard(Map<String, Object> map);
	
	// 글 삭제
	void deleteBoard(BoardVO vo);
	
	// 파일 다운로드
	ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response,FileVO vo);
	
	// 전체 데이터 수
	int getTotalCount();
	
	// 검색 후 전체 데이터 수
	int getSearchTotalCount(SearchVO search);
}
