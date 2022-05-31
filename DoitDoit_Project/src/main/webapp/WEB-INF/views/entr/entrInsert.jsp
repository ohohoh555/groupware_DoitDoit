<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 페이지</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="./dist/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
					<h3>글쓰기</h3>
 			           <form id="insertFrm" >
 			           <sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal" var="principal"/>
					        <input type="text" value="${principal.emp_id}" id="emp_id" name="emp_id"> 
					        <input type="text" value="${principal.emp_name}" id="emp_name" name="emp_name">
 			           </sec:authorize> 
						<table class="table table-bordered" id="insertTbl">
							<tbody>
								<tr>
									<td>분류</td>
									<td>
										<select name="cgory_no" class="form-control" style="width: 100px;" onchange="selectCgory(this.value)">
											<option value="101">일반</option>
											<option value="102">필독</option>
											<option value="103">인사</option>
											<option value="302">일정</option>
										</select>
										<div id="cgoryEtc"></div>
									</td>
								</tr>
								<tr>
									<td>제목</td>
									<td><input type="text" name="eboard_title" id="title" class="form-control"></td>
								</tr>
								<tr>
									<td>내용</td>
									<td>
										<textarea name ="eboard_content" id="content"></textarea>
									</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3">
										<input type="reset" class="btn btn-default" value="초기화" onclick="resetContent()">
										<input type="button" class="btn btn-default" value="등록" onclick="insertAction()" >
										<input type="button" class="btn btn-default" value="돌아가기" onclick="javascript:history.back(-1)">
									</td>
								</tr>
							</tfoot>
						</table>
						</form>
					</div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>

            </div>
        </main>
    </div>


<script type="text/javascript">
CKEDITOR.replace( 'eboard_content' ,{
	//language: 'en', //에디터의 언어 설정
 	uiColor: '#9AB8F3', // 에디터 색상 변경
	extraPlugins: 'editorplaceholder', 
    editorplaceholder: // 에디터 화면에 띄운 글귀
    '여기에 글을 입력하거나 파일을 드래그해주세요...', 
	filebrowserUploadUrl: "fileupload.do",
	uploadUrl:"fileupload.do",
     width : '750px',  // 입력창의 넓이, 넓이는 config.js 에서 % 로 제어
     height : '350px' 

	}
);

CKEDITOR.on('dialogDefinition', function( ev ){
	var dialog = ev.data.definition.dialog;
	var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
  
    switch (dialogName) {
        case 'image': 
        	
			dialogDefinition.removeContents('advanced'); // 자세히탭 제거
			dialogDefinition.removeContents('Link'); // 링크탭 제거
            
            dialog.on('show', function (obj) {
        		this.selectPage('Upload'); //업로드탭으로 시작
            });
			
            break;
    }
});

function resetContent(){
	CKEDITOR.instances.content.setData("");
}

function insertAction(){
	var insertFrm = document.getElementById("insertFrm");
	insertFrm.acton ="./insertFrm.do";
	
	
	var title = document.getElementById("title").value;
	var content = CKEDITOR.instances.content.getData();
	console.log(title, content, title.length);
	
	if(title==""){
		alert("제목을 입력해주세요");
		return false;
	}else if(title.length < 2){
		alert("제목은 2글자 이상 입력해주세요");
		return false;
	}else if(content ==""){
		alert("내용을 입력해주세요");
		return false;
	}else{
		alert("성공");
	}
}


function selectCgory(val){
	console.log("selectCgory 작동", val);
	
	var html = "";
	html +="<tr id='trDate'><td>날짜</td>";
	html +="<td style='float: left;'>시작:<input type='date' class='form-control' style='width: 150px;'>";
	html +="<input type='time' class='form-control' style='width: 150px;'> ~ ";
	html +="종료:<input type='date' class='form-control' style='width: 150px;'>";
	html +="<input type='time' class='form-control' style='width: 150px;'> ~ ";
	html +="</td></tr>";
	
	if(val == 101){
		$("#cgoryEtc").empty();
		$("#trDate").remove();
	}else if(val == 102){
		$("#cgoryEtc").empty();
		$("#trDate").remove();
		$("#cgoryEtc").text("필독게시물은 게시판 상단에 별도 표기됩니다.");
	}else if(val == 103){
		$("#cgoryEtc").empty();
		$("#trDate").remove();
	}else{
		$("#cgoryEtc").empty();
		$("#trDate").remove();
		$("#insertTbl >tbody > tr").eq(0).after(html);
	}
}
</script>
</body>
</html>