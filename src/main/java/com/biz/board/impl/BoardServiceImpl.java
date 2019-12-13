package com.biz.board.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biz.board.BoardService;
import com.biz.board.BoardVO;
import com.common.paging.Criteria;
import com.common.util.FileUtil;
import com.common.vo.FileVO;
import com.common.vo.SearchVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	// 글 등록. 트랜잭션 처리
	@Override
	public Integer insertBoard(BoardVO board, String basePath) {
		// 파일 업로드 처리.
		String path = FileUtil.getUploadPath(basePath);
		List<FileVO> fileList = FileUtil.saveAllFiles(path ,board.getUploadFile());
		try{
			int boardId = boardDAO.insertBoard(board);
			for (FileVO file : fileList){
				file.setParentId(boardId);
				boardDAO.insertBoardFile(file);
			}
			return boardId;
		}catch(Exception e){
			System.out.println("BoardServiceImpl.insertBoard() : " + e);
			// 롤백 시 서버에 저장한 파일 삭제.
			for (FileVO file : fileList){
				FileUtil.deleteFile(file);
			}
			return null;
		}
	}
	
	// 글 목록 조회
	public List<BoardVO> getBoardList(SearchVO search, Criteria criteria) {
		return boardDAO.getBoardList(search, criteria);
	}
	
	// 글 상세 조회
	public Map<String, Object> getBoard(BoardVO vo){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("board", boardDAO.getBoard(vo));
		map.put("boardFileList", boardDAO.getBoardFileList(vo.getBoardId()));
		boardDAO.increaseCnt(vo);
		return map;
	}
	
	// 글 수정 조회
	public Map<String, Object> getUpdateBoard(BoardVO vo){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("board", boardDAO.getBoard(vo));
		map.put("boardFileList", boardDAO.getBoardFileList(vo.getBoardId()));
		return map;
	}
	
	// 글 수정. 트랜잭션 처리
	public void updateBoard(Map<String, Object> map){
		try{
			BoardVO board = new BoardVO();
			board.setBoardId( Integer.parseInt( (String) map.get("boardId") ));
			board.setTitle((String) map.get("title"));
			board.setContent((String) map.get("content"));
			boardDAO.updateBoard(board);
			
			@SuppressWarnings("unchecked")
			List<String> fileIdList = (List<String>) map.get("fileIdList");
			for(String fileId : fileIdList){
				FileVO file = boardDAO.getBoardFile(Integer.parseInt(fileId));
				boardDAO.deleteBoardFile(Integer.parseInt(fileId));
				
				// 서버에 저장된 파일 삭제
				FileUtil.deleteFile(file);
			}
		}catch(Exception e){
			System.out.println("BoardServiceImpl.updateBoard() : " + e);
		}
	}
	
	// 글 삭제
	public void deleteBoard(BoardVO vo){
		List<FileVO> fileList = boardDAO.getBoardFileList(vo.getBoardId());
		boardDAO.deleteBoard(vo);
		for(FileVO file : fileList){
			FileUtil.deleteFile(file);
		}
	}
	
	// 파일 다운로드
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response, FileVO vo) {
		FileVO file = boardDAO.getBoardFile(vo.getFileId());
		// 브라우저 별 파일이름 인코딩 및 응답 헤더 설정
		FileUtil.setResponseHeader(request, response, file);
		return new ResponseEntity<byte[]>(FileUtil.fileToByteArray(file), HttpStatus.OK);
	}
	
	// 검색 후 전체 데이터 수
	public int getSearchTotalCount(SearchVO search){
		return boardDAO.getSearchTotalCount(search);
	}
	
	// 전체 데이터 수
	public int getTotalCount(){
		return boardDAO.getTotalCount();
	}
}
