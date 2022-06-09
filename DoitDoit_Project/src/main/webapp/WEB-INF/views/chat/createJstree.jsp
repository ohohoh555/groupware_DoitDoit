<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
	<div style="margin:0px auto;" >
    	<div style="margin-left:30px">
			<input type="text" id="searchPeople" placeholder="검색할 사람을 입력해 주세요." style="width: 200px; margin:0px auto;">
	    	<!-- 채팅방 생성시 jsTree 태우는 부분 -->
	    	<div id="createJsTree" style="height: 200px; margin-top:10px; overflow-y: scroll; overflow-x: hidden"></div>
	    	<hr style="size: 20px">
	    	<input type="text" id="roomName" placeholder="채팅방 제목 길이를 3~20 사이로 설정해 주세요." style="width: 200px;" maxlength="20">
	    </div>
	</div>
</body>
</html>