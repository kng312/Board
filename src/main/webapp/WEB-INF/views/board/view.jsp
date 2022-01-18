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
				<input type="hidden" id="FILE_NO" name="FILE_NO" value=""> 
			</form>
			<form id="view" method="post">
					<input type="button" class="btn btn-secondary mb-3" id="modify" value="게시물 수정">
					<input type="button" class="btn btn-secondary mb-3" id="delete"value="게시물 삭제"  >
					<input type="button" class="btn btn-secondary mb-3" id="listPagebtn" value="게시물 목록">
					<input type="button" class="btn btn-secondary mb-3" id="replybtn" value="답글 달기">
	

					<div class="card-header bg-light">
						<label for="title" class="col-sm-2 control-label">제목</label>
						<input id="title" type="text" class="form-control" name="title" value="<c:out value="${view.title}"/>"  readonly/> <br />
	

						<label for="writer" class="col-sm-2 control-label">작성자</label>
						<input id="writer" type="text" class="form-control" name="writer" value="<c:out value="${view.writer}"/>" readonly/> <br />

	
						<label for="password" class="col-sm-2 control-label">비밀번호</label><br />
						<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호는 영문,숫자,특수문자로 6~8자 입니다." /><br/>
						
						<label for="fileList" class="col-sm-2 control-label">파일목록</label><br />
					<c:forEach var="file" items="${file}">
						<a href="#" class="col-sm-2" id="fileNo" onclick="fn_fileDown('${file.FILE_NO}'); return false;">${file.ORG_FILE_NAME}</a> (${file.FILE_SIZE}kb)<br/><br/>
					</c:forEach>
				
					</div>
	
					<div class="card-header bg-light">
						<label for="content" class="col-sm-2 control-label">내용</label>
						<span id="nowByte" class="col-sm-2"></span>
						<textarea rows="15" name="content" style="width:100%; border:0; resize: none;" id="textArea_byteLimit" class="form-control" readonly><c:out value="${view.content}" /></textarea>
					</div>	
				<br /><br />
			</form>
			<!-- 댓글 작성 -->
			<div class="card mb-2">
				<div class="card-header bg-light">
	       			<i class="fa fa-comment fa"></i> 댓글
				</div>
				<div class="card-body">
					<ul class="list-group list-group-flush">
						<li class="list-group-item">
							<form name="replyForm" method="post">  
								<input type="hidden" name="no" value="${view.no}" />
								<input type="hidden" id="bid" name="bid" value="${view.bid}" />
								<input type="hidden" id="ordered" name="ordered" value="${view.ordered}" />
								<input type="hidden" id="num" name="num" value="${scri.num}"> 
								<input type="hidden" id="postNum" name="postNum" value="${scri.postNum}"> 
								<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
								<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
								
								<div class="form-inline mb-2">
									<label for="작성자" class=" fa fa-user-circle-o fa-2x">댓글 작성자</label>
									<input type="text" class=" form-control ml-3 comm" id="replyWirter" name="replyWirter" title="댓글 작성자를 입력하세요"/>
									
									<label for="writer" class="ml-4"><i class="fa fa-unlock-alt fa-2x"></i>댓글 비밀번호</label>
									<input type="password" class=" form-control ml-3  comm " id="rpassword" name="rpassword" placeholder="4자 이하로 작성해주세요." title="댓글 비밀번호를 입력하세요"/>
								</div><br/>
									<textarea class="form-control comm" rows="5" id="content" placeholder="댓글을 입력해주세요." title="댓글 내용을 입력하세요" name="content"></textarea>
									<span id="replybyt"></span>
									<button type="submit" style="float: right;" id="replyWriteBtn" class="btn btn-secondary mb-4">댓글 작성</button>
							</form>
						</li>
					</ul>
				</div>
			</div>
			<br /><br />
				<!-- 댓글 구현 -->
				<div class="card mb-2">
					<c:forEach items="${reply}" var="reply">
					<form name="DRF" class="DRF" method="post">
						<input type="hidden" name="no" value="${view.no}" />
						<input type="hidden" id="bid" name="bid" value="${view.bid}" />
						<input type="hidden" id="ordered" name="ordered" value="${view.ordered}" />
						<input type="hidden" id="num" name="num" value="${scri.num}"> 
						<input type="hidden" id="postNum" name="postNum" value="${scri.postNum}"> 
						<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"> 
						<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"> 
					
						<div class="card mb-2">
							<div class="card-header bg-light ">
		       					 <label for="댓글 작성자"  class=" fa fa-user-circle-o fa-2x">댓글 작성자</label>
		       					 <input style="width:250px;" class="form-control" name="replyWirter"    value="<c:out value="${reply.replyWirter }"/>" readonly>
							</div>
							
							<div class="card-header bg-light ">
								<span id="replyBY"></span>
								<textarea class="form-control viewContent" id="viewContent" cols="150" row="15" onmouseenter="resize(this)"  readonly><c:out value="${reply.content}" /></textarea>
								<button type="button" style="float: right;" class="btn btn-secondary replyD" id="deleteR" data-rno="${reply.rno}">댓글삭제</button>
							</div>
						</div>
						<br/>
					</form>	
				</c:forEach>
			</div>
			

		</div>

		
