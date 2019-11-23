<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mypage</title>
</head>
<body>
	<h1>mypage</h1>
	<hr>
	<form action="updateUser.do" method="post">
		<table border="1">
			<tr>
				<td>아이디</td>
				<td>${sessionScope.user.userId }<input type="hidden" name="userId" value="${user.userId }"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="email" name="email" value="${user.email }"></td>
			</tr>
			<tr>
				<td>핸드폰</td>
				<td><input type="tel" name="phone1" value="${user.phone1 }"> - 
				<input type="tel" name="phone2" value="${user.phone2 }"> - 
				<input type="tel" name="phone3" value="${user.phone3 }"></td>
			</tr>
			<tr>
				<td>가입일</td>
				<td>${user.regDate }</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="회원정보수정"><input type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
	<h2><a href="main.do">메인페이지</a></h2>
	<h2><a href="deleteUser.do">회원탈퇴</a></h2>
</body>
</html>