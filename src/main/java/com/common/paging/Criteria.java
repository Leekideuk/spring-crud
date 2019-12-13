package com.common.paging;

public class Criteria {
	private int curPage;	// 현재 페이지
	private int pageScale;		// 페이지 당 게시물 수
	private int blockScale;		// 화면 당 페이지 수
	
	public Criteria() {}
	
	// Getter/Setter
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		
		if (curPage <= 0) {
			this.curPage = 1;
			return;
		}
		this.curPage = curPage;
	}
	public void setPageScale(int pageScale) {
		if (pageScale <= 0 || pageScale > 100) {
			this.pageScale = 10;
			return;
		}
		this.pageScale = pageScale;
	}
	public int getBlockScale() {
		return blockScale;
	}
	public void setBlockScale(int blockScale) {
		if (blockScale <= 0 || blockScale > 10) {
			this.blockScale = 10;
			return;
		}
		this.blockScale = blockScale;
	}
	
	// method for MyBatis SQL Mapper
	public int getPageScale() {
		return pageScale;
	}
	// method for MyBatis SQL Mapper
	  public int getPageStart() {
	    return (this.curPage - 1) * pageScale;
	  }

	@Override
	public String toString() {
		return "Criteria [curPage=" + curPage + ", pageScale=" + pageScale + ", blockScale=" + blockScale + "]";
	}
	
	
}