<script>

	//파일 다운로드
	function fn_fileDown(fileNo){
		var formObj = $("form[name='readForm']");
		$("#FILE_NO").attr("value", fileNo);
		if($('#writer').val() == '')
		{
			return false;
		}
		formObj.attr("action", "/board/fileDown");
		formObj.submit();
		}
	
	//삭제된 게시글 버튼 막기
	$(function(){
		if($('#writer').val() == '')
		{
			$('#modify').prop("disabled", true);
			$('#replybtn').prop("disabled", true);
			$('#replyWriteBtn').prop("disabled", true);
			$('#password').prop("disabled", true);
			$('#delete').prop("disabled", true);
			
		}
	})

	
	//답글 작성
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
		$('#delete').on('click',function() {
			if($('#count').val() > 0){
				alert('답글이 달려있어 정보만 삭제 됩니다.')
				deleteDataUP()
			}

			else{
				deleteData()
			}


	
		});
	});
	
	//답글이 있는 게시글 삭제 버튼
	function deleteDataUP()
		{

			if($('#password').val() === $('#HIPW').val())
			{	if(confirm("삭제 하시겠습니까?") === true)
				{
					location.href="/board/deleteup?no=${view.no}"
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
	
	//답글이 없는 게시글삭제 버튼
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
		
	    //내용 제한 표시
		$(function() 
		{
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
		
	    
		//댓글 삭제시 하나의 요소에만 이벤트가 들어가게 name으로 받아옴
		$(function()
		{
			var reForm = $("form[name='DRF']").length;
			for(var i = 0; i < reForm; i++)
			{
				$('.replyD').eq(i).on("click", function()
				{
					
					   location.href = "/board/replyDeleteView?no=${view.no}"
						    + "&bid=${view.bid}"
							+ "&ordered=${view.ordered}"
							+ "&layer=${view.layer}"
						    + "&num=${scri.num}"
						    + "&postNum=${scri.postNum}"
						    + "&searchType=${scri.searchType}"
						    + "&keyword=${scri.keyword}"
						    + "&rno="+$(this).attr("data-rno");
						
				});
			}
		});
		

		//댓글 작성
		$(function()
		{
			$("#replyWriteBtn").on("click", function()
			{
				  var formObj = $("form[name='replyForm']");
				  
					if(fn_valiChk()){
						return false;
					}
					else if(chkPW()){
						return false;
					}

				  formObj.attr("action", "/board/replyWrite");
				  formObj.submit();
				  
			});
		})
		
		//빈공간 널값 체크
		function fn_valiChk()
		{
			var regForm = $("form[name='replyForm'] .comm").length;

			for(var i = 0; i<regForm; i++)
			{
				if($(".comm").eq(i).val().trim() == "" || $(".comm").eq(i).val() == null)
				{
					alert($(".comm").eq(i).attr("title"));
					$('.comm').eq(i).focus();
					return true;
				}
			}
		}
		//비밀번호 제한 체크
		function chkPW()
		{

			var pw = $("#rpassword").val();
			var num = pw.search(/[0-5]/g);
			var eng = pw.search(/[a-z]/ig);
			var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

			if(pw.length > 4){
				$('#rpassword').val($('#rpassword').val().substring(0, 4));
				alert("댓글 비밀번호는 4글자 이하로 작성하세요.");
				$('#rpassword').focus();
				return true;
			}
			
			if($('#replyWirter').val().length > 15) 
			{
				$('#replyWirter').val($('#replyWirter').val().substring(0, 15));
				alert("작성자는 15자 이하로 작성 하십시오")
				$('#replyWirter').focus();
				return true;
				
			}
			
            if($('#content').val().length > 300) {
                $('#content').val($('#content').val().substring(0, 300));
                alert("댓글은 300자 이하로 작성하세요.")
               	$('#content').focus();
                return true;
			}
		}
		
		//댓글 제한
		$(function() {
	    	$('#replybyt').html("("+$('#content').val().length+" / 300)");
	        $('#content').on('keyup mouseenter', function() 
			{
	            $('#replybyt').html("("+$(this).val().length+" / 300)");
	 
	            if($(this).val().length > 300) {
	                $(this).val($(this).val().substring(0, 300));
	                $('#replybyt').html("(300 / 300)");
	                alert("댓글은 300자 이하로 작성하세요.")
	                
	            }
	        });
	    });
		
		//댓글 작성자 제한
		$(function() {
			$('#replyWirter').on('keyup', function() {
				if($('#replyWirter').val().length > 15) 
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
		
		//댓글 비밀번호 제한
		$(function() {
			$('#rpassword').on('keyup', function() {
				if($('#rpassword').val().length > 4) 
					{
						$(this).val($(this).val().substring(0, 4));
						alert("댓글 비밀번호는 4글자 이하로 작성하세요.")				
					}
				if($("#rpassword").val().trim() == "" || $("#rpassword").val() == null){
					alert($("#rpassword").attr("title"));
					$('#rpassword').focus();
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