package com.board.domain;

import java.sql.Time;
import java.util.Date;

public class BoardVO {
	
	private int rownum;
	private int no;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private String password;
	private int bid;
	private int ordered;
	private int layer;
	
	private int reply_count;
	private int file_cnt;
	private int replyDeleteC;
	
	private String Email;
	
	private String searchType = "";
	private String keyword = "";
	
	
	
	
	public void setSearchType(String searchType) {
		 this.searchType = searchType;  
		}

	public String getSearchType() {
		 return searchType;
		} 

	public void setKeyword(String keyword) {
		 this.keyword = keyword;  
		} 

	public String getKeyword() {
		 return keyword;
		}
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public int getFile_cnt() {
		return file_cnt;
	}
	public void setFile_cnt(int file_cnt) {
		this.file_cnt = file_cnt;
	}
	public int getReplyDeleteC() {
		return replyDeleteC;
	}
	public void setReplyDeleteC(int replyDeleteC) {
		this.replyDeleteC = replyDeleteC;
	}
	public int getReply_count() {
		return reply_count;
	}
	public void setReply_count(int reply_count) {
		this.reply_count = reply_count;
	}
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
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BoardVO [rownum=" + rownum + ", no=" + no + ", title=" + title + ", content=" + content + ", writer="
				+ writer + ", regDate=" + regDate + ", password=" + password + ", bid=" + bid + ", ordered=" + ordered
				+ ", layer=" + layer + ", reply_count=" + reply_count + ", file_cnt=" + file_cnt + ", replyDeleteC="
				+ replyDeleteC + ", Email=" + Email + ", searchType=" + searchType + ", keyword=" + keyword + "]";
	}

	

	
	
	

	

	
	


	
	
	
	
	

}
