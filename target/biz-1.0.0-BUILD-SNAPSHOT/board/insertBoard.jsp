<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 등록</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
	//파일 리스트 번호
	var fileIndex = 0;
	// 등록할 전체 파일 사이즈
	var totalFileSize = 0;
	// 파일 리스트
	var fileList = new Array();
	// 파일 사이즈 리스트
	var fileSizeList = new Array();
	// 등록 가능한 파일 사이즈 MB
	var uploadSize = 50;
	// 등록 가능한 총 파일 사이즈 MB
	var maxUploadSize = 500;
	
	$(function (){
		fileDropDown();
	});
	
	function fileDropDown(){
		var dropZone = $("#dropZone");
		// Drag기능
		dropZone.on('dragenter dragover', function(e){
			e.stopPropagation();
			e.preventDefault();
			dropZone.css('background-color','#E3F2FC');
		});
		dropZone.on('dragleave',function(e){
            e.stopPropagation();
            e.preventDefault();
            dropZone.css('background-color','#FFFFFF');
        });
		
		dropZone.on('drop', function(e){
			e.preventDefault();
			dropZone.css('background-color','#FFFFFF');
			
			var files = e.originalEvent.dataTransfer.files;
		//	if(files != null){
		//		if(files.length < 1){
		//			alert("파일 업로드 불가");
		//			return
		//		}
				selectFile(files);
		//	}else{ 
		//		alert("ERROR");
		//	}
		});
	}	
	
	function selectFile(files){
		// 다중파일 등록
		if(files != null){
			for(var i = 0; i < files.length; i++){
				// 파일이름
				var fileName = files[i].name;
				var fileNameArr = fileName.split("\.");
				// 확장자
				var ext = fileNameArr[fileNameArr.length -1];
				// 파일 사이즈(MB)
				var fileSize = ((files[i].size / 1024) / 1024);
				
				if($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0){
					// 확장자 체크
					alert("등록 불가 확장자");
					break;
				}else if(fileSize > uploadSize){
					// 파일 사이즈 체크
					alert("용량 초과\n 업로드 가능 용량 : " + uploadSize + " MB");
				}else{
				
					// 전체 파일 사이즈
					totalFileSize += fileSize;
					// 파일 배열에 넣기
					fileList[fileIndex] = files[i];
					// 파일 사이즈 배열에 넣기
					fileSizeList[fileSize] = fileSize;
					// 업로드 파일 목록 생성
					addFileList(fileIndex, fileName, fileSize);
					// 파일번호증가
					fileIndex++;
				}
			}
		}
	}
	
	// 업로드 파일 목록 생성
	function addFileList(fIndex, fileName, fileSize){
		var html = "";
		html += "<tr id='fileTr_" + fIndex + "'>";
		html += "	<td class='left' colspan='2'>";
		html +=			fileName + " / " + fileSize + "MB" + "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>";
		html += "	</td>";
		html += "</tr>";
		
		$('#fileTableTbody').append(html);
	}
	
	// 업로드 파일 삭제
	function deleteFile(fIndex){
		// 전체 파일 사이즈 수정
		totalFileSize -= fileSizeList[fIndex];
		// 파일 배열에서 삭제
		delete fileList[fIndex];
		// 파일 사이즈 배열 삭제
		delete fileSizeList[fIndex];
		// 업로드 파일 테이블 목록에서 삭제
		$("#fileTr_" + fIndex).remove();
	}
	
	// 파일 등록
	function uploadFile(){
		// 등록할 파일 리스트
		var uploadFileList = Object.keys(fileList);
		// 용량을 500MB를 넘을 경우 업로드 불가
		if(totalFileSize > maxUploadSize){
			alert("용량 초과");
			return;
		}
		
		if(confirm("등록 하시겠습니까?")){
			var form = $('#uploadForm')[0];
			var formData = new FormData(form);
			for(var i = 0 ; i < uploadFileList.length; i++){
				formData.append('uploadFile', fileList[uploadFileList[i]]);
			}
			
			$.ajax({
				url : "insertBoard.do",
				data : formData,
				dataType : 'json',
				type : "post",
				enctype : "multipart/form-data",
				processData : false,
				contentType : false,
				cache: false,
				success:function(result){
					location.href=result.location
				},
				error:function(result, status, error){
					$('input').next('span').remove();
					$('textarea').next('span').remove();
					for(var key in result.responseJSON){
						$('input[name=' + key + ']').after('<span class="error"><br>' + result.responseJSON[key] + '</span>');
						$('textarea[name=' + key + ']').after('<span class="error"><br>' + result.responseJSON[key] + '</span>');
					}
				}
			});
		}
	}
	
</script>

</head>
<body>
	<%@ include file="/header.jsp" %>
	<div class="container">
	<form id="uploadForm" action="insertBoard.do" method="post" enctype="multipart/form-data" class="form-horizontal">
		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-8 col-sm-8 col-xs-8">
			<input name="userId" type="hidden" value="${sessionScope.user.userId }"/>
			<div class="form-group"	>
				<input type="text" name="title" class="form-control" placeholder="제목을 입력하세요."/>
			</div>
			<div class="form-group">
				<textarea name="content" rows="15" cols="50" class="form-control" placeholder="내용을 입력하세요"></textarea>
			</div>
			<div id="fileTableTbody">
				<div id="dropZone" style="height:100px;">
						파일을 드래그 하세요
				</div>
			</div>
			<div class="form-group" align="center">
				<input type="button" value="등록" class="btn btn-default btn-block" onclick="uploadFile(); return false;"/>
			</div>
		</div>
	</form>
	</div>
	

	
	
</body>
</html>