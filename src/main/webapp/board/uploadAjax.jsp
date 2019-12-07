<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업로드 AJAX</title>
<style>
	.fileDrop {
		width:600px;
		height:200px;
		border: 1px dotted blue;
	}
	
	small {
		margin-left:3px;
		front-weight:bold;
		color:gray;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$(".fileDrop").on("dragenter dragover", function(event){
			event.preventDefault(); // 기본효과 막음. 파일 업로드 영역에 파일을 드래그 했을 때 내용이 바로 보여지는 기본효과를 막음.
		});
		// event : JQuery의 이벤트
		// originalEvent : javascript의 이벤트
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();	// 기본효과를 막음
			// 드래그된 파일의 정보
			var files = event.originalEvent.dataTransfer.files;
			// 첫번째 파일
			var file = files[0];
			// 콘솔에서 파일정보 확인
			console.log(file);
			
			// ajax로 전달할 폼 객체
			var formData = new FormData();	
			// 폼 객체에 파일 추가, append("변수명", 값)
			formData.append("file", file);
			$.ajax({
				type:"post",
				url:"uploadAjax.do",
				data: formData,
				dataType: "text",
				processData: false,	// true=> get, false=>post
				contentType: false,	// true=> application/x-www-form-urlencoded, false=>multipart/form-data
				success: function(data){
					data = JSON.parse(data);
					var str = "";
					// 이미지 파일이면 썸네일 이미지 출력
					if(checkImageType(data[0].realName)){
						str = "<div><a href='displayFile.do?fileName=" + data[0].realName + "'>'";
						str += "<img src='displayFile.do?fileName=" + data[0].realName + "'/></a>";
					}else{ // 일반파일이면 다운로드링크
						str = "<div><a href='displayFile.do?fileName=" + data[0].realName + "'>" + data[0].fileName + "</a>";
					}
					// 삭제버튼
					str += "<span data-src=" + data[0].realName + ">[삭제]</span></div>";
					$(".uploadedList").append(str);
				}
			});	
		});
		
		$(".uploadedList").on("click", "span", function(event){
			alert("이미지 삭제")
			var that = $(this);	// 여기서 this는 클릭한 span태그
			$.ajax({
				url : "deleteFile.do",
				type : "post",
				data : {fileName:$(this).attr("data-src")},	// json 방식
				dataType : "text",
				success : function(result){
					if(result == "deleted"){
						// 클릭한 span태그가 속한 div를 제거
						that.parent("div").remove();
					}
				}
			});
		});
	});
	
		
	// 이미지파일 형식 체크
	function checkImageType(fileName){
		// i : ignore case (대소문자 무관)
		var pattern = /jpg|gif|png|jpeg/i;	// 정규표현식
		return fileName.match(pattern);
	}
</script>
</head>
<body>
${voList }
123
<h2>AJAX File Upload</h2>
<!-- 파일을 업로드할 영역 -->
<div class="fileDrop"></div>
<!-- 업로드된 파일 목록 -->
<div class="uploadedList"></div>
</body>
</html>