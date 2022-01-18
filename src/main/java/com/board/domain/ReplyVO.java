package com.board.domain;

public class ReplyVO {
	private int rno;
	private int no;
	private String rpassword;
	private String content;
	private String replyWirter;
	private int bid;
	private int ordered;
	
	

	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getOrdered() {
		return ordered;
	}
	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}
	public String getReplyWirter() {
		return replyWirter;
	}
	public void setReplyWirter(String replyWirter) {
		this.replyWirter = replyWirter;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public String getRpassword() {
		return rpassword;
	}
	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ReplyVO [rno=" + rno + ", no=" + no + ", rpassword=" + rpassword + ", content=" + content
				+ ", replyWirter=" + replyWirter + ", bid=" + bid + ", ordered=" + ordered + "]";
	}

	

	

	
	

	
	
}