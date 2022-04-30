package com.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.net.URLEncoder;

import com.board.domain.BoardVO;
import com.board.domain.Email;
import com.board.domain.EmailSender;
 
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;
import com.board.service.ReplyService;
import com.board.service.boardService;
import com.board.util.FileUtils;

import net.nurigo.java_sdk.Coolsms;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	boardService service;
	
	@Inject
	ReplyService replyService;

    @Autowired
    private EmailSender emailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public void getList(BoardVO vo, Model model,  @ModelAttribute("scri") SearchCriteria scri) throws Exception{
    	
    	
    	model.addAttribute("list",service.list(scri));
    	service.fileCount(vo.getFile_cnt());
    	
    	Page page = new Page();
    	page.setCri(scri);
    	page.setCount(service.count(scri));
    	
    	model.addAttribute("page", page);
    	
    	
    }
    
    //게시물 작성페이지로 이동
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public void getwrite(Model model, BoardVO vo, @ModelAttribute("scri") SearchCriteria scri,
													RedirectAttributes rttr) throws Exception
    {
    	
    	
    	model.addAttribute("write", vo);
    	model.addAttribute("scri", scri);
    	
    	

    	
    }
    
    //리퀘스트 매핑 url주소 받기
    //게시물 작성 post
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String postWirte(BoardVO vo, @ModelAttribute("scri") SearchCriteria scri, @RequestParam("no") int no,
										RedirectAttributes rttr, Model model, MultipartHttpServletRequest mpRequest,HttpServletRequest request) throws Exception {
    	logger.info("write");
    	service.write(vo, mpRequest);
    	
    	
    	
    	
    	
   	 	rttr.addAttribute("num", scri.getNum());
   	 	rttr.addAttribute("postNum", scri.getPostNum());
   	 	rttr.addAttribute("searchType", scri.getSearchType());
   	 	rttr.addAttribute("keyword", scri.getKeyword());
   	 
   	 	model.addAttribute("scri", scri);
   	 	
   	 	
   	 	//이메일 보내기
	   	 Email email = new Email();
	     

	     
	     
	     String subject = "게시글이 등록 되었습니다.";
	     String content = vo.getContent();
	      
	     
	     email.setSubject(subject);
	     email.setContent(content);
	     emailSender.SendEmail(email);
	     

	     String api_key ="NCSXEBN2VAQ1UKOG";
	     String api_secret = "IEMNKXLNBWYZ95UM4JZZVZJGAR1M5BG3";

	     Message coolsms = new Message(api_key, api_secret);

	    // 4 params(to, from, type, text) are mandatory. must be filled
	     HashMap<String, String> params = new HashMap<String, String>();	// 수신전화번호
	    
	     
	     params.put("to", "01029350116");
	     params.put("from", "01029350116");	// 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
	     params.put("type", "SMS");
	     params.put("text", "게시글이 등록되었습니다");
	     params.put("app_version", "test app 1.2"); // application name and version

	     try {
	    	    JSONObject obj = (JSONObject) coolsms.send(params);
	    	    System.out.println(obj.toString());
	     	 } catch (CoolsmsException e) {
	    	      System.out.println(e.getMessage());
	    	      System.out.println(e.getCode());
	    	    	}
	     
   	 service.fileCount(vo.getFile_cnt());
   	 System.out.println(vo.getFile_cnt());
	    	  
   
   	 	

    	
   	 
       return "redirect:/board/list";
    }
    
    //게시물 조회
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String getView(BoardVO vo, @RequestParam("no") int no, @RequestParam("bid") int  bid ,@RequestParam("ordered") int  ordered,
    					@ModelAttribute("scri") SearchCriteria scri,
    					Model model) throws Exception {
    	
    	
    	service.replyCount(no);
    	service.fileCount(no);
    	logger.info("fileCount");
    	
    	model.addAttribute("count", service.replyDeleteC(bid, ordered));
    	
    	
    	service.view(no);
    	model.addAttribute("view", service.view(vo.getNo()));
    	model.addAttribute("scri", scri);
    	
    	
    	//댓글 조회
    	List<ReplyVO> reply = replyService.replyList(no);
    	model.addAttribute("reply",reply);
    	
		List<Map<String, Object>> fileList = service.selectFileList(vo.getNo());
		model.addAttribute("file", fileList);
		
		return "/board/view";
    }
    
    
    //게시물 수정
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public void getModify(BoardVO vo,	@RequestParam("no") int no,
    						@ModelAttribute("scri") SearchCriteria scri,Model model) throws Exception {
    	
    	
    	model.addAttribute("view", service.view(vo.getNo()));
    	model.addAttribute("scri", scri);
    	
		List<Map<String, Object>> fileList = service.selectFileList(vo.getNo());
		model.addAttribute("file", fileList);
    	//string
    	
    }
    
    //게시물 수정
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String postModify(BoardVO vo,
    						@ModelAttribute("scri") SearchCriteria scri, 
    						RedirectAttributes rttr,
    						@RequestParam(value="fileNoDel[]") String[] files,
    						@RequestParam(value="fileNameDel[]") String[] fileNames,
    						MultipartHttpServletRequest mpRequest) throws Exception 
    {
    	service.modify(vo,files,fileNames,mpRequest);
     
		rttr.addAttribute("no", vo.getNo());
		rttr.addAttribute("bid", vo.getBid());
		rttr.addAttribute("ordered", vo.getOrdered());
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
	 return "redirect:/board/view?no=" + vo.getNo();
    }
    
    //답변이 있는 게시물 삭제
    @RequestMapping(value = "/deleteup", method = RequestMethod.GET)
    public String getDeleteup(@RequestParam("no") int no,
    						@ModelAttribute("scri") SearchCriteria scri, 
    						RedirectAttributes rttr) throws Exception {
      
    	service.deleteup(no);
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
    }
    
    //답변이 없는 게시물 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String getDelete(@RequestParam("no") int no,
    						@ModelAttribute("scri") SearchCriteria scri, 
    						RedirectAttributes rttr) throws Exception {
      
    	service.delete(no);
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
    }
    
    //댓글 작성
	@RequestMapping(value="/replyWrite", method = RequestMethod.POST)
	public String replyWrite(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		
		replyService.replyWrite(vo);
		
		rttr.addAttribute("no", vo.getNo());
		rttr.addAttribute("bid", vo.getBid());
		rttr.addAttribute("ordered", vo.getOrdered());
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/view?no=" + vo.getNo();
	}
	
	//댓글 삭제
    @RequestMapping(value = "/replyDeleteView", method = RequestMethod.GET)
    public String getReplyDelete(ReplyVO vo, SearchCriteria scri, @RequestParam(value="bid", required=false) int  bid ,@RequestParam(value="ordered",required=false) int  ordered, Model model) throws Exception {
    	logger.info("reply delete");
    	
    	model.addAttribute("count", service.replyDeleteC(bid, ordered));
    	model.addAttribute("replyDelete", replyService.replySelect(vo.getRno()));
    	
    	model.addAttribute("scri", scri);
    	
    	return "board/replyDeleteView";
    	
    }

	//댓글 삭제
	@RequestMapping(value="/replyDelete", method = RequestMethod.POST)
	public String replyDelete(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply delete");
		
		replyService.replyDelete(vo);
		
		rttr.addAttribute("no", vo.getNo());
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("bid", vo.getBid());
		rttr.addAttribute("ordered", vo.getOrdered());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/view?no=" + vo.getNo();
	}
	
    //파일 다운로드
	@RequestMapping(value="/fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception{
		Map<String, Object> resultMap = service.selectFileInfo(map);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte[]형식으로 변환한다.
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("C:\\mp\\file\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
	
	//엑셀 다운로드
	@RequestMapping(value = "/excelDown")
	@ResponseBody

	public void excelDown(@ModelAttribute BoardVO vo, HttpServletResponse response
	                                                    , HttpServletRequest request, RedirectAttributes rttr,SearchCriteria scri) throws Exception{

	   service.excelDown(vo, response,scri);
	   
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		

	}
    
    
//    //게시물 목록 + 페이징
//    /*
//    @RequestMapping(value="/listPage", method = RequestMethod.GET)
//    public void getListPage(Model model, @RequestParam("num") int num) throws Exception
//    {
//    	Page page = new Page();
//    	
//    	page.setNum(num);
//    	page.setCount(service.count());  
//
//    	List<BoardVO> list = service.listPage(page.getDisplayPost(), page.getPostNum());
//
//    	model.addAttribute("list", list);   
//    	
////    	model.addAttribute("pageNum", page.getPageNum());
////
////    	model.addAttribute("startPageNum", page.getStartPageNum());
////    	model.addAttribute("endPageNum", page.getEndPageNum());
////    	 
////    	model.addAttribute("prev", page.getPrev());
////    	model.addAttribute("next", page.getNext());  
//    	
//
//    	model.addAttribute("page", page);
//    	model.addAttribute("select", num);
//    	
//    	
//    	/*
//    	//게시물의 총 갯수
//    	int count = service.count();
//    	
//    	//한 페이지에 출력할 게시물 갯수
//    	int postNum = 10;
//    	
//    	//하단 페이징 번호([게시물 총 갯수 / 한페이지에 출력할 갯수] 의 올림)
//    	int pageNum = (int)Math.ceil((double)count/postNum);
//    	
//    	//출력할 게시물
//    	int displayPost = (num -1) * postNum;
//    	
//    	//한번에 펴시할 페이징 번호의 갯수
//    	int pageNum_cnt = 10;
//    	
//    	//표시되는 페이지 번호 중 마지막 번호
//    	int endPageNum = (int)(Math.ceil((double)num / (double)pageNum_cnt) * pageNum_cnt);
//    	
//    	//표시되는 페이지 번호 중 첫번째 번호
//    	int startPageNum = endPageNum - (pageNum_cnt -1);
//    	
//    	//마지막 번호 재계산
//    	int endPageNum_tmp = (int)(Math.ceil((double)count / (double)postNum));
//    	 
//    	if(endPageNum > endPageNum_tmp) {
//    	 endPageNum = endPageNum_tmp;
//    	}
//    	
//    	boolean prev = startPageNum == 1 ? false : true;
//    	boolean next = endPageNum * postNum >= count ? false : true;
//    	
//    	List<BoardVO> list = service.listPage(displayPost, postNum);
//    	model.addAttribute("list", list);
//    	model.addAttribute("pageNum", pageNum);
//    	
//    	// 시작 및 끝 번호
//    	model.addAttribute("startPageNum", startPageNum);
//    	model.addAttribute("endPageNum", endPageNum);
//
//    	// 이전 및 다음 
//    	model.addAttribute("prev", prev);
//    	model.addAttribute("next", next);
//    	
//    	//현재 페이지
//    	model.addAttribute("select", num);
//    	
//    }
//    
//    //게시물 목록 + 페이징 + 검색
//    @RequestMapping(value="/listPageSearch", method = RequestMethod.GET)
//    public void getListPageSearch(Model model,  @RequestParam("num") int num, 
//    											@RequestParam(value="searchType", required= false, defaultValue = "title") String searchType, 
//    											@RequestParam(value="keyword", required= false, defaultValue = "") String keyword) throws Exception
//    {
//    	Page page = new Page();
//    	
//    	page.setNum(num);
//    	page.setCount(service.searchCount(searchType, keyword));
//    	
//    	//검색어 타입과 검색어
//    	//page.setSearchTypeKeyword(searchType, keyword);
//    	page.setSearchType(searchType);
//    	page.setKeyword(keyword);
//
//    	List<BoardVO> list = 
//    			service.listPageSearch(page.getDisplayPost(), page.getPostNum(),searchType, keyword);
//
//    	model.addAttribute("list", list);   
//    	model.addAttribute("page", page);
//    	model.addAttribute("select", num);
//    	
//    	//model.addAttribute("searchType", searchType);
//    	//model.addAttribute("keyword", keyword);
//    }
//    */
//    

    
}





