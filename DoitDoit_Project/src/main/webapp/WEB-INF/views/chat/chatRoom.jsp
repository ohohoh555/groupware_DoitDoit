<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.0/sockjs.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script type="text/javascript" src="./js/chat.js"></script>
</head>
<body>
	<input type="hidden" id="roomId" value="${room_id}">
	<input type="hidden" id="empId" value="${emp_id }"> 
	<div>
		<label>${userId }님 채팅에 오신것을 반갑습니다.</label>
	</div>
	<div>
		<table>
			<thead>
				<tr>
					<th>채팅러</th><th>채팅</th><th>채팅 시간</th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach var="i" items="${chatList}">
					<tr>
						<td>${i.emp_id}</td><td>${i.chat_con}</td><td>${i.chat_time}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td>				
						<input type="text" id="chatCon"><button id="chatSend">전송</button>
<!-- 						<input type="text" id="chatCon" onkeyup="enterkey()"><button id="chatSend">전송</button> -->
					</td>	
				</tr>
				<tr>
					<td>
						<input multiple="multiple" type="file" name="file" id="chatFile">
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<div>
		<div>현재 채팅 멤버</div>
		<div id="chat_memList">
		
		</div>
	</div>
</body>
</html>