<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리(공지)</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/entr/entrAdmin.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN_BOARD','ROLE_ADMIN_INSA')">
					<div id="adminContent">
						<ul class="nav nav-tabs">
							<li class="active" id="navGongji"><a href="./entrBoardAdmin.do">공지게시판</a></li>
							<li id="navJaryo"><a href="./jaryoBoardAdmin.do">자료게시판</a></li>
						</ul>
						<br>
						
						<div>
							<button onclick="javascript:location.href='./entrBoardAdmin.do'" class="btn btn-default">전체</button>
							<button value="101" onclick="cgoryBoard(this.value)" class="btn btn-default">일반</button>
							<button value="102" onclick="cgoryBoard(this.value)" class="btn btn-default">필독</button>
							<button value="103" onclick="cgoryBoard(this.value)" class="btn btn-default">인사</button>
							<button value="302" onclick="cgoryBoard(this.value)" class="btn btn-default">일정</button>
						</div>
						<br>
						<form action="#" method="post" id="entrFrm" onsubmit="return ChangEntrDel()">
						<input type="submit" class="btn btn-success" value="숨김/보임" >
						<hr>
						<table id="entrTable" class="stripe">
							<thead>
								<tr>
									<th><input type="checkbox" name="allchk" id="allChk" onclick="allSelect(this.checked)"></th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>숨김</th>
									<th>조회</th>
								</tr>
							</thead>
						</table>
						</form>
					</div>
            </sec:authorize>
            </div><!-- content 끝 -->
        </main>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	$("#entrTable").DataTable({
		aoColumnDefs: [
	          { 'bSortable': false, 'aTargets': [0,1] }
	       ],
	       scrollCollapse:true,
			scrollY:"200px",
	     lengthMenu: [ 5, 10, 15],
	     displayLength: 5,
	  	"language": {
            "emptyTable": "게시글이 없어요.",  //게시글이 없을 경우
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
            "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
            "infoEmpty": "게시글 없음",   // info부분이 비었을 경우 
            "infoFiltered": "( _MAX_건의 게시글에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
	     
	     
		ajax:{
			url:"./selEboardAllAdmin.do",
			type:"post",
			dataSrc: ''
		},
		columns:[
			{data:"eboard_no", render:function(data){return '<input type="checkbox" name="chk" value="'+data+'">'}},
        	{data:"cgory_no"},
        	{data:"eboard_title",
        	render: function (data,type,row,meta) {
	        	      return '<a href="./OneBoardAdmin.do?eboard_no='+row.eboard_no+'">'+data+'</a>';
	        	    }		
        	},
        	{data:"emp_name"},
        	{data:"eboard_regdate"},
        	{data:"eboard_delflag"},
        	{data:"eboard_readcount"}
        ]
		
		
	});	
}); 
</script>
</body>
</html>