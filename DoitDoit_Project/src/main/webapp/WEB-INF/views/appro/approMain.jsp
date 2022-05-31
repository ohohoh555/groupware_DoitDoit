<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문서함 메인 화면</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
<style type="text/css">
.signImg1{
	width: 200px;
    height: 200px;
  	background-image: url(./img/sign/sign01.png);
    background-size: 60%;
    background-repeat: no-repeat;
    background-position: center;
    border:2px solid  #6667AB;
    border-radius: 100%;
    margin: 50px 0 0 50px;
}
.signImg2{
	width: 200px;
    height: 200px;
  	background-image: url(./img/sign/sign02.png);
    background-size: 60%;
    background-repeat: no-repeat;
    background-position: center;
    border:2px solid  #6667AB;
    border-radius: 100%;
    margin: 50px 50px;
    
}
</style>
</head>

<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                    <div class="rContent-normal-top" style="width: 600px; height: 100px; margin-top: 10%;">
                    	<h2 style="text-align: center;">결재예정문서조회</h2>
                    	<input type="hidden" id="empId" value="${emp_id}">
                    </div>
                    <!-- 
                    <div class="rContent-normal-bottom" style="width: 600px; height: 500px;">
	                    <div style="float: left; cursor: pointer;" onclick="location.href='./signpadForm.do';"  >
		                    <div  class="signImg1">
		                    </div>
		                    <div style="margin-left: 90px; margin-top: 50px;" >
		                    <h2 style="font-style: #6667AB;">서명생성</h2>
		                    </div>
	                    </div>
	                    
	                    <div style="float: right; cursor: pointer;" onclick="location.href='./signModify.do';">
		                    <div  class="signImg2">
		                    </div>
		                    <div style="margin-left: 90px;">
		                    <h2 style="font-style: #6667AB;">서명관리</h2>
		                    </div>
	                    </div>
                    </div>  -->
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
         
            </div>
        </main>
    </div>
</body>
<script type="text/javascript">
window.onload = function approDocList(){
	console.log("아작스 실행전");
	var empId = document.getElementById("empId").value;
	console.log(empId);
	$.ajax({
		url : "./docAllList.do",
		type : "GET",
		data : "emp_id="+empId,
		async : true,
		success : function(data){
			console.log("ajax성공");
			console.log(data);
			console.log(data.length);
			/* 
			for(let i=0; i<data.length; i++){
			console.log(data[i].APPRO_NO); //문서번호
			console.log(data[i].APPRO_EMPNAME); //결재자
			console.log(data[i].APPRO_TITLE); //문서제목
			console.log(data[i].APPRO_REGDATE); //문서 기안일
			console.log(data[i].APPRO_STATUS); //결재대기
			console.log(data[i].APPRO_TYPE); //문서상태 (상신,송신)
			
				
			} */
		//	[[{APPRO_LINE_NO=2, APPRO_EMPNAME=이정민, EMP_ID=2022052800, APPRO_TITLE=더미데이터 값 입력테스트, DOC_FORM_NO=DOC0001, APPRO_STATUS_NO=1, APPRO_NO=DOIT-2022052502, APPRO_REGDATE=2022-05-25 08:44:16.0, APPRO_STATUS=결재대기, APPRO_TYPE=상신, APPRO_CONTENT=oracle.sql.CLOB@331eaf3}]]
		}
	});
}
</script>
</html>