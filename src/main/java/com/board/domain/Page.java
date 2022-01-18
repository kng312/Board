package com.board.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Page {
	
	//현재 페이지, 페이지당 게시물 표시 갯수
	private Criteria cri;
	
	// 게시물 총 갯수
	private int count;

	// 한번에 표시할 페이징 번호의 갯수
	private int pageNumCnt = 10;

	// 표시되는 페이지 번호 중 마지막 번호
	private int endPageNum;

	// 표시되는 페이지 번호 중 첫번째 번호
	private int startPageNum;

	// 다음/이전 표시 여부
	private boolean prev;
	private boolean next;
	private int last;
	private int first =1 ;
	


	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setCount(int count) {
		 this.count = count;
		 dataCalc();
		}

	public int getCount() {
		 return count;
		}

	public int getPageNumCnt() {
		 return pageNumCnt;
		}

	public int getEndPageNum() {
		 return endPageNum;
		}

	public int getStartPageNum() {
		 return startPageNum;
		}

	public boolean getPrev() {
		 return prev;
		} 

	public boolean getNext() {
		 return next;
		}
	public int getLast(){
		return last;
	}
	
	public int getFirst() {
		
		if(startPageNum > 1)
		{
			first = 1;
		}
		return first;
	}


	private void dataCalc() {
		 
		 // 마지막 번호
		 endPageNum = (int)(Math.ceil((double)cri.getNum() / (double)pageNumCnt) * pageNumCnt);
		 
		 // 시작 번호
		 startPageNum = endPageNum - (pageNumCnt - 1);
		 
		 // 마지막 번호 재계산
		 int endPageNum_tmp = (int)(Math.ceil((double)count / (double)cri.getPostNum()));
		 
		 if(endPageNum > endPageNum_tmp) {
		  endPageNum = endPageNum_tmp;
		 }
		 
		 prev = startPageNum == 1 ? false : true;
		 next = endPageNum * cri.getPostNum() >= count ? false : true;
		 last = (int) (Math.ceil((double)count / (double)cri.getPostNum())); 

		 
		 
		}
	//페이지 쿼리 만드는 메소드
	public String makeQuery(int num) {
		UriComponents uri = UriComponentsBuilder.newInstance()
				.queryParam("num", num)
				.queryParam("postNum", cri.getPostNum())
				.build();
		return uri.toUriString();
	}
	
	  public String makeQuery(int idx, int num) {
		    UriComponents uri = UriComponentsBuilder.newInstance()
		            .queryParam("idx", idx)
		            .queryParam("num", num)
		            .queryParam("postNum", cri.getPostNum())
		            .build();
		    return uri.toUriString();
		  }
	
	//검색 타입과 검색어
	public String makeSearch(int num)
	{
	  
	 UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("num", num)
	            .queryParam("postNum", cri.getPostNum())
	            .queryParam("searchType", ((SearchCriteria)cri).getSearchType())
	            .queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
	            .build(); 
	    return uriComponents.toUriString();  
	}
	

	private String encoding(String keyword) {
		if(keyword == null || keyword.trim().length() == 0) { 
			return "";
		}
		 
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		} catch(UnsupportedEncodingException e) { 
			return ""; 
		}
	}
	

	
	


}
