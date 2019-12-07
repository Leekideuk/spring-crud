<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 리스트</title>
<script>
function paging(page){
	location.href="${path}/getBoardList.do?curPage="+page+"&searchCondition=${searchCondition}" + "&searchKeyword=${searchKeyword}"
}
</script>

</head>
<body>
	<h1>게시판 리스트</h1>
	<!-- 검색 시작 -->
	<form action="getBoardList.do" method="post">
		<table border="1">
			<tr>
			 <td align="right">
			 	<select name="searchCondition">
			 	<c:forEach items="${conditionMap }" var="option">
			 		<option value="${option.value }" <c:if test="${option.value == searchCondition }">selected</c:if>> ${option.key }
				</c:forEach>
			 	</select>
			 	<input name="searchKeyword" type="text" value="${searchKeyword }"/>
			 	<input type="submit" value="검색"/>
			 </td>
			</tr>
		</table>
	</form> 
	<!-- 검색 종료 -->
	
	<table border="1">
		<tr>
			<th>no</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		
		<c:forEach items="${boardList }" var="board">
			<tr>
				<td>${board.boardId }</td>
				<td align="left"><a href="getBoard.do?boardId=${board.boardId }">${board.title }</a></td>	
				<td>${board.userId }</td>
				<td><fmt:formatDate value="${board.regDate }" pattern="yyyy-MM-dd"/></td>
				<td>${board.cnt }</td>
			</tr>
		</c:forEach>
	</table>
	<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 -->
	<c:if test="${paging.curBlock > 1}">
		<a href="#" onclick="paging('1')">[처음]</a>
	</c:if>
	<!-- 이전페이지 블록으도 이동 -->
	<c:if test="${paging.curBlock > 1 }">
		<a href="#" onclick="paging(${paging.prevBlockPage})">[이전]</a>
	</c:if>
	<c:forEach var="num" begin="${paging.blockBegin }" end="${paging.blockEnd }">
		<!-- 현재 페이지면 하이퍼링크 제거 -->
		<c:choose>
			<c:when test="${num == paging.criteria.curPage }">
				<span style="color:red">${num }</span>&nbsp;
			</c:when>
			<c:otherwise>
				<a href="#" onclick="paging(${num})">${num }</a>&nbsp;
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${paging.curBlock <= paging.totalBlock }">
		<a href="#" onclick="paging('${paging.nextBlockPage}')">[다음]</a>
	</c:if>
	<!-- 끝 페이지로 이동 -->
	<c:if test="${paging.criteria.curPage <= paging.totalPage }">
		<a href="#" onclick="paging('${paging.totalPage}')">[끝]</a>
	</c:if>
	<br>
	${paging }
</body>
</html>