package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.biz.user.UserVO;
import com.biz.user.impl.UserDAO;
import com.common.mail.MailHandler;

public class Test {
	public static void main(String[] args){
		
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		MailHandler mh = (MailHandler) container.getBean("com.common.mail.MailHandler#0");
		
		UserDAO dao = (UserDAO) container.getBean("userDAO");
		UserVO vo = new UserVO();
		vo.setEmail("5260015@naver.coms");
		System.out.println(dao.findUserInfo(vo));
		
	}
	
	
}





