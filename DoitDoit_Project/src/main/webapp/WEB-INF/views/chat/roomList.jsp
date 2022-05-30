<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<label>${principal}님 채팅에 오신것을 반갑습니다.</label>
	</div>
	<div>
		<table>
			<thead>
				<tr>
					<th>채팅방 제목</th><th>최근 채팅</th><th>채팅 시간</th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach var="i" items="${rooms}">
					<tr>
						<td>${i.room_name}</td><td><a href="./chatRoom.do?room_Id=${i.room_id }">${i.chat_con}</a></td><td>${i.chat_time }</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>