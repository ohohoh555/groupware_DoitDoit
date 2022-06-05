<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
	<input type="text" id="roomName" placeholder="채팅방 제목을 설정해 주세요." style="width: 270px;">
    <div id="makeJsTree">
    
    </div>
    <button onclick="btnInvite()">초대</button>
    <button onclick="btnCancle()">취소</button>
</body>
</html>