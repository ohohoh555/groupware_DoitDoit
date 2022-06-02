<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료게시판(사용자)</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="./css/jaryo/dragAndDrop.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
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
						<div>
							<div class="div1">
								<input type="button" class='menu btn btn-default' value='파일 업로드'
									onclick="slideDown()">
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
					</div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>

            </div>
        </main>
    </div>
</body>
</html>