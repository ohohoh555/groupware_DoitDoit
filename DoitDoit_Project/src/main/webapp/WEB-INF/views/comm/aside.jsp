<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
	<aside>
    	<div id="profile">
    		<div id="proHeader">
    			<img onclick="chatOn()" class="menuIcon" src="./img/menuBar.png" alt="">
    		</div>
    		<div id="proBody">
    			<div id="profileCont"></div>
    		</div>
    		<div id="proFoot">
    			<p><sec:authentication property="principal" var="info"/>환영합니다 <b>${info.emp_name}</b>님</p>
    			<p><sec:authentication property="principal" var="info"/>&lt;${info.rank_no}&gt;</p>
    		</div>
    	</div>
    	<div id="chat" style="overflow:hidden; overflow-y: scroll;padding: 5px">
    		<div id="chatRoom">

    		</div>
    	</div>
  	</aside>
</body>
</html>