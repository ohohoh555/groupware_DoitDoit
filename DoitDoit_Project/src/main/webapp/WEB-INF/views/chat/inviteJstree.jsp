<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
	<div style="margin:0px auto;" >
    	<div style="margin-left:30px">
			<input type="text" id="searchPeople" placeholder="검색할 사람을 입력해 주세요." style="width: 200px; margin:0px auto;">
	    	<!-- 채팅방 생성시 jsTree 태우는 부분 -->
	    	<div id="inviteJsTree" style="height: 200px; margin-top:10px; overflow-y: scroll; overflow-x: hidden"></div>
	    </div>
	</div>
</body>
</html>