<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container" style="padding-top:10px;">
	<form action="login.do" method="post" class="form-horizontal">
		<div class="col-md-offset-4 col-sm-offset-4 col-xs-offset-4 col-md-4 col-sm-4 col-xs-4">
			<div class="form-group"	>
    			<input type="text" name="userId" id="userId" class="form-control" placeholder="아이디를 입력하세요.">
  			</div>
  			<div class="form-group">
	    		<input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력하세요.">
  			</div>
  			<div class="form-group" align="center">
      			<input type="submit" class="btn btn-default btn-block" value="로그인">
	  		</div>
	  		<div class="form-group" align="right">
	  			<a href="#">아이디&#183;비밀번호 찾기</a>
	  		</div>
  		</div>
	</form>
	</div>
	
	
</body>
</html>