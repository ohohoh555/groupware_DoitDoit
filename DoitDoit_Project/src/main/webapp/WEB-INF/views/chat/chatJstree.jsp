<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<body>
	<div style="display: inline-flex; margin:0px auto;" >
    	<div style="margin-left:30px">
			<input type="text" id="searchPeople" placeholder="검색할 사람을 입력해 주세요." style="width: 200px; margin:0px auto;">
	    	<div id="makeJsTree" style="height: 200px; margin-top:10px; overflow-y: scroll; overflow-x: hidden">
	    
	    	</div>
	    </div>
	    <div style="margin:50px; margin-top:70px">
	    	<input type="button" class="btnAdd" onclick="peopleAdd()" value="&gt;" ><br><br>
	    	<input type="button" class="btnSub" onclick="peopleSub()" value="&lt;">
	    </div>
	    <div>
	    	<input type="text" id="roomName" placeholder="채팅방 제목 길이를 3~20 사이로 설정해 주세요." style="width: 200px;" maxlength="20">
	    	<div id="selectedPeople">
	    		
	    	</div>
	    </div>
	</div>
</body>
</html>