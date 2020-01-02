package com.controller.board;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.board.BoardService;
import com.biz.board.BoardVO;
import com.common.paging.PageMaker;
import com.common.vo.FileVO;
import com.common.vo.SearchVO;
import com.exception.validation.UpdateBoardValidator;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Resource(name="basePath")
	String basePath;
	@Autowired
	private BoardCriteria boardCriteria;
	
	// 글 등록
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.GET)
	public String insertBoard(@ModelAttribute BoardVO vo){
		return "board/insertBoard.jsp";
	}
	
	@RequestMapping(value="/insertBoard.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> insertBoard(@Valid @ModelAttribute BoardVO vo, 
			BindingResult brs) throws MethodArgumentNotValidException{
		if(brs.hasErrors()){ throw new MethodArgumentNotValidException(null, brs); }
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int boardId = boardService.insertBoard(vo, basePath);
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
	public String getBoardList(SearchVO search, @RequestParam(defaultValue="1") int curPage, Model model, ServletRequest request) {
		/*// WebApplicationContext TEST
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();	// ContextLoaderListener 이용해서 생성한 WebApplicationContext
		WebApplicationContext ctx2 = RequestContextUtils.getWebApplicationContext(request);	// DispatcherServlet 이용해서 생성한 WebApplicationContext
  		// ctx.hashCode() == ctx2.getParent().hashCode() == true
		String[] strs = ctx2.getBeanDefinitionNames();
		for(String str : strs) System.out.println(str);*/
		
		if(search.getSearchCondition() == null) search.setSearchCondition("TITLE");
		if(search.getSearchKeyword() == null) search.setSearchKeyword("");

		int count = boardService.getSearchTotalCount(search);
		boardCriteria.setCurPage(curPage);
		PageMaker pageMaker = new PageMaker(count, boardCriteria);
		
		model.addAttribute("boardList", boardService.getBoardList(search, boardCriteria));
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
	public String updateBoard(BoardVO vo, Model model, Principal principal){
		// 게시글 작성 아이디와 로그인 아이디가 다른 경우
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@ security 처리 가능하면 security로 처리 or 예외처리.
		if(!principal.getName().equals(vo.getUserId())){ 
			return "redirect:getBoard.do?boardId="+vo.getBoardId(); 
		}
		model.addAttribute("boardMap", boardService.getUpdateBoard(vo));
		return "board/updateBoard.jsp";
	}
	
	@ResponseBody
	@RequestMapping(value="updateBoard.do", method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> updateBoard(@RequestBody Map<String, Object> map) throws MethodArgumentNotValidException{
		// Map 사용시 BindingResult를 파라미터로 받아서 사용하면 org.springframework.beans.NotReadablePropertyException 발생함. 
		BindingResult brs = new MapBindingResult(map, "map");
		new UpdateBoardValidator().validate(map, brs);
		if(brs.hasErrors()){ throw new MethodArgumentNotValidException(null, brs); }
		
		boardService.updateBoard(map);	
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("location", "getBoard.do?boardId="+map.get("boardId"));
		return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
	}
	
	// 글 삭제
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo, Principal principal){
		// 게시글 작성 아이디와 로그인 아이디가 다른 경우
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@ security 처리 가능하면 security로 처리 or 예외처리.
		if(!principal.getName().equals(vo.getUserId())){ 
			return "redirect:getBoard.do?boardId="+vo.getBoardId(); 
		}
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
