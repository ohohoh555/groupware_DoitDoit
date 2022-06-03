<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정관리</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="./dist/fullcalendar-5.10.2/lib/main.css" />
<link rel="stylesheet" href="./dist/fullcalendar-5.10.2/lib/main.min2.css" />
<link rel="stylesheet" href="./dist/datetimepicker/jquery.datetimepicker.css">
<script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.js"></script>
<script src="https://unpkg.com/tippy.js@6"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./dist/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript" src="./dist/fullcalendar-5.10.2/lib/main.min2.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="./js/cald/Calendar.js"></script>
<script type="text/javascript" src="./js/cald/datatime.js"></script>
<style type="text/css">
	.fc-license-message{
		display: none;
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
					<div class="rContent-normal-top">
						<form method="post" id="frm">
							<div>
								<b>일정명 : </b>
								<input type="text" id="title" name="eboard_title">
								<b>내용 : </b>
								<input type="text" id="content2" name="eboard_content">
							</div>
							
							<div style="margin-top: 10px;">
								<b>일정 시작일 : </b>
								<input type="text" name="cald_start" id="datetimepicker1" readonly="readonly" ondblclick="return false">
								<b>일정 종료일 : </b>
								<input type="text" name="cald_end" id="datetimepicker2" readonly="readonly">
								<b>색상 : </b>
								<input type="color" name="cald_color">
							</div>
							<div>
								<input type="button" onclick="insertAjax()" value="전송">
							</div>
						</form>
					</div>
					<div id="calendar" class="rContent-normal-bottom"></div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>