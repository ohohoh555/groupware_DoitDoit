<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자서명 메인 화면</title>
<%@include file="../comm/setting.jsp" %>
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
                    	<h2 style="text-align: center;">전자서명 서명관리</h2>
                    </div>
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
                    </div> 
                    
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
          <%--   <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize> --%>
            </div>
        </main>
    </div>
</body>
</html>