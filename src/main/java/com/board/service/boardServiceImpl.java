package com.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.dao.boardDAO;
import com.board.domain.BoardVO;
import com.board.domain.Criteria;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;
import com.board.util.FileUtils;

@Service
public class boardServiceImpl implements boardService{

	@Inject
	private boardDAO dao;
	
	@Inject
	private FileUtils fileUtils;
	
	//목록
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		return dao.list(scri);
	}
	
	//작성
	public void write(BoardVO vo,MultipartHttpServletRequest mpRequest) throws Exception
	{
		if(vo.getNo() == 0) 
		{
			 dao.write(vo);
		}
		else 
		{
			dao.replyShape(vo);
			 dao.replyWrite(vo);
		}
		
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(vo, mpRequest); 
		int size = list.size();
		for(int i=0; i<size; i++)
		{ 
			dao.insertFile(list.get(i)); 
		}
		
		
		
		
		
	}

	//조회
	@Override
	public BoardVO view(int no) throws Exception {
		
		return dao.view(no);
	}
	
	//수정
	@Override
	public void modify(BoardVO vo,String[] files, String[] fileNames, MultipartHttpServletRequest mpRequest) throws Exception {
	  
		dao.modify(vo);
	 
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(vo, files, fileNames, mpRequest);
		Map<String, Object> tempMap = null;
		
		int size = list.size();
		
		for(int i = 0; i<size; i++) 
		{
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) 
			{
				dao.insertFile(tempMap);
			}
			else 
			{
				dao.updateFile(tempMap);
			}
		}
	}
	
	//답변이 있는 게시글 삭제
	@Override
	public void deleteup(int no) throws Exception {
		dao.deleteup(no);
	}
	
	//답변이 없는 게시글 삭제
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
	
	//답글 카운트
	@Override
	public int replyDeleteC(int bid, int ordered) throws Exception {
		
		return dao.replyDeleteC(bid, ordered);
		
	}
	
	// 첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int no) throws Exception {
		
		return dao.selectFileList(no);
	}
	
	// 첨부파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		return dao.selectFileInfo(map);
	}
	
	//첨부파일 총 갯수
	@Override
	public int fileCount(int no) throws Exception {
		return dao.fileCount(no);
	}
	
	//엑셀 다운로드
	@Override

	   public void excelDown(BoardVO vo, HttpServletResponse response) throws Exception {

	 

	   List<BoardVO> testList = dao.selectTestList(vo, response);

	 

	   try {

	      //Excel Down 시작

	      Workbook workbook = new HSSFWorkbook();

	 

	      //시트생성

	      Sheet sheet = workbook.createSheet("list");

	 

	      //행, 열, 열번호

	      Row row = null;

	      Cell cell = null;

	      int rowNo = 0;

	 

	      // 테이블 헤더용 스타일

	      CellStyle headStyle = workbook.createCellStyle();

	      // 가는 경계선을 가집니다.

	      headStyle.setBorderTop(BorderStyle.THIN);

	      headStyle.setBorderBottom(BorderStyle.THIN);

	      headStyle.setBorderLeft(BorderStyle.THIN);

	      headStyle.setBorderRight(BorderStyle.THIN);

	      // 배경색은 노란색입니다.

	      headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());

	      headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	      // 데이터용 경계 스타일 테두리만 지정

	      CellStyle bodyStyle = workbook.createCellStyle();

	      bodyStyle.setBorderTop(BorderStyle.THIN);

	      bodyStyle.setBorderBottom(BorderStyle.THIN);

	      bodyStyle.setBorderLeft(BorderStyle.THIN);

	      bodyStyle.setBorderRight(BorderStyle.THIN);

	 

	      // 헤더명 설정

	      String[] headerArray = {"글번호", "제목","작성일","작성자","내용"};

	      row = sheet.createRow(rowNo++);

	      for(int i=0; i<headerArray.length; i++) {

	      cell = row.createCell(i);

	      cell.setCellStyle(headStyle);

	      cell.setCellValue(headerArray[i]);

	      }

	 

	      for(BoardVO excelData : testList ) {

	 

	      row = sheet.createRow(rowNo++);

	      cell = row.createCell(0);

	      cell.setCellStyle(bodyStyle);

	      cell.setCellValue(excelData.getRownum());

	 
	      cell = row.createCell(1);

	      cell.setCellStyle(bodyStyle);

	      cell.setCellValue(excelData.getTitle());
	      
	      

	      cell = row.createCell(2);

	      cell.setCellStyle(bodyStyle);

	      cell.setCellValue(excelData.getRegDate());
	     
	      

	      cell = row.createCell(3);

	      cell.setCellStyle(bodyStyle);

	      cell.setCellValue(excelData.getWriter());

	 

	      cell = row.createCell(4);

	      cell.setCellStyle(bodyStyle);

	      cell.setCellValue(excelData.getContent());

	 

	      }

	 

	      // 컨텐츠 타입과 파일명 지정

	      response.setContentType("ms-vnd/excel");

	      response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("list.xls", "UTF8"));

	 

	      // 엑셀 출력

	      workbook.write(response.getOutputStream());

	      workbook.close();

	      } catch (Exception e) {

	      e.printStackTrace();

	      }

	 

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
