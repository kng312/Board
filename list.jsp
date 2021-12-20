<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType = "text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<style>
	.current{
		background-color: black;
		
	}
</style>


<body>
	<div class="container-fluid">
			<form role="form" method="get">
				<table class="table table-hover text-center">
					<thead>
						<tr >
							<th scope="col" class="text-center"> 번호</th>
							<th scope="col" class="text-center"> 제목</th>
							<th scope="col" class="text-center"> 작성일</th>
							<th scope="col" class="text-center"> 작성자</th>
						</tr>
					</thead>
					<c:forEach items="${list}" var="list">
						<tr>
							<td><c:out value="${list.rownum}" /></td>
							<td style="text-align: left;">
								<a href="/board/view?no=${list.no}&
													num=${scri.num}&
													postNum=${scri.postNum}&
													searchType=${scri.searchType}&
													keyword=${scri.keyword}" ><c:out value="${list.title}"/> </a>
							</td>
							<td>
								<fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd" />
							</td>
							<td><c:out value="${list.writer}"/> </td>
						</tr>
					</c:forEach>
				</table>
	
			<input type="button" class="btn btn-secondary pull-right" value="게시물 목록" onclick="location.href='/board/list/'"/>
			<input type="button" class="btn btn-secondary pull-right" value="게시물 작성" onclick="location.href='/board/write/'" />
					
		<div id="search" class="search row">
			<div  class="col-xs-2 col-sm-2">
				<select name="searchType" class="form-control">
					<option value="title" <c:if test="${scri.searchType eq 'title'}">selected</c:if>>제목</option>
					<option value="content" <c:if test="${scri.searchType eq 'content'}">selected</c:if>>내용</option>
					<option value="title_content" <c:if test="${scri.searchType eq 'title_content'}">selected</c:if>>제목+내용</option>
					<option value="writer" <c:if test="${scri.searchType eq 'writer'}">selected</c:if>>작성자</option>
				</select>
			</div>
			<div class="col-xs-10 col-sm-10">
				<div class="input-group">
					<input type="text" class="form-control" id="keywordInput" name="keyword" value="${scri.keyword}" />
					<span class="input-group-btn">
						<input type="button" class="btn btn-secondary pull-right" id="searchBtn" value="검색" />
					</span>
				</div>
			</div>
		</div>
			
		<div class="col-md-offset-3 text-center">
			<ul class="pagination justify-content-center">
				<c:if test="${page.prev}">
					<li class="page-item"><a class="page-link" href="${page.makeSearch(page.first)}">
					 	처음 </a></li>
				</c:if>
				<c:if test="${page.prev}">
					 <li class="page-item"><a class="page-link" href="${page.makeSearch(page.startPageNum - 1)}">
					 	이전 </a></li> 
				</c:if>
					
				<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
						<li class="page-item ${cri.num == num ? 'current' : ''}">
						<a class="page-link" href="${page.makeSearch(num)}">${num}</a></li> 
				</c:forEach>
				<c:if test="${page.next}">
					<li class="page-item"> <a class="page-link" href="${page.makeSearch(page.endPageNum + 1)}">
						다음 </a></li>
				</c:if>
				<c:if test="${page.next}">
					 <li class="page-item"><a class="page-link" href="${page.makeSearch(page.last)}">
					 	끝 </a></li>
				</c:if>
			</ul>
		</div>
	</form>
</div>
	<script>
		$(function(){
			$("#searchBtn").click(function(){
				self.location ='${page.makeQuery(1)}' + "&searchType=" + $("select option:selected").val() + "&keyword=" + encodeURIComponent($('#keywordInput').val());
			})
		})
	
	
	</script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
</body>
</html>

