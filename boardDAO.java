package com.board.dao;

import java.util.List;

import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;

public interface boardDAO {
	//조회
	public List<BoardVO> list(SearchCriteria scri ) throws Exception;
	
	//작성
	public int write(BoardVO vo) throws Exception;
	
	//조회
	public BoardVO view(int no) throws Exception;
	
	//수정
	public void modify(BoardVO vo) throws Exception;
	
	//삭제
	public void delete(int no) throws Exception;
	
	// 게시물 총 갯수
	public int count(SearchCriteria scri) throws Exception;

	public void replyShape(BoardVO vo);

	public int replyWrite(BoardVO vo);
	
	//자식 갯수 가져오기
	public int replyDeleteC(int no) throws Exception;
	
	//댓글 갯수 가져오기
	public void replyCount(int no) throws Exception;

	
	
	// 게시물 목록 + 페이징
	//public List<BoardVO> listPage(int displayPost, int postNum) throws Exception;
	
	// 게시물 목록 + 페이징 + 검색
	 //public List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	 
	// 게시물 총 갯수 + 검색 적용
	 //public int searchCount(String searchType, String keyword) throws Exception;
	
	//댓글 구현
	//public boolean addReply (ReplyVO rvo) throws Exception;
	
	//댓글 가져오기
	
	//public List<ReplyVO> getReply(int boardIdx) throws Exception;

}
