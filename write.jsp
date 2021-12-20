<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
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
	<h2>게시물 작성</h2><br />
	<form name="writeForm" method="post" action="/board/write">
		<div>
			<button type="submit" id="writer_btn"class="wbtn" >게시물 등록 </button>
			<input id="btt" type="button"  value="게시물 목록" onclick="location.href='/board/list'">
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td>
						<label class="col-sm-2 control-label">제목</label>
						<input id="title" type="text" size="100" class="chk" name="title" placeholder="제목은 50자 이하로 작성하세요" title="제목을 입력하세요"/> <br />
					<td>
				</tr>
	
				<tr>
					<td>
						<label class="col-sm-2 control-label">작성자</label>
						<input id="writer" type="text" size="100" class="chk" name="writer" placeholder="작성자는 10자이하로 작성하세요" title="작성자를 입력하세요"/> <br />
					</td>
				<tr>
	
				<tr>
					<td>
						<label class="col-sm-2 control-label">비밀번호</label>
						<input type="password" id="password" size="100" class="chk" name="password" placeholder="비밀번호는 영문,숫자,특수문자로 6~8자로 작성하세요" title="비밀번호를 입력하세요" />
					</td>
				</tr>
	
			<tr>
				<td>
					<label class="col-sm-2 control-label">내용</label>
					<span id="nowByte"></span>
					<textarea rows="20" cols="100" class="chk" style="width:100%; border:1; overflow: hidden; resize: none;" name="content" id="textArea_byteLimit" placeholder="내용은 1000자 이하로 작성하세요" class="chk" title="내용을 입력하세요" ></textarea>
				<td>
			</tr>
		</tbody>
	</table>

	</form>
</div>


		
<script>

//글자수 제한
	$(document).ready(function() 
	{
		$('#nowByte').html("("+$('#textArea_byteLimit').val().length+" / 1000)");
		$('#textArea_byteLimit').on('keyup mouseleave ', function() 
		{
			$('#nowByte').html("("+$(this).val().length+" / 1000)");
		
			if($(this).val().length > 1000) 
			{
				$(this).val($(this).val().substring(0, 1000));
				$('#nowByte').html("(1000 / 1000)");
				alert("글자수를 초과 하셨습니다.")
			}
		});
	});


	$(document).ready(function() 
	{
		$('#title').on('keyup', function() 
		{
			if($(this).val().length > 50) 
			{
				$(this).val($(this).val().substring(0, 50));
				alert("제목은 50자 이하로 작성 하십시오")				
			}
			if($("#title").val().trim() == "" || $("#title").val() == null){
				alert($("#title").attr("title"));
				$('#titl').focus();
			}
			
		});
	});

	$(document).ready(function() 
	{
		$('#writer').on('keyup', function() 
		{
			
			if($(this).val().length > 10) 
			{
				$(this).val($(this).val().substring(0, 10));
				alert("작성자는 10자 이하로 작성 하십시오")				
			}
			if($("#writer").val().trim() == "" || $("#writer").val() == null){
				alert($("#writer").attr("title"));
				$('#writer').focus();
			}
			
		});
	});
	

	// 작성 벨류데이션 체크 (빈값, 빈공간, 비밀번호 조건)
	$(document).ready(function(){
		var formObj = $("form[name='writeForm']");
		
		$(".wbtn").on("click ", function(){
			
			
			if(fn_valiChk()){
				return false;
			}
			else if(chkPW()){
				return false;
			}
			formObj.attr("action", "/board/write");
			formObj.attr("method", "post");
			formObj.submit();
		});
	})
	function fn_valiChk(){
		var regForm = $("form[name='writeForm'] .chk").length;
		for(var i = 0; i<regForm; i++){
			if($(".chk").eq(i).val().trim() == "" || $(".chk").eq(i).val() == null){
				alert($(".chk").eq(i).attr("title"));
				$('.chk').eq(i).focus();
				
				return true;
			}
		}
	}
	
	function chkPW()
		{

			var pw = $("#password").val();
			var num = pw.search(/[0-9]/g);
			var eng = pw.search(/[a-z]/ig);
			var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

			if(pw.length < 6 || pw.length > 8 || (pw.search(/\s/) != -1) || (num < 0 || eng < 0 || spe < 0 )){
				alert("비밀번호는 영문,특수문자,숫자 포함 6자리 ~ 8자리 이내로 입력해주세요.");
				$('#password').focus();
				return true;
			}
			
			if($('#writer').val().length > 10) 
			{
				$('#writer').val($('#writer').val().substring(0, 10));
				alert("작성자는 10자 이하로 작성 하십시오")
				$('#writer').focus();
				return true;
				
			}
			
			if($('#title').val().length > 50) 
			{
				$('#title').val($('#title').val().substring(0, 50));
				alert("제목은 50자 이하로 작성 하십시오")
				$('title').focus();
				return true;
			}
	}


	/*비밀번호 조건 마우스리브 체크
	$(document).ready(function(){
		$('#password').on('mouseleave', function(){
			var pw = $("#password").val();
			var num = pw.search(/[0-9]/g);
			var eng = pw.search(/[a-z]/ig);
			var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

			if(pw.length < 6 || pw.length > 8)
			{
				$('#password').val($('#password').val().substring(0, 8));
				alert("영문,숫자, 특수문자를 혼합하여 6자리 ~ 8자리 이내로 입력해주세요.");
				return false;
			}
			else if(pw.search(/\s/) != -1)
			{
				$('#password').val($('#password').val().substring(0, 8));
				alert("비밀번호는 공백 없이 입력해주세요.");
				return false;
			}
			else if(num < 0 || eng < 0 || spe < 0 )
			{
				$('#password').val($('#password').val().substring(0, 8));s
				alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
				return false;
			}
			else 
			{
				return true;
			}

		})
	})*/

	//textarea 높이 조절
	$(document).ready(function(){
		$('#textArea_byteLimit').on('keyup mouseleave', function() {
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