<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판관리(자료)</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/entr/jaryoAdmin.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN_BOARD','ROLE_ADMIN_INSA')">
                <div id="rContent">
					<div class="rContent-full">
						<ul class="nav nav-tabs">
							<li id="navGongji"><a href="./entrBoardAdmin.do">공지게시판</a></li>
							<li class="active" id="navJaryo"><a onclick="adminJaryo()">자료게시판</a></li>
						</ul>
						<br>
					<input type='button' class='btn btn-success' value='숨김/보임' onclick='ChangJaryoDel()'>
					<input type='button' class='btn btn-primary' value='삭제' onclick='DeleteJaryo()'>
					<div id="jaryoResult"></div>
					</div><!-- rContent-full 끝 -->
                </div> <!-- rContent 끝 -->
            </sec:authorize>
            </div><!-- content 끝 -->
        </main>
    </div>
 
</body>
</html>