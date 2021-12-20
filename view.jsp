<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
		<h2>게시글 조회</h2><br />
			<form name="readForm" role="form" method="post">
				<input type="hidden" id="HIPW" value="<c:out value="${view.password}"/>" />
				<input type="hidden" id="no" name="no" value="${view.no}" />
				<input type="hidden" id="num" name="num" value="${scri.num}"> 
				<input type="hidden" id="postNum" name="postNum" value="${scri.postNum}"> 
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
			</form>
			<form id="view" method="post">
				<div>
					<input type="button" class="btn btn-secondary mb-3" id="modify" value="게시물 수정">
					<input type="button" class="btn btn-secondary mb-3" value="게시물 삭제" onclick="deleteData()">
					<input type="button" class="btn btn-secondary mb-3" id="listPagebtn" value="게시물 목록">
				</div>
				<div class="mb-3">
					<label for="title" class="col-sm-2 control-label">제목</label>
					<input id="title" type="text" class="form-control" name="title" value="<c:out value="${view.title}"/>"  readonly/> <br />
				</div>

				<div class="mb-3">
					<label for="writer" class="col-sm-2 control-label">작성자</label>
					<input id="writer" type="text" class="form-control" name="writer" value="<c:out value="${view.writer}"/>" readonly/> <br />
				</div>

				<div class="mb-3">
					<label for="password" class="col-sm-2 control-label">비밀번호</label><br />
					<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호는 영문,숫자,특수문자로 6~8자 입니다." />
				</div>

				<div class="mb-3">
					<label for="content" class="col-sm-2 control-label">내용</label><br />
					<span id="nowByte"></span>
					<textarea rows="15" name="content" style="width:100%; border:0; resize: none;" id="textArea_byteLimit" class="form-control" readonly><c:out value="${view.content}" /></textarea>
				</div>


			</form>
		</div>
<script>
	$("#listPagebtn").on("click", function(){
	
		location.href = "/board/list?num=${scri.num}"
		+"&postNum=${scri.postNum}"
		+"&searchType=${scri.searchType}&keyword=${scri.keyword}";
		})
		
	$(function(){
		$('#modify').on("click", function(){
			
			if( $('#password').val() == '')
			{
				alert("비밀번호를 입력하세요")
				$('#password').focus();
			}
			else if($('#HIPW').val() == $('#password').val())
			{
				location.href="/board/modify?no=${view.no}"
			}
			else
			{
				alert("비밀번호가 틀렸습니다.")
				$('#password').focus();
				$('#password').val($('#password').val().substring(0, 0));
			}
		})
	});
	
	function deleteData()
		{
			location.href = "/board/list?num=${scri.num}"
			+"&postNum=${scri.postNum}"
			+"&searchType=${scri.searchType}&keyword=${scri.keyword}";
			if($('#password').val() === $('#HIPW').val())
			{	if(confirm("삭제 하시겠습니까?") === true)
				{
					location.href="/board/delete?no=${view.no}"
				}
				else{	}
			}
			else if($('#password').val() == '')
			{
				alert("비밀번호를 입력하세요.")
				$('#password').focus();
				
			}
			else
			{
				alert("비밀번호가 틀렸습니다.")
				$('#password').focus();
			}
		}
		
	    
		$(function() {
	    	$('#nowByte').html("("+$('#textArea_byteLimit').val().length+" / 1000)");
	        $('#textArea_byteLimit').on('keyup', function() {
	            $('#nowByte').html("("+$(this).val().length+" / 1000)");
	 
	            if($(this).val().length > 1000) {
	                $(this).val($(this).val().substring(0, 1000));
	                $('#nowByte').html("(1000 / 1000)");
	                alert("글자수를 초과 하셨습니다.")
	                
	            }
	        });
	    });

	//textarea 높이 조절
	$(function(){
		$('#textArea_byteLimit').on('mouseover', function() {
  			adjustHeight();
		})
	})
	function adjustHeight() {
		var textEle = $('textarea');
		textEle[0].style.height = 'auto';
		var textEleHeight = textEle.prop('scrollHeight');
		textEle.css('height', textEleHeight);
	};
	    
</script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
</body>
</html>