package com.board.domain;

public class Criteria {
	
	//현재 페이지
	private int num;
	//화면에 표시할 게시물 갯수
	private int postNum;
	//게시글의 시작 행 번호
	private int displayPost;
	
	public Criteria () {
		this.num = 1;
		this.postNum = 10;
	}
	
	public Criteria (int num, int postNum) {
		this.num = num;
		this.postNum = postNum;
		this.displayPost = (num - 1) * postNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.displayPost = (num - 1) * postNum;
		this.num = num;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.displayPost = (this.num - 1) * postNum;
		this.postNum = postNum;
	}

	public int getDisplayPost() {
		return displayPost;
	}

	public void setDisplayPost(int displayPost) {
		this.displayPost = displayPost;
	}

	@Override
	public String toString() {
		return "Criteria [num=" + num + ", postNum=" + postNum + ", displayPost=" + displayPost + "]";
	}
	

}
