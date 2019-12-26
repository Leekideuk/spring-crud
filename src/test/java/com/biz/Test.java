package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.user.UserVO;
import com.biz.user.impl.UserDAO;
import com.common.mail.MailHandler;

public class Test {
	static AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
	
	public static void main(String[] args){
		
		
		MailHandler mh = (MailHandler) container.getBean("com.common.mail.MailHandler#0");
		
		UserDAO dao = (UserDAO) container.getBean("userDAO");
		UserVO vo = new UserVO();
		vo.setUserId("5260015");
		vo.setPassword("1234");
		dao.updateUserPassword(vo);
		
	}
	
	
}





