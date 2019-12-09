<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
				console.log(result);
			}
		});
	});
});
</script>
</head>

<body>
	<%@ include file="/header.jsp" %>
	<form id="updateForm">
		<input name="boardId" type="hidden" value="${boardMap['board'].boardId }"/>
		<table border="1">
			<tr>
				<td>제목</td>
				<td><input name="title" type="text" value="${boardMap['board'].title }"/></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${boardMap['board'].userId}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" rows="10" cols="40">${boardMap['board'].content }</textarea></td>
			</tr>
			<tr>
				<td>등록일</td>
				<td>${boardMap['board'].regDate }</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${boardMap['board'].cnt }</td>
			</tr>
			<tr>
				<td>첨부</td>
				<td id="fileList">
					<c:forEach var="file" items="${boardMap['boardFileList']}"> 
                        <div>
                        	${file.fileName}${file.sizeToString()}
                        	<a href="#" data-src=${file.fileId }>삭제</a>
                        </div>
                    </c:forEach>       
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input id="submitBtn" type="button" value="수정"/></td>
			</tr>
		</table>
		
	</form>
	<hr>
	<a href="getBoardList.do">[게시판]</a>
	<a href="deleteBoard.do?boardId=${boardMap['board'].boardId }">[삭제]</a>&nbsp;&nbsp;&nbsp;
	

</body>
</html>