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
	#viewContent{
	    min-height: 5rem;
	    overflow-y: hidden;
	    resize: none;
	    wrap:hard;
}


	
</style>
<body>
	<div class="container" role="main">
		<h2>게시글 조회</h2><br />
			<input type="hidden" id="count" name="count" value="${count}"/>
			<form name="readForm" role="form" method="post">
				<input type="hidden" id="HIPW" value="<c:out value="${view.password}"/>" />
				<input type="hidden" id="no" name="no" value="${view.no}" />
				<input type="hidden" id="bid" name="bid" value="${view.bid}" />
				<input type="hidden" id="ordered" name="ordered" value="${view.ordered}" />
				<input type="hidden" id="layer" name="layer" value="${view.layer}" />
				<input type="hidden" id="num" name="num" value="${scri.num}"> 
				<input type="hidden" id="postNum" name="postNum" value="${scri.postNum}"> 
				<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
				<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
			</form>
			<form id="view" method="post">
				<div>
					<input type="button" class="btn btn-secondary mb-3" id="modify" value="게시물 수정">
					<input type="button" class="btn btn-secondary mb-3" id="delete"value="게시물 삭제"  >
					<input type="button" class="btn btn-secondary mb-3" id="listPagebtn" value="게시물 목록">
					<input type="button" class="btn btn-secondary mb-3" id="replybtn" value="답글 달기">
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
				<!-- 댓글 구현 -->
			<c:forEach items="${reply}" var="reply">
				<form name="DRF" class="DRF">
					<button type="button" style="float: right;" class="btn btn-secondary replyD" id="deleteR" data-rno="${reply.rno}">댓글삭제</button>
					<b>댓글 작성자</b> <input type="text" name="replyWirter"    value="<c:out value="${reply.replyWirter }"/>" readonly>
					<div>
						<span id="replyBY"></span>
						<textarea class="form-control viewContent" id="viewContent" cols="150" row="15" onmouseenter="resize(this)"  readonly><c:out value="${reply.content}" /></textarea>
					</div>	
				</form>
			</c:forEach>
			
			<!-- 댓글 작성 -->
			<div class="mb-3">
				<form name="replyForm" method="post">  
					<input type="hidden" name="no" value="${view.no}" />
					<input type="hidden" id="num" name="num" value="${scri.num}"> 
					<input type="hidden" id="postNum" name="postNum" value="${scri.postNum}"> 
					<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
					<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
					<br />
					<div class="mb-3">
						<label for="작성자" class="col-sm-2 control-label">댓글 작성자</label>
						<input type="text" class="form-control " id="replyWirter" name="replyWirter"/>
					</div>
					<div class="mb-3">
						<label for="content" class="col-sm-2 control-label">댓글 내용</label>
						<span id="replybyt"></span>
						<textarea row="15" class="form-control" id="content" title="댓글 내용을 입력하세요" name="content"></textarea>
					</div>
					<div class="mb-3">
						<label for="writer" class="col-sm-2 control-label">비밀 번호</label>
						<input type="password" class="form-control rpassword " id="rpassword" name="rpassword" placeholder="비밀번호는 4자 이하로 작성해주세요." title="비밀번호를 입력하세요"/>
					</div>
					<div class="mb-3">
						 <button type="submit" style="float: right;" id="replyWriteBtn" class="btn btn-secondary mb-3">작성</button>
					</div>
				</form>
			</div>
		</div>

		
