package com.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;

public interface boardService {
	
	//목록
	public List<BoardVO> list(SearchCriteria scri) throws Exception;
	
	//작성
	public void write(BoardVO vo,MultipartHttpServletRequest mpRequest) throws Exception;
	
	//조회
	public BoardVO view(int no) throws Exception;
	
	//수정
	public void modify(BoardVO vo, String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception;
	
	//답변이 없는 게시글 삭제
	public void deleteup(int no) throws Exception;
	
	//답변이 없는 게시글 삭제
	public void delete(int no) throws Exception;
	
	//답글 카운트
	public int replyDeleteC(int bid, int ordered) throws Exception;
	
	// 게시물 총 갯수
	public int count(SearchCriteria scri) throws Exception;
	
	//댓글 총 갯수
	public void replyCount(int no) throws Exception;
	
	//첨부파일 총 갯수
	public int fileCount(int no) throws Exception;
	
	// 첨부파일 조회
	public List<Map<String, Object>> selectFileList(int no) throws Exception;
	
	// 첨부파일 다운'
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;

	//엑셀 다운로드
	public void excelDown(BoardVO vo, HttpServletResponse response) throws Exception;

	
	
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
