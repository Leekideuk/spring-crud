<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<style>
	body{
    	padding-top: 50px;
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(function(){
	$(".nav li").click(function(e){
		$(".nav li").removeClass("active"); 
		$(this).addClass("active");
	});	
});
</script>

</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
  		<div class="container-fluid">
    		<div class="navbar-header">
      			<a class="navbar-brand" href="#">WebSiteName</a>
    		</div>
    		<ul class="nav navbar-nav">
      			<li><a href="getBoardList.do">게시판</a></li>
      			<li><a href="insertBoard.do">글쓰기</a></li>
    		</ul>
    		<ul class="nav navbar-nav navbar-right">
    			<c:if test="${sessionScope.user == null }">
      			<li><a href="signup.do">회원가입</a></li>
      			<li><a href="login.do">로그인</a></li>
      			</c:if>
      			<c:if test="${sessionScope.user != null }">
      			<li><a href="logout.do">로그아웃</a></li>
      			</c:if>
    		</ul>
  		</div>
	</nav>
	<c:if test="${sessionScope.user != null }">
	<h2 align="right" style="padding-right:10px"><a href="mypage.do">${sessionScope.user.userId }</a></h2>
	</c:if>
</body>
</html>