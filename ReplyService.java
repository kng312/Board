package com.board.service;

import java.util.List;

import com.board.domain.ReplyVO;

public interface ReplyService {
	
	// 댓글 조회
	public List<ReplyVO> replyList(int no) throws Exception;

	// 댓글 조회
	public void replyWrite(ReplyVO vo) throws Exception;

	// 댓글 수정
	public void modify(ReplyVO vo) throws Exception;

	// 댓글 삭제
	public void replyDelete(ReplyVO vo) throws Exception;
	
	//특정 댓글 조회
	public ReplyVO replySelect (int rno) throws Exception;
	

}
