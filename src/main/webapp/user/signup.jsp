<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
	
	<h1>회원가입</h1>
	<hr>
	<form:form commandName="user" action="signup.do" method="post">
		<table border="1">
			<tr>
				<td>아이디</td>
				<td><form:input path="userId"/><form:errors path="userId"/></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><form:input path="password"/><form:errors path="password"/></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><form:input path="email"/><form:errors path="email"/></td>
			</tr>
			<tr>
				<td>핸드폰</td>
				<td><form:input path="phone1"/>-<form:input path="phone2"/>-<form:input path="phone3"/></td>
				
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="회원가입"></td>
			</tr>
		</table>
	</form:form>
	
</body>
</html>