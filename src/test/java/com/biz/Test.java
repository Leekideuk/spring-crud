package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.user.UserService;
import com.biz.user.impl.UserDAO;

public class Test {
	public static void main(String[] args){
		
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		//BoardService boardService = (BoardService) container.getBean("boardService");
		//BoardDAO boardDAO = (BoardDAO) container.getBean("boardDAO");
		
		//CommentDAO commentDAO = (CommentDAO) container.getBean("commentDAO");
		//CommentService commentService = (CommentService) container.getBean("commentService");
		
	//	UserDAO userDAO = (UserDAO) container.getBean("userDAO");
		//System.out.println(userDAO.existEmail("a@aa"));
		//UserService userService = (UserService) container.getBean("userService");
		//System.out.println(userService.existEmail("a@aa"));
		
		
		/*AbstractApplicationContext ctx = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/dispatcher-servlet.xml");
		System.out.println(ctx);
		String[] strArr = ctx.getBeanDefinitionNames();
		for(String str : strArr){
			System.out.println(str);
		}*/
		
		
	}
	
	
}





