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
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./dist/datetimepicker/jquery.datetimepicker.full.min.js"></script>
<script type="text/javascript" src="./dist/fullcalendar-5.10.2/lib/main.min2.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="./js/cald/Calendar.js"></script>
<script type="text/javascript" src="./js/cald/datatime.js"></script>
<style type="text/css">
	.fc-license-message{
		display: none;
	}
	
	.koHolidays{
    	pointer-events: none;
    }
    
    .fc-daygrid-day-number{
    	pointer-events: none;
    }
    
    .fc-event-main-frame>.fc-event-title-container{
    	text-overflow: ellipsis;
    	margin-left: 48px;
    }
    
   .fc-event-main-frame>.fc-event-time{
   		position: absolute;
    }
    .koHolidays>.fc-event-main>.fc-event-main-frame>.fc-event-title-container{
    	margin-left: 0px;
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
								<input type="button" class="btn btn-default" onclick="insertAjax()" value="전송">
								<input type="checkbox" class="form-check-input" id="modifyButton"><label for="modifyButton">수정 활성화 버튼</label>
							</div>
						</form>
					</div>
					<div id="calendar" class="rContent-normal-bottom"></div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            
	            <!-- Edit Modal -->
				<div class="modal fade" id="schedule-edit">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <!-- Modal Header -->
				            <div class="modal-header">
				                <h4 class="modal-title">일정 수정</h4>
				                <button type="button" class="close" data-dismiss="modal">&times;</button>
				            </div>
				            
				            <!-- Modal body -->
				            <div class="modal-body">
				                <form id="modalFrm">
				                    <div class="form-group">
				                    	<input type="hidden" name="cald_id" id="id">
				                        <label>제목:</label>
				                        <input type="text" id="modalTitle" class="form-control" readonly>
				                        <label>내용:</label>
				                        <input type="text" name="eboard_content" class="form-control" id="modalContent">
				                        <label>일정 시작일:</label>
				                        <input type="text" name="cald_start" class="form-control" id="datetimepicker1_2" readonly>
				                        <label>일정 종료일:</label>
				                        <input type="text" name="cald_end" class="form-control" id="datetimepicker2_2" readonly>
				                    </div>
				                </form>
				            </div>
				            <!-- Modal footer -->
				            <div class="modal-footer">
				                <button type="button" class="btn btn-primary" data-dismiss="modal">취소</button>
				                 <button type="button" class="btn btn-success" onclick="updateContent()">수정</button>
				                 <input type="button" class="btn btn-danger" value="삭제" onclick="deleteContent()">
				            </div>
				        </div>
				    </div>
				</div>
				<!-- detail Modal -->
				<div class="modal fade" id="schedule-detail">
				    <div class="modal-dialog">
				        <div class="modal-content">
				            <!-- Modal Header -->
				            <div class="modal-header">
				                <h4 class="modal-title">상세 일정</h4>
				                <button type="button" class="close" data-dismiss="modal">&times;</button>
				            </div>
				            
				            <!-- Modal body -->
				            <div class="modal-body">
				                <form id="modaldetailFrm">
				                    <div class="form-group">
				                    	<input type="hidden" name="cald_id" id="id2">
				                        <label>제목:</label>
				                        <input type="text" id="modalTitle2" class="form-control" readonly>
				                        <label>내용:</label>
				                        <input type="text" class="form-control" id="modalContent2" readonly>
				                        <label>일정 시작일:</label>
				                        <input type="text" class="form-control" id="start" readonly>
				                        <label>일정 종료일:</label>
				                        <input type="text" class="form-control" id="end" readonly>
				                    </div>
				                </form>
				            </div>
				            <!-- Modal footer -->
				            <div class="modal-footer">
				                <button type="button" class="btn btn-primary" data-dismiss="modal">취소</button>
				            </div>
				        </div>
				    </div>
				</div>
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>