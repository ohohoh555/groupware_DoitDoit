<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<%@include file="../comm/setting.jsp" %>
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
					<h3>글수정</h3>
 			   		<form id="modifyFrm" method="post" onsubmit="return modifyAction()" action="./modifyFrm.do">
 			           <sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal" var="principal"/>
					        <input type="hidden" value="${principal.emp_id}" id="emp_id" name="emp_id"> 
					        <input type="hidden" value="${principal.emp_name}" id="emp_name" name="emp_name">
 			           </sec:authorize> 
 			           		<input type="hidden" value="${eVo.eboard_content}" id="originContent" >
 			           		<input type="text" name="cgory_no" value="${eVo.cgory_no}">
 			           		<input type="text" name="eboard_no" value="${eVo.eboard_no}">
 			           		<input type="text" name="cald_id" value="${eVo.cald_id}">
						<table class="table table-bordered" id="modifyTbl">
							<tbody>
								<tr>
									<td>분류</td>
									<td>
										<select class="form-control" style="width: 100px;" disabled>
											<c:choose>
											<c:when test="${eVo.cgory_no =='101'}"><option value="101">일반</option></c:when>	
											<c:when test="${eVo.cgory_no =='102'}"><option value="102">필독</option></c:when>
											<c:when test="${eVo.cgory_no =='103'}"><option value="103">인사</option></c:when>
											<c:when test="${eVo.cgory_no =='302'}"><option value="302">일정</option></c:when>
											</c:choose>
										</select>
										
									</td>
								</tr>
								<c:if test="${eVo.cgory_no =='302'}">
								<tr>
									<td>일시</td>
									<td>
										<div style="float: left;">
										<label>시작:</label>
										<input type='datetime-local' class='form-control' id="cald_start" name="cald_start" style='width: 200px;'>
										</div>

										<div style="margin-left: 10px;">
										<label>종료:</label>
										<input type='datetime-local' class='form-control' id="cald_end" name="cald_end" style='width: 200px;'>
										</div>
									</td>
								</tr>
								</c:if>
								<tr>
									<td>제목</td>
									<td><input type="text" id="eboard_title" class="form-control" value="${eVo.eboard_title}" disabled></td>
								</tr>
								<c:choose>
								<c:when test="${eVo.cgory_no =='302'}">
								<tr>
									<td>내용</td>
									<td>
									<textarea name ="cald_content" id="cald_content" class="form-control" style="width: 740px; height: 250px;">${eVo.eboard_content}</textarea>
									</td>
								</tr>
								</c:when>
								<c:otherwise>
								<tr>
									<td>내용</td>
									<td>
										<textarea name ="eboard_content" id="eboard_content">${eVo.eboard_content}</textarea>
									</td>
								</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3">
										<input type="button" class="btn btn-default" value="초기화" onclick="resetOrigin()">
										<input type="submit" class="btn btn-default" value="수정완료">
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

function resetOrigin(){
	var con = confirm("원본글로 복구하시겠습니까?? (작업중인 내용이 모두 사라집니다.)");
	var originContent=document.getElementById("originContent").value;
	if(con){
		CKEDITOR.instances.content.setData(originContent);
	}
}

function modifyAction(){
	console.log("modifyAction 작동");
	var modifyFrm = document.getElementById("#modifyFrm");
	
	var cald_start= $("#cald_start").val();
	var cald_end=$("#cald_end").val();
	
	if(cald_start==""){
		alert("수정할 일정시작을 입력해주세요");
		return false;
	}else if(cald_end==""){
		alert("수정할 일정종료를 입력해주세요");
		return false;
	}else{
		modifyFrm.submit();
	}
	
	


}

</script>
</body>
</html>