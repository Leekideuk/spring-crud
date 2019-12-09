<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
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
	<%@ include file="/header.jsp" %>
	<!-- 검색 시작 -->
	<div class="container">
	<form action="getBoardList.do" method="post">
		<table class="table">
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
	</div>
	<!-- 검색 종료 -->
	
	<div class="container">
	<table class="table table-hover table-condensed table-bordered" style="text-align: center;">
		<thead>
			<tr>
				<td width="10%">번호</td><td width="60%">제목</td><td width="10%">작성자</td><td width="10%">등록일</td><td width="10%">조회</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${boardList }" var="board">
				<tr>
					<td>${board.boardId }</td>
					<td align="left"><a href="getBoard.do?boardId=${board.boardId }">${board.title }</a></td>	
					<td>${board.userId }</td>
					<td><fmt:formatDate value="${board.regDate }" pattern="MM-dd"/></td>
					<td>${board.cnt }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<!-- 페이징 처리 -->
	<div style="text-align: center;">
	<nav>
  		<ul class="pagination">
  			<!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면 -->
  			<c:if test="${paging.curBlock > 1}">
    			<li><a href="#" onclick="paging('1')">[처음]</a></li>
    		</c:if>
    		<!-- 이전페이지 블록으도 이동 -->
			<c:if test="${paging.curBlock > 1 }">
				<li><a href="#" onclick="paging(${paging.prevBlockPage})">[이전]</a></li>
			</c:if>
    		<c:forEach var="num" begin="${paging.blockBegin }" end="${paging.blockEnd }">
			<!-- 현재 페이지면 하이퍼링크 제거 -->
				<c:choose>
					<c:when test="${num == paging.criteria.curPage }">
						<li><span style="color:red">${num }</span></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" onclick="paging(${num})">${num }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
    		<!-- 다음 블록 페이지로 이동 -->
			<c:if test="${paging.curBlock <= paging.totalBlock }">
				<li><a href="#" onclick="paging('${paging.nextBlockPage}')">[다음]</a></li>
			</c:if>
			<!-- 끝 페이지로 이동 -->
			<c:if test="${paging.criteria.curPage <= paging.totalPage }">
				<li><a href="#" onclick="paging('${paging.totalPage}')">[끝]</a></li>
			</c:if>
    	</ul>
	</nav>
	</div>
	${paging }
</body>
</html>