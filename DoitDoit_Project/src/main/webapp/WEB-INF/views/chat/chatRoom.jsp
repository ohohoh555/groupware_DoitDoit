<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.0/sockjs.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="./js/chat/chat.js"></script>

<style type="text/css">
	.rContent-full > div > div {
		float: left;
	}
	
	/* 채팅, 파일 */
	.chatGroup{
		width:310px;
		height:100%;
		background-color: #6D6AB7;
		border: 1px solid black;
	}
	
	/* 참가 리스트 layout */
	.memberList{
		width:180px;
		height: 100%;
	}
	
	/* memberList 안에 */
	.members{
		margin-top: 1px;
		width: 100%;
		height: 722px;
		background-color: #B5B2FF;
	}
	
	/* 각 영역 상단 부분 */
	.roomInfo{
		width: 100%;
		height: 75px;
		background-color: #F6F6F6;
	}
	
	/* 채팅방 입력 부분 */
	.insChat{
		width: 100%;
		height: 150px;
		background-color: #FAFAFA; 
		margin-top: 12px;
	}
	
	.inputArea{
		padding: 10px;
		width : 100%;
		height: 100%;
	}
	
	textarea{
		resize: none;
	}
	
	#btnSend{
		margin-left: 20px;
		position: absolute;
		top: 775px;
	}
	
	#fileIcon{
		margin-left: 10px;
		position: relative;
		bottom: 5px;
		width: 30px;
	}
	
	/* 채팅 리스트 */
	#dragdrop{
		border-radius: 3px;
	}
	
	#chatList{
		width: 100%;
		height: 560px;	
		vertical-align: bottom;
	}
	
	#chatLog {
	    height: 565px;
	    overflow-y: auto;
	    padding: 0px 10px;
	}
	
	.myMsg {
		width:265px;
	    text-align: right;
	    margin-bottom: 5px;
	}
	
	.anotherMsg {
	    text-align: left;
	    margin-bottom: 5px;
	}
	
	.msg {
		height:100%;
	    display: inline-block;
	    border-radius: 15px;
	    padding: 7px 15px;
	    margin-bottom: 10px;
	    margin-top: 5px;
	}
	
	.issue{
		text-align: center;
		margin-bottom: 5px;
	}
	
	.anotherMsg > .msg {
	    background-color: #f1f0f0;
	}
	
	.myMsg > .msg {
	    background-color: #0084FF;
	    color: #fff;
	}
	
	.issue > .msg{
		background-color: #8C8C8C;
	    color: #fff;
	}
	
	.msg{
		max-width: 200px;
		white-space: pre-wrap;
	}
	
	.msg > img{
		margin: 0px auto;
	}
	
	.Name {
	    font-size: 12px;
	    display: block;
	}
	/* 채팅방 멤버 리스트 */
</style>
</head>
<body>
	<input type="hidden" id="roomId" value="${room_id}">
	<input type="hidden" id="empId" value="${emp_id }"> 
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
						<div style="width: 800px; height: 796px;">
							<div id="dragdrop" class="chatGroup">
								<div class="roomInfo">
									채팅방 정보
								</div>
								<div id="chatList"> <!-- chatWrap -->
									<c:forEach items="${chatList }" var="i">
										
									</c:forEach>
									<div id="chatLog">
										<c:forEach items="${chatList }" var="i">
						                	<div class=${i.emp_id==0?"issue":(emp_id==i.emp_id?"myMsg":"anotherMsg")}>
												${i.chat_con}
						                	</div>						             	
										</c:forEach>
					            	</div>
								</div>
								<div class="insChat">
									<div class="inputArea">
										<div>
											<textarea rows="5" cols="25"></textarea>					
											<button id="btnSend">전송</button>									
										</div>
										<div>
											<label for="chatFile">
												<img src="./img/chat/file.png" id="fileIcon">
											</label>
											<input type="file" value="파일 전송" name="file" id="chatFile" multiple="multiple" style="display: none;">
										</div>
									</div>
								</div>
							</div>
							<!-- 멤버 리스트 -->
							<div class="memberList">
								<div class="roomInfo">
									채팅방 참가 멤버
								</div>
								<div id="members" class="members">
								
								</div>
							</div>
							<!-- 사진, 파일 -->
							<div class="chatGroup">
								<div class="roomInfo">
									사진, 파일
								</div>
								<div>
								
								</div>
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