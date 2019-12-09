<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
	<form:form commandName="user" action="signup.do" method="post" class="form-horizontal">
		<div class="col-md-offset-4 col-sm-offset-4 col-xs-offset-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group"	>
				<p style="padding-top: 10px">아이디</p>
				<form:input path="userId" class="form-control" placeholder="아이디를 입력하세요."/><form:errors path="userId"/>
			</div>
			<div class="form-group"	>
				<p>비밀번호</p>
				<form:input path="password" class="form-control" placeholder="비밀번호를 입력하세요"/><form:errors path="password"/>
			</div>
			<div class="form-group"	>
				<p>이메일</p>
				<form:input path="email" class="form-control" placeholder="이메일을 입력하세요."/><form:errors path="email"/>
			</div>
			<div class="form-group"	>
				<p>핸드폰</p>
				<form:input path="phone" class="form-control" placeholder="핸드폰 번호를 입력하세요."/>
			</div>
			<div class="form-group" align="center">
      			<input type="submit" class="btn btn-default btn-block" value="회원가입">
	  		</div>
		</div>
	</form:form>
	</div>
</body>
</html>