<script>
	$(document).ready(function(){
		$('#replybtn').on('click', function(){
			   location.href = "/board/write?no=${view.no}"
					+ "&bid=${view.bid}"
					+ "&ordered=${view.ordered}"
					+ "&layer=${view.layer}"
				    + "&num=${scri.num}"
				    + "&postNum=${scri.postNum}"
				    + "&searchType=${scri.searchType}"
				    + "&keyword=${scri.keyword}"
		})
	})

	//댓글 삭제시 하나의 요소에만 이벤트가 들어감
	$(function(){
		var reForm = $("form[name='DRF']").length;
		for(var i = 0; i < reForm; i++){
			$('.replyD').eq(i).on("click", function(){
				   location.href = "/board/replyDeleteView?no=${view.no}"
					    + "&num=${scri.num}"
						+ "&bid=${view.bid}"
						+ "&ordered=${view.ordered}"
						+ "&layer=${view.layer}"
					    + "&postNum=${scri.postNum}"
					    + "&searchType=${scri.searchType}"
					    + "&keyword=${scri.keyword}"
					    + "&rno=" + $(this).attr("data-rno");
					
				});
			}
	});
	

	//댓글 작성
	$(function(){
		$("#replyWriteBtn").on("click", function(){
			  var formObj = $("form[name='replyForm']");
			  
				if(fn_valiChk()){
					return false;
				}
				else if(chkPW()){
					$('.rpassword').focus();
					$('#rpassword').val($('#rpassword').val().substring(0, 0));
					return false;
				}
				if($("#content").val().trim() == "" || $("#content").val() == null){
					alert($("#content").attr("title"));
					$('#content').focus();
					return false;
				}
				if($("#replyWirter").val().trim() == "" || $("#replyWirter").val() == null){
					alert($("#replyWirter").attr("title"));
					$('#replyWirter').focus();
					return false;
				}
			  formObj.attr("action", "/board/replyWrite");
			  formObj.submit();
			});
	})
	
	//빈공간 널값 체크
	function fn_valiChk(){
		var regForm = $("form[name='replyForm'] .rpassword").length;
		for(var i = 0; i<regForm; i++){
			if($(".rpassword").eq(i).val().trim() == "" || $(".rpassword").eq(i).val() == null){
				alert($(".rpassword").eq(i).attr("title"));
				$('.rpassword').eq(i).focus();
				return true;
			}
		}
	}
	//비밀번호 제한 체크
	function chkPW()
	{

		var pw = $(".rpassword").val();
		var num = pw.search(/[0-5]/g);
		var eng = pw.search(/[a-z]/ig);
		var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

		if(pw.length > 4){
			alert("비밀번호는 4자리 이내로 입력해주세요.");
			
			return true;
		}
	}
	
		
	//목록으로 가기
	$("#listPagebtn").on("click", function(){
	
		location.href = "/board/list?num=${scri.num}"
		+"&postNum=${scri.postNum}"
		+"&searchType=${scri.searchType}&keyword=${scri.keyword}";
		})
		
	
	
		//수정페이지로 가기
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
	
	//답글이 있는지 확인 후 삭제
	$(function(){
		$('#delete').on('click',function(){
			if($('#count').val() > 0){
				alert('답글이 달려있어 삭제할 수 없습니다.')
			}
			else
			{ deleteData() };
		})
	})
	
	//삭제 버튼
	function deleteData()
		{

			if($('#password').val() === $('#HIPW').val())
			{	if(confirm("삭제 하시겠습니까?") === true)
				{
					location.href="/board/delete?no=${view.no}"
						+ "&bid=${view.bid}"
						+ "&ordered=${view.ordered}"
						+ "&layer=${view.layer}"
						+"&num=${scri.num}"
						+"&postNum=${scri.postNum}"
						+"&searchType=${scri.searchType}&keyword=${scri.keyword}";
						
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
		//댓글 제한
		$(function() {
	    	$('#replybyt').html("("+$('#content').val().length+" / 300)");
	        $('#content').on('keyup mouseenter', function() 
			{
	            $('#replybyt').html("("+$(this).val().length+" / 300)");
	 
	            if($(this).val().length > 300) {
	                $(this).val($(this).val().substring(0, 300));
	                $('#replybyt').html("(300 / 300)");
	                alert("글자수를 초과 하셨습니다.")
	                
	            }
	        });
	    });
		
		//댓글 작성자 제한
		$(function() {
			$('#replyWirter').on('keyup', function() {
				if($(this).val().length > 15) 
					{
						$(this).val($(this).val().substring(0, 15));
						alert("작성자는 15자 이하로 작성하세요")				
					}
				if($("#replyWirter").val().trim() == "" || $("#replyWirter").val() == null){
					alert($("#replyWirter").attr("title"));
					$('#replyWirter').focus();
					}
						
				});
			});

	//textarea 높이 조절
	$(function(){
		$('#textArea_byteLimit').on('scroll', function() {
  			adjustHeight();
  			
		})
	})
	function adjustHeight() {
		var textEle = $('#textArea_byteLimit');
		textEle[0].style.height = 'auto';
		var textEleHeight = textEle.prop('scrollHeight');
		textEle.css('height', textEleHeight);
	};

	//댓글 textarea 높이 조절
	$(function(){
	$('#content').on('keyup scroll', function() {
			replyheight();
		})
	})
	function replyheight() {
		var textEle = $('#content');
		textEle[0].style.height = 'auto';
		var textEleHeight = textEle.prop('scrollHeight');
		textEle.css('height', textEleHeight);
	};
	
	
	/*//댓글뷰 textarea 높이 조절
	$(function(){
		var reViForm = $("form[name='DRF']").length;
		for(var i = 0; i < reViForm; i++){
		$('.viewContent').eq(i).on('scroll', function() {
			
				var textEle = $('.viewContent');
				textEle[0].style.height = 'auto';
				var textEleHeight = textEle.prop('scrollHeight');
				textEle.css('height', textEleHeight);
			})
		}
	})
	
	function viewReplyheight() {
		var textEle = $('.viewContent');
		textEle[0].style.height = 'auto';
		var textEleHeight = textEle.prop('scrollHeight');
		textEle.css('height', textEleHeight);
	};*/
	
	function resize(obj) {
	    obj.style.height = '1px';
	    obj.style.height = (12 + obj.scrollHeight) + 'px';
	}
	
	

	
	
	
	    
</script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
</body>
</html>