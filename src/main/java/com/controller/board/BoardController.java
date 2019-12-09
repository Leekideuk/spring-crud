package com.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.board.BoardCriteria;
import com.biz.board.BoardService;
import com.biz.board.BoardVO;
import com.common.paging.Criteria;
import com.common.paging.PageMaker;
import com.common.vo.FileVO;
import com.common.vo.SearchVO;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private MessageSource messageSource;
	@Resource(name="basePath")
	String basePath;
	
	// 글 등록
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.GET)
	public String insertBoard(@ModelAttribute BoardVO vo){
		return "board/insertBoard.jsp";
	}
	
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> insertBoard(HttpSession session, @Valid @ModelAttribute BoardVO vo, 
			BindingResult brs) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 로그아웃 상태에서 글 등록 불가.
		if(session.getAttribute("user") == null){
			map.put("location", "main.do");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		
		// 유효성 체크 후 메시지 보내기.
		if(brs.hasErrors()){ 
			List<FieldError> errors = brs.getFieldErrors();
		    for (FieldError error : errors ) {
		        map.put(error.getField(), messageSource.getMessage(error, null));
		    }
		    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
		}
		
		Integer boardId = boardService.insertBoard(vo, basePath);
		map.put("location", "getBoard.do?boardId="+boardId);
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 검색 조건 목록 설정
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		conditionMap.put("작성자", "USERID");
		return conditionMap;
	}
	
	// 글 목록 조회
	@RequestMapping(value="/getBoardList.do")
	public String getBoardList(SearchVO search, @RequestParam(defaultValue="1") int curPage, Model model) {
		if(search.getSearchCondition() == null) search.setSearchCondition("TITLE");
		if(search.getSearchKeyword() == null) search.setSearchKeyword("");

		int count = boardService.getSearchTotalCount(search);		
		Criteria criteria = new BoardCriteria(curPage, 5, 5);
		PageMaker pageMaker = new PageMaker(count, criteria);
		
		model.addAttribute("boardList", boardService.getBoardList(search, criteria));
		model.addAttribute("searchCondition", search.getSearchCondition());	// 검색 후 <option>의 selected 사용.
		model.addAttribute("searchKeyword", search.getSearchKeyword());	// 검색 후	serchKeyword 유지.
		model.addAttribute("paging", pageMaker);
		return "board/getBoardList.jsp";	
	}
	
	
	// 글 상세 조회
	@RequestMapping(value="/getBoard.do")
	public String getBoard(BoardVO vo, Model model){
		model.addAttribute("boardMap", boardService.getBoard(vo));
		return "board/getBoard.jsp";
	}
	
	// 글 수정
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.GET)
	public String updateBoard(BoardVO vo, Model model){
		model.addAttribute("boardMap", boardService.getUpdateBoard(vo));
		return "board/updateBoard.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="updateBoard.do", method=RequestMethod.POST)
	public ResponseEntity<String> updateBoard(@RequestBody Map<String, Object> map){
		boardService.updateBoard(map);
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	// 글 삭제
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo){
		boardService.deleteBoard(vo);
		return "redirect:getBoardList.do";
	}
	
	// 파일 다운로드
	@RequestMapping(value = "fileDownload.do")
	@ResponseBody
	public  ResponseEntity<byte[]>  fileDownload(HttpServletRequest request,HttpServletResponse response,FileVO vo) {
	   	return boardService.downloadFile(request, response, vo);
	}
	
}
