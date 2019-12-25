package com.biz;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.common.mail.MailHandler;

public class Test {
	public static void main(String[] args){
		
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		MailHandler mh = (MailHandler) container.getBean("com.common.mail.MailHandler#0");
		
	}
	
	
}





