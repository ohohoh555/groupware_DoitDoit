<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN_BOARD','ROLE_ADMIN_INSA')">
                <div id="rContent">
					<div class="rContent-full">
						<h1>공지게시판</h1>
						<div>
							<button>전체</button>
							<button value="101" onclick="cgoryBoard(this.value)">일반</button>
							<button value="102" onclick="cgoryBoard(this.value)">필독</button>
							<button value="103" onclick="cgoryBoard(this.value)">인사</button>
							<button value="302" onclick="cgoryBoard(this.value)">일정</button>
						</div>
						<table id="entrTable">
							<thead>
								<tr>
									<th><input type="checkbox"></th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>숨김여부</th>
									<th>조회</th>
								</tr>
							</thead>
						</table>
					</div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize>
            </div>
        </main>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	$("#entrTable").DataTable({
	     lengthMenu: [ 5, 10, 15],
	     displayLength: 10,
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
			{data:"eboard_no", render:function(){return '<input type="checkbox">'}},
        	{data:"cgory_no"},
        	{data:"eboard_title"},
        	{data:"emp_name"},
        	{data:"eboard_regdate"},
        	{data:"eboard_delflag"},
        	{data:"eboard_readcount"}
        ]
		
		
	});
});


function cgoryBoard(val){
	console.log("cgoryBoard 작동", val);
	
	
	let param = {"cgory_no" : val};
	
// 	$.ajax({
// 		url:"./selEboardCgoryAdmin.do",
// 		type:"post",
// 		data:JSON.stringify(param),
// 		contentType:"application/json",
// 		succes:function(data){
// 			$('#entrTable').DataTable({
// 				data:data,
// 				   columns: [
// 				        { data: 'eboard_no' },
// 				        { data: 'cgory_no' }
// 				        ]
				
				
// 			});
// 		},
// 		error:function(){
// 			alert("cgoryBoard 통신실패");
// 		}
// 	});
}

</script>
</body>
</html>