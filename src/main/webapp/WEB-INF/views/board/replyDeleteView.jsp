<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko">
<head>
<meta charset="UTF-8">
<title>게시물 조회</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<style>
	body{
		padding-top : 70px;
		padding-bottom : 30px;
	}
	::-webkit-input-placeholder {
		text-align : center;
	}
</style>
<body>
	<div class="container" role="main">
				<h2>댓글 삭제</h2><br />
		 <section id="container">
		  <form name="deleteForm" role="form" method="post" action="/board/replyDelete">
		   <input type="hidden" id="no" name="no" value="${replyDelete.no}" readonly="readonly" />
		   <input type="hidden" id="rno" name="rno" value="${replyDelete.rno}" readonly="readonly" />
		   <input type="hidden" id="num" name="num" value="${scri.num}" readonly="readonly" />
		   <input type="hidden" id="postNum" name="postNum" value="${scri.postNum}" readonly="readonly" />
		   <input type="hidden" id="searchType" name="searchType" value="${scri.searchType}" readonly="readonly" />
		   <input type="hidden" id="keyword" name="keyword" value="${scri.keyword}" readonly="readonly" />
		   
		   <p>비밀번호를 입력해 주세요</p>
		    <input type="hidden" class="RPW"  id="RPW" name="rpassword" value="${replyDelete.rpassword}" />
			<input type="password" title="비밀번호" class="rpassword" id="rpassword" ><br />
		   
		   
		    <input type="button" id="DR" value="댓글 삭제" />
		    <button type="button" id="cancel_btn">취소</button>
		
		    <script>
		    
			$('#DR').on('click', function(){
				
					if($('#rpassword').val() == '')
					{
						alert("비밀번호를 입력하세요")
						$('#rpassword').focus();
					}
					else if($('.RPW').val() == $('.rpassword').val())
					{
						if(confirm("삭제 하시겠습니까?") === true){
							var formObj = $("form[name='deleteForm']");
							formObj.attr("action", "/board/replyDelete");
							formObj.submit();
						}
					}
					else
					{
						alert("비밀번호가 틀렸습니다.")
						$('.rpassword').focus();
						$('.rpassword').val($('.rpassword').val().substring(0, 0));
					}
		})
		    
		    
		    // 폼을 변수에 저장
		    var formObj = $("form[role='updateForm']"); 
		    
		    // 취소 버튼 클릭
		    $("#cancel_btn").click(function(){
		     
		     location.href = "/board/view?no=${replyDelete.no}"
			  + "&bid=${replyDelete.bid}"
			  + "&ordered=${replyDelete.ordered}"
			  + "&layer=${view.layer}"
		      + "&num=${scri.num}"
		      + "&postNum=${scri.postNum}"
		      + "&searchType=${scri.searchType}"
		      + "&keyword=${scri.keyword}";      
		    });
		    </script>
		   
		  </form>
		 </section>
	</div>
		
<script>
	
	

	    
</script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
</body>
</html>