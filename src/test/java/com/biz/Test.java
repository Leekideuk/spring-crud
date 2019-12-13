package com.biz;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.comment.CommentService;
import com.biz.comment.CommentVO;
import com.biz.comment.Impl.CommentDAO;

public class Test {
	public static void main(String[] args){
		
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		//BoardService boardService = (BoardService) container.getBean("boardService");
		//BoardDAO boardDAO = (BoardDAO) container.getBean("boardDAO");
		//UserDAO userDAO = (UserDAO) container.getBean("userDAO");
		
		CommentDAO commentDAO = (CommentDAO) container.getBean("commentDAO");
		CommentService commentService = (CommentService) container.getBean("commentService");
		
		System.out.println(commentService.getTotalCountInBoard(279));
		
		
		
	}
	
	
}





