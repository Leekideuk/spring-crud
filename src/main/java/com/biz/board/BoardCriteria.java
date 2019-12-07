package com.biz.board;

import com.common.paging.Criteria;

public class BoardCriteria extends Criteria{

	public BoardCriteria(int curPage, int pageScale, int blockScale) {
		setCurPage(curPage);
		setPageScale(pageScale);
		setBlockScale(blockScale);
	}
	
	
}
