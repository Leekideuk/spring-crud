<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세</title>
</head>

<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-8 col-sm-8 col-xs-8">
			<ul class="list-inline" style="padding-top:5px;">
				<li><strong>${boardMap['board'].userId}</strong></li>
				<li><fmt:formatDate value="${boardMap['board'].regDate }" pattern="yyyy-MM-dd HH:mm"/></li>
				<li>조회 ${boardMap['board'].cnt }</li>
			</ul>
			<pre>${boardMap['board'].title }</pre>
			<pre>${boardMap['board'].content }</pre>
			
		
		
			<span>첨부</span>
			<c:forEach var="file" items="${boardMap['boardFileList']}" varStatus="status">
				<div><a href="fileDownload.do?fileId=${file.fileId }">${file.fileName}</a>${file.sizeToString()}</div>
			</c:forEach>       
			<ul class="list-inline navbar-right">
				<li><input type="button" class="btn btn-default" onclick="location.href='updateBoard.do?boardId=${boardMap['board'].boardId }'" value="수정"/></li>
				<li><input type="button" class="btn btn-default" onclick="location.href='deleteBoard.do?boardId=${boardMap['board'].boardId }'" value="삭제"/></li>
			</ul>
		</div>
	</div>

</body>
</html>