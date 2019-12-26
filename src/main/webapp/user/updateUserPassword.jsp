<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 변경</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
		<div>비밀번호 변경</div>
		<form action="" method="post" >
			<input type="hidden" name="userId" value="${sessionScope.user.userId }"/>
			<label>비밀번호</label>
			<input type="password" name="password"/>
			<input type="submit" value="변경" class="btn btn-default"/>
		</form>
	</div>
</body>
</html>