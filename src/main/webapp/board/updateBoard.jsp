<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>	

// form -> json 변환
function objectifyForm(formArray) {
	  var returnArray = {};
	  for (var i = 0; i < formArray.length; i++){
	    returnArray[formArray[i]['name']] = formArray[i]['value'];
	  }
	  return returnArray;
}

var fileIdList = new Array();
var fileIdx = 0;

$(document).ready(function(){
	$("#fileList").on("click", "a", function(e){
		fileIdList[fileIdx++] = $(this).attr("data-src");
		$(this).parent("div").remove();
	});
	
	$("#submitBtn").on("click", function(e){
		var form = $("#updateForm").serializeArray();
		// form -> json 변환
		var json = objectifyForm(form);
		// 삭제 할 fileList 추가
		json.fileIdList=fileIdList;
		var json_str = JSON.stringify(json);
		
		$.ajax({
			url : "updateBoard.do",
			type : "post",
			data : json_str,
			dataType : "text",
			contentType : "application/json; charset=utf-8",
			success : function(result){
				alert("수정완료");
				location.href="getBoard.do?boardId=${boardMap['board'].boardId }"
			}
		});
	});
});
</script>
</head>

<body>
	<%@ include file="/header.jsp" %>
	<div class="container" style="padding-top: 10px;">
	<form id="updateForm">
		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-8 col-sm-8 col-xs-8">
			<input name="boardId" type="hidden" value="${boardMap['board'].boardId }"/>
			<ul class="list-inline" style="padding-top:5px;">
				<li><strong>${boardMap['board'].userId}</strong></li>
				<li><fmt:formatDate value="${boardMap['board'].regDate }" pattern="yyyy-MM-dd HH:mm"/></li>
				<li>조회 ${boardMap['board'].cnt }</li>
			</ul>
			<div class="form-group"	>
				<input name="title" type="text" value="${boardMap['board'].title }" class="form-control" placeholder="제목을 입력하세요."/>
			</div>
			<div class="form-group"	>
				<textarea name="content" rows="15" class="form-control" placeholder="내용을 입력하세요">${boardMap['board'].content }</textarea>
			</div>

			<span>첨부</span>
			<div id="fileList">
				<c:forEach var="file" items="${boardMap['boardFileList']}"> 
					<div>
						${file.fileName}${file.sizeToString()}
						<a href="#" data-src=${file.fileId }>삭제</a>
					</div>
				</c:forEach>       
			</div>
			<ul class="list-inline navbar-right">
				<li><input type="button" id="submitBtn" class="btn btn-default" value="수정"/></li>
				<li><input type="button" class="btn btn-default" onclick="location.href='deleteBoard.do?boardId=${boardMap['board'].boardId }'" value="삭제"/></li>
			</ul>
		</div>
	</form>
	</div>
	

</body>
</html>