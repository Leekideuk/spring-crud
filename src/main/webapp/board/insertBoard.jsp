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
	// 파일 리스트
	var fileList = new Array();
	
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
			selectFile(files);
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
				if($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0){
					// 확장자 체크
					alert(fileName+" 등록 불가 확장자");
					continue;
				}
				
				// 파일 배열에 넣기
				fileList[fileIndex] = files[i];
				// 업로드 파일 목록 생성
				addFileList(fileIndex, fileName);
				// 파일번호증가
				fileIndex++;
			}
		}
	}
	
	// 업로드 파일 목록 생성
	function addFileList(fIndex, fileName){
		var html = "";
		html += "<tr id='fileTr_" + fIndex + "'>";
		html += "	<td class='left' colspan='2'>";
		html +=			fileName + "<a href='#' onclick='deleteFile(" + fIndex + "); return false;' class='btn small bg_02'>삭제</a>";
		html += "	</td>";
		html += "</tr>";
		
		$('#fileZone').append(html);
	}
	
	// 업로드 파일 삭제
	function deleteFile(fIndex){
		// 파일 배열에서 삭제
		delete fileList[fIndex];
		// 업로드 파일 테이블 목록에서 삭제
		$("#fileTr_" + fIndex).remove();
	}
	
	// 파일 등록
	function uploadFile(){
		if(confirm("등록 하시겠습니까?")){
			var form = $('#uploadForm')[0];
			var formData = new FormData(form);
			for(var i = 0 ; i < fileList.length; i++){
				formData.append('uploadFile', fileList[i]);
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
	<div class="container" style="padding-top: 10px;">
	<form id="uploadForm" action="insertBoard.do" method="post" enctype="multipart/form-data" class="form-horizontal">
		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-8 col-sm-8 col-xs-8">
			<input name="userId" type="hidden" value="${pageContext.request.userPrincipal.name}"/>
			<div class="form-group"	>
				<input type="text" name="title" class="form-control" placeholder="제목을 입력하세요."/>
			</div>
			<div class="form-group">
				<textarea name="content" rows="15" class="form-control" placeholder="내용을 입력하세요"></textarea>
			</div>
			<div id="fileZone">
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