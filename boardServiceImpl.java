package com.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dao.boardDAO;
import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;

@Service
public class boardServiceImpl implements boardService{

	@Inject
	private boardDAO dao;
	
	//목록
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		return dao.list(scri);
	}
	
	//작성
	public int write(BoardVO vo) throws Exception
	{
		if(vo.getNo() == 0) {
			return dao.write(vo);
		}
		else {
			dao.replyShape(vo);
			return dao.replyWrite(vo);
		}
	}

	//조회
	@Override
	public BoardVO view(int no) throws Exception {
		
		return dao.view(no);
	}
	
	//수정
	@Override
	public void modify(BoardVO vo) throws Exception {
	  
	 dao.modify(vo);
	}
	
	//삭제
	@Override
	public void delete(int no) throws Exception {
		dao.delete(no);
	}
	
	// 게시물 총 갯수
	@Override
	public int count(SearchCriteria scri) throws Exception {
	 return dao.count(scri);
	}
	
	//댓글 갯수
	@Override
	public void replyCount(int no) throws Exception {
		dao.replyCount(no);
		
	}

	@Override
	public int replyDeleteC(int no) throws Exception {
		return dao.replyDeleteC(no);
		
	}
	
	

	
	// 게시물 목록 + 페이징
	
	/*@Override
	public List<BoardVO> listPage(int displayPost, int postNum) throws Exception {
	 return dao.listPage(displayPost, postNum);
	}
	
	// 게시물 목록 + 페이징 + 검색
	@Override
	public List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword) throws Exception {
	 return  dao.listPageSearch(displayPost, postNum, searchType, keyword);
	}
	
	// 게시물 총 갯수 + 검색 적용
	@Override
	public int searchCount(String searchType, String keyword) throws Exception {
	 return dao.searchCount(searchType, keyword);
	}*/
	
	//댓글 추가
	//public boolean addReply (ReplyVO rvo) throws Exception{
		
		//return dao.addReply(rvo);
	//}
	
	//댓글 가져오기
	///public List<ReplyVO> getReply(int boardIdx) throws Exception {
		
		//return dao.getReply(boardIdx);
	//}

}
