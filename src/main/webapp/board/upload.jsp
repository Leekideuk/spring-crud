<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일 업로드</title>
<style>
	iframe {
		width: 600px;
		height: 100px;
		border: 1px;
		border-style: solid;
	}
</style>
</head>
<body>

<!-- target을 지정한 곳으로 form data가 이동함. -->
<form id="form1" target="iframePhoto" action="upload.do" method="post" enctype="multipart/form-data">
	<input type="file" name="file" multiple="multiple"/>
	<input type="text" name="str"/>
	<input type="submit" value="업로드"/>
</form>
<!-- form data가 이곳으로 이동 -->
<iframe name="iframePhoto"></iframe>
<script>
	function addFilePath(msg){
		console.log(msg); // 파일명 콘솔 출력
		document.getElementById("form1").reset(); // iframe에 업로드 결과를 출력 후 form에 저장된 데이터 초기화
	}
</script>

</body>
</html>