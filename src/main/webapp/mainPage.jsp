<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>인덱스 페이지</title>
</head>
<body>
	<h1>${sessionScope.user }</h1>
	<h1>인덱스 페이지</h1>
	<h2><a href="login.do">로그인</a></h2>
	<h2><a href="logout.do">로그아웃</a></h2>
	<h2><a href="signup.do">회원가입</a></h2>
	<h2><a href="mypage.do">마이페이지</a></h2>
</body>
</html>