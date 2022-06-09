<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/chat/chat.js"></script>
<style type="text/css">
	.rContent-full > div > div {
		float: left;
	}
	
	/* 채팅, 파일 */
	.chatGroup{
		width:310px;
		height:100%;
		background-color: #B5B2FF;
		border: 1px solid black;
	}
	
	/* 참가 리스트 layout */
	.memberList{
		width:180px;
		height: 100%;
	}
	
	/* memberList 안에 */
	.members{
		padding-top: 10px;
		width: 100%;
		height: 722px;
		background: #3d3e6d;
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
	    height: 572px;
	    overflow-y: scroll;
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
	
	.msg, .imageMsg {
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
	
	.anotherMsg > .msg, .anotherMsg > .imageMsg {
	    background-color: #f1f0f0;
	}
	
	.myMsg > .msg, .myMsg > .imageMsg {
	    background-color: #0084FF;
	    color: #fff;
	}
	
	.issue > .msg{
		background-color: #8C8C8C;
	    color: #fff;
	}
	
	.msg .imageMsg{
		max-width: 200px;
/* 		white-space: pre-wrap; */
	}
	
	.imageMsg > img{
		max-width : 180px;
		max-height : 180px;
		margin: 0px auto;
	}
	
	.Name {
	    font-size: 12px;
	    display: block;
	}
	/* 채팅방 멤버 리스트 */
	.memList{
		font-size: 20px; 
		color: white; 
		padding-left: 5px
	}
	
	.memListRank{
		font-size: 15px;
	}
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
						<sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal" var="principal"/>
					        <input type="hidden" value="${principal.emp_id}" id="pr_emp_id" name="pr_emp_id"> 
					        <input type="hidden" value="${principal.emp_name}" id="pr_user_name" name="pr_emp_name">
 			           </sec:authorize> 
 			           <input type="hidden" value="${room_id}" id="room_id">
 			           <input type="hidden" value="${room_mem_list }" id="room_mem_list">
					   <div style="width: 800px; height: 796px;">
							<div id="dragdrop" class="chatGroup">
								<div class="roomInfo">
									채팅방 정보
									<div class="container">
										<div>
											<button class="btn-sm btn-info" onclick="treeDo('invite')" data-toggle="modal" data-target="#iJstree">초대하기</button>
											<button onclick="getOut('${room_id}','${emp_id}')">나가기</button>
										</div>									
										<div class="modal fade" id="iJstree" role="dialog">
											<div class="modal-dialog modal-sm">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4>채팅방 초대</h4>
													</div>
													<div class="modal-body">
														
													</div>
													<div class="mod-footer">
														<div style="width: 200px; margin:10px auto">
															<button onclick="btnCreate()" class="btn btn-success">생성</button>
															<button onclick="btnHide('invite')" class="btn">숨김</button>
												    		<button onclick="btnCancle('invite')" class="btn">취소</button>								
														</div>
													</div>
												</div>
											</div>
										</div>									
									</div>
								</div>
								<!-- 모달 끝 -->
								<div id="chatList"> <!-- chatWrap -->
									<div id="chatLog">
										<c:forEach items="${chatList }" var="i">
						                	<div class=${i.emp_id==0?"issue":(emp_id==i.emp_id?"myMsg":"anotherMsg")}>
												${i.chat_con}
						                	</div>						             	
										</c:forEach>
										<div class="myMsg">
												<span class="Name">김우연</span>
												<span class="imageMsg"><img src="./chatFile/2022/6/9/5c498bea-7291-43c1-b229-1d91e1ce3c4e.png"></span>
						                </div>
					            	</div>
								</div>
								<div class="insChat">
									<div class="inputArea">
										<div>
											<textarea rows="5" cols="25" id="chatCon"></textarea>					
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
									<c:forEach items="${room_mem_list }" var="i">
										<div id="${i.EMP_ID }">
												<span class="memList"></span>
												<span class="memList">${i.EMP_NAME }</span>
												<span class="memList memListRank">${i.RANK_NAME }</span>
											<hr style="color:white;">
										</div>
									</c:forEach>
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