<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈 화면</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
</head>

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