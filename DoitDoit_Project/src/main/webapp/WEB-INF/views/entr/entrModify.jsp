<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="./dist/ckeditor/ckeditor.js"></script>
</head>
<body>
${eVo}
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
					<h3>글쓰기</h3>
 			           <form id="insertFrm" onsubmit="return insertAction()" method="post">
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
										<select name="cgory_no" class="form-control" style="width: 100px;" onchange="selectCgory(this.value)" >
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
									<td><input type="text" name="eboard_title" id="title" class="form-control" value="${eVo.eboard_title}"></td>
								</tr>
								<tr>
									<td>내용</td>
									<td>
										<textarea name ="eboard_content" id="content">${eVo.eboard_content}</textarea>
									</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3">
										<input type="button" class="btn btn-default" value="초기화" >
										<input type="submit" class="btn btn-default" value="수정완료" >
										<input type="button" class="btn btn-default" value="취소" onclick="cancleModify()">
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

function cancleModify(){
	console.log("cancleModify 수정취소");
	var con = confirm("글 수정을 취소하시겠습니까? (작업중인 내용이 모두 사라집니다.)");
	if(con){
		history.back();
	}
}

</script>
</body>
</html>