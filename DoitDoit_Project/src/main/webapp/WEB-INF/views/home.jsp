<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈 화면</title>
</head>
<%@include file="./comm/setting.jsp" %>
<body>
	<div id="container">
        <%@include file="./comm/nav.jsp" %>
        <main>
            <%@include file="./comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="calendar" class="rContent-half"></div>
                    <div id="resent" class="rContent-half"></div>
<!-- 					<div class="rContent-full"></div> -->
<!-- 					<div class="rContent-normal-top"></div> -->
<!-- 					<div class="rContent-normal-bottom"></div> -->
                </div>
            <%@include file="./comm/aside.jsp" %>    
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal" var="info"/> ${info.emp_id} <br>
            	직급 : <sec:authentication property="principal" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="principal" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="principal" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>