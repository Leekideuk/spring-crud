<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세</title>
</head>


<body>
	<h1>글 상세</h1>
	<hr>
	<table border="1">
		<tr>
			<td>제목</td>
			<td>${boardMap['board'].title }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${boardMap['board'].userId}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td height="100">${boardMap['board'].content }</td>
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
			<td>
				<c:forEach var="file" items="${boardMap['boardFileList']}" varStatus="status">
					<a href="fileDownload.do?fileId=${file.fileId }">${file.fileName}</a>${file.sizeToString()} <br/>
				</c:forEach>       
			</td>
	</table>
	<hr>
	<a href="insertBoard.do">새 글 등록</a>&nbsp;&nbsp;&nbsp;
	<a href="updateBoard.do?boardId=${boardMap['board'].boardId }">글 수정</a>&nbsp;&nbsp;&nbsp;
	<a href="deleteBoard.do?boardId=${boardMap['board'].boardId }">글 삭제</a>&nbsp;&nbsp;&nbsp;
	<a href="getBoardList.do">글 목록</a>

</body>
</html>