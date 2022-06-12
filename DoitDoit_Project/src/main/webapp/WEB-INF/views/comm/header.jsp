<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
	<header>
		<sec:authentication property="principal" var="emp"/><input type="hidden" id="empN" value="${emp.emp_id}">
		<a href="./gohome.do" id="headerLogo"><img alt="로고" src="./img/Doit_Logo.png"></a>
		<a href="#"><img alt="" id="bell" src="./img/bell32.png" onclick="approWindowOn()"></a>
		<a href="./logout.do"><img alt="로그아웃버튼" src="./img/logout32.png" onclick="disconn()"></a>
	</header>
</body>
