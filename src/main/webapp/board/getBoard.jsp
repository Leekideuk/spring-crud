<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>

<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-8 col-sm-8 col-xs-8">
			<ul class="list-inline" style="padding-top:5px;">
				<li><strong>${boardMap['board'].userId}</strong>&nbsp;</li>
				<li><fmt:formatDate value="${boardMap['board'].regDate }" pattern="yyyy-MM-dd HH:mm"/></li>
				<li>조회 ${boardMap['board'].cnt }&nbsp;</li>
				<li>번호 ${boardMap.board.boardId }&nbsp;</li>
			</ul>
			<pre>${boardMap['board'].title }</pre>
			<pre>${boardMap['board'].content }</pre>
			<span>첨부</span>
			<c:forEach var="file" items="${boardMap['boardFileList']}" varStatus="status">
				<div><a href="fileDownload.do?fileId=${file.fileId }">${file.fileName}</a>${file.sizeToString()}</div>
			</c:forEach>
			
			<!-- 댓글 시작-->	
			<div style="padding:10px 0 30px 0;">
				<div id="commentList">
					
				</div>
				<span>댓글</span>
				<form class="commentForm">
					<input type="hidden" name="boardId" value="${boardMap['board'].boardId }"/>
					
					<div class="form-group col">
						<textarea id="boardText" name="content" rows="3" placeholder="내용을 입력하세요" style="width:90%"></textarea>
						<ul class="list-inline navbar-right">
							<li><input type="button" class="btn btn-default" value="등록" onclick="insertComment(this);" /></li>
						</ul>
					</div>
				</form>
			</div>
			<!-- 댓글 끝-->
			
			<div>
				<ul class="list-inline navbar-right">
					<li><input type="button" class="btn btn-default" onclick="location.href='updateBoard.do?boardId=${boardMap['board'].boardId }'" value="수정"/></li>
					<li><input type="button" class="btn btn-default" onclick="location.href='deleteBoard.do?boardId=${boardMap['board'].boardId }'" value="삭제"/></li>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- 페이징 처리 -->
	<div style="text-align: center;">
	<nav>
		<ul class="pagination"></ul>
	</nav>
	</div>
	
<script>
$(function(){
	getCommentList(1);
});
//form -> json 변환
function objectifyForm(formArray) {
	  var returnArray = {};
	  for (var i = 0; i < formArray.length; i++){
	    returnArray[formArray[i]['name']] = formArray[i]['value'];
	  }
	  return returnArray;
}

function insertComment(btnThis){
	var form = $(btnThis).parents("form").serializeArray();
	// form -> json 변환
	var json = objectifyForm(form);
	var json_str = JSON.stringify(json);
	$("#boardText").val("");
	$.ajax({
		url : "insertComment.do",
		data : json_str,
		dataType : "text",
		type : "post",
		contentType : "application/json; charset=utf-8",
		success:function(page){ 
			getCommentList(page);
		},
		error:function(result, status, error){
			console.log(error);
			alert("로그인 하세요.");
			location.href="login.do";
		}
	});
}

function getCommentList(curCommentPage){
	$.ajax({
		type: "get",
		url: "getCommentList.do?curCommentPage="+curCommentPage+"&boardId="+"${boardMap['board'].boardId }",
		dataType: "json",
		contentType : "application/json; charset=utf-8",
		success: function(result){
			var html="";
			for(var key in result.commentList){
				var comment = result.commentList[key];
				html += "<div class='comment' style='width:100%; display:inline-block; margin-left:"+20*comment.depth+"px'>";
				if(comment.deleteFlag == false){
				html += "<font size='2em'>"+comment.userId+"<span style='cursor:pointer;' onclick='setDeleteFlagTrue(this,"+comment.commentId+");'> [삭제]</span></font>";}
 				html += "<p>depth"+comment.depth+":::order"+comment.order+":::parentId"+comment.parentId+":::commentId"+comment.commentId+"</P>"
				html += "<div class='comment"+comment.commentId+"' style='cursor:pointer; width:80%;' onclick='getCommentForm(this);'><pre>"+comment.content+"</pre></div>";
				html += "<div></div>";
				html += "<input type='hidden' name='commentId' value="+comment.commentId+" />";
				html += "</div>"
				$("#commentList").html(html);
			}
			// 페이징 처리
			paging(result.commentPaging);
		}
	});
}

function paging(paging){
	var html = "";
	// 처음 페이지로 이동
	if(paging.curBlock > 1){ html+= "<li><a href='#' onclick='getCommentList(1)';>[처음]</a></li>" };
	// 이전 페이지 블록으로 이동
	if(paging.curBlock > 1){ html+= "<li><a href='#' onclick='getCommentList("+paging.prevBlockPage+")';>[이전]</a></li>"};
	for(var begin = paging.blockBegin; begin <= paging.blockEnd; begin++){
		// 현재 페이지면 하이퍼링크 제거
		if(begin == paging.criteria.curPage){html+= "<li><span style='color:red;'>"+begin+"</span></li>"}
		else{html+= "<li><a href='#' onclick='getCommentList("+begin+")';>"+begin+"</a></li>"};
	}
	// 다음 블록 페이지로 이동
	if(paging.curBlock <= paging.totalBlock){html+= "<li><a href='#' onclick='getCommentList("+paging.nextBlockPage+");'>[다음]</a></li>"};
	// 끝 페이지로 이동
	if(paging.criteria.curPage <= paging.totalPage){html+= "<li><a href='#' onclick='getCommentList("+paging.totalPage+");'>[끝]</a></li>"};
	$(".pagination").html(html);
}

var commentId = 0;
function getCommentForm(commentThis){
	var html = "<form class=commentForm2>"
		html += "<input type='hidden' name='boardId' value="+${boardMap['board'].boardId }+" />"
		html += "<input type='hidden' name='parentId' value="+$(commentThis).next().next().val()+" />"
		html += "<div class='form-group col'>"
		html += "<textarea name='content' rows='3' placeholder='내용을 입력하세요' style='width:90%'></textarea>"
		html += "<ul class='list-inline navbar-right'>"
		html += "<li><input type='button' class='btn btn-default' value='등록' onclick='insertComment(this);' /></li>"
		html += "</ul></div></form>"
	// 댓글 클릭 시 입력 폼은 한 개만 나오도록.
	if(commentId == 0){
		$(commentThis).next().append(html);
		commentId = $(commentThis).attr("class");
	}else if(commentId == $(commentThis).attr("class")){
		$(".commentForm2").remove(); 
		commentId = 0;
	}else if(commentId != $(commentThis).attr("class")){
		$(".commentForm2").remove();
		$(commentThis).next().append(html);
		commentId = $(commentThis).attr("class");
	}
}

function setDeleteFlagTrue(t, commentId){
	commentId = "{\"commentId\":\""+commentId+"\"}";
	$.ajax({
		type: "post",
		url: "setDeleteFlagTrue.do",
		data: commentId,
		dataType: "text",
		contentType : "application/json; charset=utf-8",
		success: function(page, status){
			getCommentList(page);
		}
	});
}


</script>

</body>
</html>