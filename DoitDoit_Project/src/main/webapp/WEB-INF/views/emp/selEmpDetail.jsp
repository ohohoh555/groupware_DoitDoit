<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" type="text/css" href="./css/selEmp.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            	<form action="">
            		<div class="profile"></div>
            		<c:forEach items="${lists}" var="empVo">
            		<div class="inputUser">
            			<input type="text" value="${empVo.emp_id}">
            			<input type="text" value="${empVo.emp_name}">
            			<input type="text" value="${empVo.emp_email}">
            			<input type="text" value="${empVo.dept_no}">
            			<input type="text" value="${empVo.rank_no}">
            			<input type="text" value="${empVo.emp_regdate}">
            		</div>
            		<div class="inputAdd">
            			<input type="text" value="${empVo.emp_address}">
            			<input type="text" value="${empVo.emp_nfc}">
            		</div>
            		</c:forEach>
            		<div class="buttonArea"></div>
            	</form>
            </div>
        </main>
    </div>
</body>
</html>