package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.board.BoardService;
import com.biz.board.impl.BoardDAO;
import com.biz.user.impl.UserDAO;

public class Test {
	static AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
	static BoardService boardService = (BoardService) container.getBean("boardService");
	static BoardDAO boardDAO = (BoardDAO) container.getBean("boardDAO");
	static UserDAO userDAO = (UserDAO) container.getBean("userDAO");
	
	public static void main(String[] args){
		String[] strs = container.getBeanDefinitionNames();
		for(String s : strs){
			System.out.println(s);
		}
		
	
	}
}




