package com.board.domain;

public class SearchCriteria extends Criteria {
	
	private String searchType = "";
	private String keyword = "";
	
	public void setSearchType(String searchType) {
		 this.searchType = searchType;  
		}

	public String getSearchType() {
		 return searchType;
		} 

	public void setKeyword(String keyword) {
		 this.keyword = keyword;  
		} 

	public String getKeyword() {
		 return keyword;
		}
	
	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + "]";
	}
	
}
