package com.biz;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.board.BoardService;
import com.biz.board.BoardVO;
import com.biz.board.impl.BoardDAO;
import com.common.vo.FileVO;

public class Test {
	static AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
	static BoardService boardService = (BoardService) container.getBean("boardService");
	static BoardDAO boardDAO = (BoardDAO) container.getBean("boardDAO");
	
	public static void main(String[] args){
		BoardVO boardVO = new BoardVO();
		boardVO.setUserId("bbb");
		List<FileVO> fileList = boardDAO.getBoardFileListByUserId(boardVO.getUserId());
		for(FileVO vo : fileList){
			System.out.println(vo);
		}
		
		
	}
	
	
}





