package com.board.controller;

import java.util.List;

import javax.inject.Inject;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.BoardVO;
import com.board.domain.Page;
import com.board.domain.ReplyVO;
import com.board.domain.SearchCriteria;
import com.board.service.ReplyService;
import com.board.service.boardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	boardService service;
	
	@Inject
	ReplyService replyService;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public void getList(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception{
    	
    	
    	
    	model.addAttribute("list",service.list(scri));
    	
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
    
    //게시물 작성 post
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String postWirte(BoardVO vo, @ModelAttribute("scri") SearchCriteria scri, 
										RedirectAttributes rttr, Model model) throws Exception {
    	service.write(vo);
    	
   	 rttr.addAttribute("num", scri.getNum());
   	 rttr.addAttribute("postNum", scri.getPostNum());
   	 rttr.addAttribute("searchType", scri.getSearchType());
   	 rttr.addAttribute("keyword", scri.getKeyword());
   	 
   	 model.addAttribute("scri", scri);
   	 
       return "redirect:/board/list";
    }
    
    //게시물 조회
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public void getView(@RequestParam("no") int no, 
    					@ModelAttribute("scri") SearchCriteria scri,
    					Model model) throws Exception {
    	service.replyCount(no);
    	logger.info("replyDeleteC");
    	
    	model.addAttribute("count", service.replyDeleteC(no));
    	
    	
    	BoardVO vo = service.view(no);
    	model.addAttribute("view", vo);
    	model.addAttribute("scri", scri);
    	
    	
    	//댓글 조회
    	List<ReplyVO> reply = replyService.replyList(no);
    	model.addAttribute("reply",reply);
    }
    
    
    //게시물 수정
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public void getModify(	@RequestParam("no") int no,
    						@ModelAttribute("scri") SearchCriteria scri,Model model) throws Exception {
    	
    	BoardVO vo = service.view(no);
    	model.addAttribute("view", vo);
    	
    	model.addAttribute("scri", scri);
    	
    	
    }
    
    //게시물 수정
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String postModify(BoardVO vo,
    						@ModelAttribute("scri") SearchCriteria scri, 
    						RedirectAttributes rttr) throws Exception {
     service.modify(vo);
     
	 rttr.addAttribute("num", scri.getNum());
	 rttr.addAttribute("postNum", scri.getPostNum());
	 rttr.addAttribute("searchType", scri.getSearchType());
	 rttr.addAttribute("keyword", scri.getKeyword());
		
	 return "redirect:/board/view?no=" + vo.getNo();
    }
    
    //게시물 삭제
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
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/view?no=" + vo.getNo();
	}
	
	//댓글 삭제
    @RequestMapping(value = "/replyDeleteView", method = RequestMethod.GET)
    public String getReplyDelete(ReplyVO vo, SearchCriteria scri,Model model) throws Exception {
    	logger.info("reply delete");
    	
    	
    	model.addAttribute("replyDelete", replyService.replySelect(vo.getRno()));
    	System.out.println("댓글번호" + replyService.replySelect(vo.getRno()));
    	
    	model.addAttribute("scri", scri);
    	
    	return "board/replyDeleteView";
    	
    }

	//댓글 삭제
	@RequestMapping(value="/replyDelete", method = RequestMethod.POST)
	public String replyDelete(ReplyVO vo, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply delete");
		
		replyService.replyDelete(vo);
		System.out.println("댓글번호" + replyService.replySelect(vo.getRno()));
		
		rttr.addAttribute("no", vo.getNo());
		rttr.addAttribute("num", scri.getNum());
		rttr.addAttribute("postNum", scri.getPostNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/view?no=" + vo.getNo();
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





