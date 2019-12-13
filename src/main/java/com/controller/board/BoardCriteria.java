package com.controller.board;

import org.springframework.stereotype.Component;

import com.common.paging.Criteria;

@Component
public class BoardCriteria extends Criteria{

	public BoardCriteria() {
		setPageScale(5);
		setBlockScale(5);
	}
	
	
}
