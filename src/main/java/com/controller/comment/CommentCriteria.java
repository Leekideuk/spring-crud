package com.controller.comment;

import org.springframework.stereotype.Component;

import com.common.paging.Criteria;

@Component
public class CommentCriteria extends Criteria {
	
	public CommentCriteria(){
		setPageScale(10);
		setBlockScale(5);
	}
}
