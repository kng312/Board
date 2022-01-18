package com.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;

@Repository
public class boardDAOImpl implements boardDAO{

	@Inject
	private SqlSession sql;
	
	private static String namespace = "com.test.mappers.board";

	//목록
	@Override
	public List<BoardVO> list(SearchCriteria scri) {
		
		return sql.selectList(namespace + ".list", scri);
	}
	
	//작성
	@Override
	public void write(BoardVO vo) throws Exception {
		
		 sql.insert(namespace + ".write", vo);
		
	}

	//조회
	@Override
	public BoardVO view(int no) throws Exception {

		return sql.selectOne(namespace + ".view", no);
	}
	
	//수정
	@Override
	public void modify(BoardVO vo) throws Exception {
		
	 sql.update(namespace + ".modify", vo);
	 
	}
	
	//답변이 있는 게시글 삭제
	public void deleteup(int no) throws Exception {
	 sql.update(namespace + ".deleteup", no);
	}
	
	//답변이 없는 게시글 삭제
	public void delete(int no) throws Exception {
	 sql.delete(namespace + ".delete", no);
	}
	
	// 게시물 총 갯수
	@Override
	public int count(SearchCriteria scri) throws Exception {
	 return sql.selectOne(namespace + ".count", scri); 
	}


	@Override
	public void replyShape(BoardVO vo) {
		sql.update(namespace + ".replyShape", vo);
		
	}

	@Override
	public int replyWrite(BoardVO vo) {
		return sql.insert(namespace+".replyWrite", vo);
	}
	
	//댓글 총 개수
	@Override
	public void replyCount(int no) throws Exception {
		sql.update(namespace + ".updateReplyCount", no);
	}

	@Override
	public int replyDeleteC(int bid, int ordered) throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		map.put("bid", bid);
		map.put("ordered", ordered);
		
		return sql.selectOne(namespace +".replyDeleteC" , map);
		
	}
	
	//첨부파일 총 갯수
	@Override
	public int fileCount(int no) throws Exception {
		return sql.update(namespace + ".fileCount", no);
	}
	
	// 첨부파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		sql.insert(namespace +".insertFile", map);
	}
	
   	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int no) throws Exception {
		return sql.selectList(namespace + ".selectFileList", no);
	}
	
	// 첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
			return sql.selectOne(namespace +".selectFileInfo", map);
		}
	
	//첨부파일 수정
	@Override
	public void updateFile(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
		sql.update(namespace +".updateFile", map);
	}

	//엑셀 다운로드
	@Override
	public List<BoardVO> selectTestList(BoardVO vo, HttpServletResponse response, SearchCriteria scri) throws Exception {
		
		return sql.selectList(namespace +".selectTestList", vo);
	}

	
	
	
	// 게시물 목록 + 페이징
	/*@Override
	public List<BoardVO> listPage(int displayPost, int postNum) throws Exception {

	 HashMap<String, Integer> data = new HashMap<String, Integer>();
	  
	 data.put("displayPost", displayPost);
	 data.put("postNum", postNum);
	  
	 return sql.selectList(namespace + ".listPage", data);
	}
	
	// 게시물 목록 + 페이징 + 검색
	 @Override
	 public List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword) throws Exception {

	  HashMap<String, Object> data = new HashMap<String, Object>();
	  
	  data.put("displayPost", displayPost);
	  data.put("postNum", postNum);
	  
	  data.put("searchType", searchType);
	  data.put("keyword", keyword);
	  
	  return sql.selectList(namespace + ".listPageSearch", data);
	 }
	 
	// 게시물 총 갯수 + 검색 적용
	 @Override
	 public int searchCount(String searchType, String keyword) throws Exception {
	  
	  HashMap<String, Object> data = new HashMap<String, Object>();
	  
	  data.put("searchType", searchType);
	  data.put("keyword", keyword);
	  
	  return sql.selectOne(namespace + ".searchCount", data); 
	 }*/
	
	//댓글 추가
//	public boolean addReply (ReplyVO rvo) throws Exception{
//		
//		return sql.selectOne(namespace + ".addReply" + rvo);
//	}
//	
//	//댓글 가져오기
//	public List<ReplyVO> getReply(int boardIdx) throws Exception {
//		
//		return sql.selectList(namespace + ".getReply" + boardIdx);
//	}
	

}
