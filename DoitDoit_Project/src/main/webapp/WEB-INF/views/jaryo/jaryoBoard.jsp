<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료게시판(사용자)</title>
<%@include file="../comm/setting.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/jaryo/dragAndDrop.css">
<script type="text/javascript" src="./js/jaryo/Map.js"></script>
<script type="text/javascript" src="./js/jaryo/dragAndDrop.js"></script>
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
						<h3>&lt;&lt;자료게시판&gt;&gt;</h3>
						<sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal" var="principal"/>
					        <input type="text" value="${principal.emp_id}" id="emp_id" name="emp_id"> 
					        <input type="text" value="${principal.emp_name}" id="emp_name" name="emp_name">
 			           </sec:authorize> 
						<div>
							<div class="div1">
								<input type="button" class='menu btn btn-default' value='파일 업로드' onclick="slideDown()">
								<div id="fileUpload" class="dragAndDropDiv hide10">
									<table id='fileTable' class='fileList table-bordered'>
										<tr>
											<td id='tabFileName'>파일명</td>
											<td id='tabFileSize'>사이즈</td>
											<td id='tabFileDel'>삭제</td>
										</tr>
									</table>
								</div>
							</div> <!-- div1 끝 -->
							<div class="div2">
								<input type="button" class="btn btn-default" value="등록" onclick="submitFile()">
							</div>
						</div>
						<div>
							<table id="jaryoTable" class="stripe">
								<thead>
									<tr>
										<th>번호</th>
										<th>파일명</th>
										<th>크기</th>
										<th>등록일자</th>
									</tr>
								</thead>
							</table>
						</div>
					</div><!-- rContent-full 끝 -->
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>

            </div>
        </main>
    </div>
    
    
<script type="text/javascript">
$(document).ready(function(){
	$("#jaryoTable").DataTable({
		lengthMenu: [ 5, 10, 15],
	     displayLength: 5,
	     lengthChange: false,
		  	"language": {
	            "emptyTable": "자료가 없어요.",
	            "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
	            "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
	            "infoEmpty": "자료 없음",   // info부분이 비었을 경우 
	            "infoFiltered": "( _MAX_건의 게시글에서 필터링됨 )",
	            "search": "검색: ",
	            "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
	            "loadingRecords": "로딩중...",
	            "processing":     "잠시만 기다려 주세요...",
	            "paginate": {
	                "next": "다음",
	                "previous": "이전"
	            }
	        }, 
	        
	      
	        ajax:{
	        	url:"./selJaryoAllUser.do",
	        	type:"post",
	        	dataSrc:""
	        },
	        columns:[
	        	{data:"eboard_no"},
	        	{data:"flist_originname"},
	        	{data:"flist_size"},
	        	{data:"eboard_regdate"},
	        ]
	});
});

</script>   
</body>
</html>