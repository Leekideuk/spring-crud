<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이페이지</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
	<form action="updateUser.do" method="post" class="form-horizontal">
		<div class="col-md-offset-4 col-sm-offset-4 col-xs-offset-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group"	>
				<p>아이디</p>
				<span class="form-control">${pageContext.request.userPrincipal.name}</span>
				<input type="hidden" name="userId" value="${pageContext.request.userPrincipal.name}">
			</div>
			<div class="form-group"	>
				<p>이메일</p>
				<span class="form-control">${requestScope.user.email }</span>
			</div>
			<div class="form-group"	>
				<p>핸드폰</p>
				<input type="tel" name="phone" value="${user.phone }" class="form-control">
			</div>
			<div class="form-group"	>
				<p>가입일</p>
				<span class="form-control"><fmt:formatDate value="${user.regDate }" pattern="yyyy-MM-dd HH:mm"/></span>
			</div>
			<div class="form-group" align="center">
				<input type="submit" class="btn btn-default" value="수정">
				<input type="reset" class="btn btn-default" value="취소">
				<input type="button" class="btn btn-default" onclick="location.href='deleteUser.do'" value="탈퇴"/>
			</div>
		</div>
	</form>
	</div>
	
	<div class="container" align="center">
		<a href="updateUserEmail.do">[이메일 변경] </a>
		<a href="updateUserPassword.do">[비밀번호 변경]</a>
	</div>
	
</body>
</html>