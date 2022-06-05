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
    	<div id="chat">
    		<div>
    			<sec:authentication property="principal" var="info"/>
    			<input type="hidden" id="pr_emp_id" value="${info.emp_id }">
    			<!-- 채팅방 리스트 -->
	    		<div id="chatRoom">
	
	    		</div>    		
    		</div>
    		<div class="container">
    			<div>
	    			<button class="btn-sm btn-info" data-toggle="modal" data-target="#chatJstree">채팅방 생성</button>    						
    			</div>
<!--     		jstree 초대 부분(modal) -->
			 	<div class="modal fade" id="chatJstree" role="dialog">
					<div class="modal-dialog modal-sm">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4>채팅창 초대</h4>
							</div>
							<div class="mod-body">
								
							</div>
						</div>
					</div>
				</div>
    		</div>
    	</div>
  	</aside>
</body>
</html>