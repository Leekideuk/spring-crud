package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.board.BoardService;
import com.biz.board.impl.BoardDAO;
import com.biz.user.impl.UserDAO;

public class Test {
	public static void main(String[] args){
		
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		BoardService boardService = (BoardService) container.getBean("boardService");
		System.out.println(boardService.getTotalCount());
		BoardDAO boardDAO = (BoardDAO) container.getBean("boardDAO");
		System.out.println(boardDAO.getTotalCount());
		UserDAO userDAO = (UserDAO) container.getBean("userDAO");
		System.out.println(userDAO.existUserId("aaa"));
		System.out.println(userDAO.existUserId("aaaa"));
	}
	
	
}





