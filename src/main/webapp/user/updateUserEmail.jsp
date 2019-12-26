<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이메일 변경</title>
<script>
	function updateEmail(){
		var form = $('#updateEmailForm').serialize();
		
		$.ajax({
			url : "updateUserEmail.do",
			data : form,
			dataType : "json",
			type : "post",
			success:function(result){ 
				location.href=result.location;
			},
			error:function(result, status, error){
				resultJson = result.responseJSON;
				$("#error").html(resultJson.email);
			}
		});
	}
</script>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
		<div>이메일 변경</div>
		<form id="updateEmailForm" >
			<input type="hidden" name="userId" value="${sessionScope.user.userId }"/>
			<label>이메일 주소</label>
			<input type="email" name="email"/>
			
			
			<input type="button" onclick="updateEmail();" value="변경" class="btn btn-default"/>
			<span id="error"></span>
		</form>
	</div>
</body>
</html>