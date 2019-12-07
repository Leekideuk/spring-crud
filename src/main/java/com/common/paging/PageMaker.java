package com.common.paging;

public class PageMaker {
	private Criteria criteria;
	private int prevBlockPage;				// 이전 페이지
	private int nextBlockPage;				// 다음 페이지
	private int totalPage;				// 전체 페이지 개수
	private int totalBlock;				// 전체 페이지 블록 개수
	private int curBlock;				// 현재 페이지 블록
	private int blockBegin;				// 현재 페이지 블록의 시작번호
	private int blockEnd;				// 현재 페이지 블록의 끝 번호
	
	public PageMaker(int count, Criteria criteria){
		this.criteria = criteria;
		setTotalPage(count);		// 전체 페이지 개수 계산
		setTotalBlock();	// 전체 페이지 블록 개수 계산
		setBlockRange();	// 전체 페이지 블록의 시작, 끝 번호 계산
	}
	
	public void setBlockRange(){
		curBlock = (criteria.getCurPage()-1) / criteria.getBlockScale() + 1;
		blockBegin = (curBlock-1)*criteria.getBlockScale()+1;
		blockEnd = blockBegin+criteria.getBlockScale()-1;
		// 마지막 블록이 범위를 초과하지 않도록 처리
		if(blockEnd > totalPage) blockEnd = totalPage;
		// 이전 눌렀을 때 이동할 페이지 번호
		//prevBlockPage = (criteria.getCurPage() <= criteria.getBlockScale()) ? 1 : (curBlock-1)*criteria.getBlockScale();
		prevBlockPage = (curBlock == 1) ? 1 : (curBlock-1)*criteria.getBlockScale();
		// 다음 눌렀을 때 이동할 페이지 번호
		//nextBlockPage = (curBlock > totalBlock) ? (curBlock*criteria.getBlockScale()) : (curBlock*criteria.getBlockScale()) +1;
		nextBlockPage = (curBlock*criteria.getBlockScale()) +1;
		// 마지막 페이지가 범위를 초과하지 않도록 처리리
		if(nextBlockPage >= totalPage) nextBlockPage = totalPage;
	}
	
	
	// Getter/Setter
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public int getPrevBlockPage() {
		return prevBlockPage;
	}
	public void setPrevBlocPage(int prevBlockPage) {
		this.prevBlockPage = prevBlockPage;
	}

	public int getNextBlockPage() {
		return nextBlockPage;
	}
	public void setNextBlockPage(int nextBlockPage) {
		this.nextBlockPage = nextBlockPage;
	}

	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int count) {
		this.totalPage = (int) Math.ceil(count*1.0 / criteria.getPageScale());
	}
	
	public int getTotalBlock() {
		return totalBlock;
	}
	public void setTotalBlock() {
		this.totalBlock = (int)Math.ceil((double)totalPage / criteria.getBlockScale());
		
	}
	
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public int getBlockBegin() {
		return blockBegin;
	}
	public void setBlockBegin(int blockBegin) {
		this.blockBegin = blockBegin;
	}

	public int getBlockEnd() {
		return blockEnd;
	}
	public void setBlockEnd(int blockEnd) {
		this.blockEnd = blockEnd;
	}

	@Override
	public String toString() {
		return "PageMaker [criteria=" + criteria + ", prevBlockPage=" + prevBlockPage + ", nextBlockPage="
				+ nextBlockPage + ", totalPage=" + totalPage + ", totalBlock=" + totalBlock + ", curBlock=" + curBlock
				+ ", blockBegin=" + blockBegin + ", blockEnd=" + blockEnd + "]";
	}
}
