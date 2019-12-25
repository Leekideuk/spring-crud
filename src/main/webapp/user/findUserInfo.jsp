<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디 / 비밀번호 찾기</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
		<div>아이디 / 비밀번호 찾기</div>
		<form action="/findUserInfo.do" method="post" >
			<label>이메일 주소</label>
			<input type="email" name="email"/>
			<input type="submit" value="전송" class="btn btn-default"/>
		</form>
	</div>
	
</body>
</html>