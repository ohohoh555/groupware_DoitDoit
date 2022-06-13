<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
	<header>
		<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
		<sec:authentication property="principal" var="emp"/><input type="hidden" id="empN" value="${emp.emp_id}">
		<a href="${pageContext.request.contextPath}/comm/gohome.do" id="headerLogo"><img alt="로고" src="./img/Doit_Logo.png"></a>
		 <sec:authorize access="hasRole('ROLE_USER')">
		<img alt="" id="bell" src="./img/bell32.png" onclick="approWindowOn()">
		</sec:authorize>
		<a href="${pageContext.request.contextPath}/comm/logout.do"><img alt="로그아웃버튼" src="./img/logout32.png" onclick="disconn()"></a>
	</header>
</body>
