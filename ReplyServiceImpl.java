package com.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.ReplyDAO;
import com.board.domain.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO dao;

	// 댓글 조회
	@Override
	public List<ReplyVO> replyList(int no) throws Exception {
		return dao.replyList(no);
	}

	@Override
	public void replyWrite(ReplyVO vo) throws Exception {
		dao.replyWrite(vo);
	}

	@Override
	public void modify(ReplyVO vo) throws Exception {
		dao.modify(vo);
	}

	@Override
	public void replyDelete(ReplyVO vo) throws Exception {
		dao.replyDelete(vo);
	}

	@Override
	public ReplyVO replySelect(int rno) throws Exception {
		
		return dao.replySelect(rno);
	}

	
	
}
