<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판(사원)</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-normal-top">
						<h5>공지게시판</h5>
						<table id="FildokTable" class="cell-border">
							<thead>
								<tr>
									<th>No.</th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>조회</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="fVo" items="${fList}">
									<tr>
										<td>★</td>
										<td>[필독]</td>
										<td>${fVo.eboard_title}</td>
										<td>${fVo.emp_name}</td>
										<td>${fVo.eboard_regdate}</td>
										<td>${fVo.eboard_readcount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="rContent-normal-bottom">
						<table id="EntrTable" class="cell-border">
							<thead>
								<tr>
									<th>No.</th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>조회</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="eVo" items="${eList}">
									<tr>
										<td>${eVo.eboard_no}</td>
										<td>${eVo.cgory_no}</td>
										<td><a href="./OneBoard.do?eboard_no=${eVo.eboard_no}">${eVo.eboard_title}</a></td>
										<td>${eVo.emp_name}</td>
										<td>${eVo.eboard_regdate}</td>
										<td>${eVo.eboard_readcount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div>
							<button onclick="javascript:location.href='./entrBoard.do'">전체</button>
							<button value="101" onclick="cgoryAction(this.value)">일반</button>
							<button value="103" onclick="cgoryAction(this.value)">인사</button>
							<button value="302" onclick="cgoryAction(this.value)">일정</button>
						</div>
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
	$('#EntrTable').DataTable({
   		"language": {
            "emptyTable": "데이터가 없어요.",  //게시글이 없을 경우
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
            "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
            "infoEmpty": "데이터 없음",   // info부분이 비었을 경우 
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        lengthMenu: [ 5, 10, 15],
        displayLength: 5

	});
	
	
	$("#FildokTable").DataTable({
		lengthChange: false,
		searching: false,
		ordering: false,
		info: false,
		paging:false
	});
		
});

function cgoryAction(val){
	console.log("cgoryAction 작동", val);
	
	$("#EntrTable").DataTable().destroy();
	
		$.ajax({
			url:"./cgoryBoard.do?cgory_no="+val,
			type:"get",
			success:function(data){
				console.log(data);
				$('#EntrTable').DataTable({
					data:data,
				    columns: [
				        { data: 'eboard_no' },
				        { data: 'cgory_no' },
				       // { data: 'eboard_title',render: function(data){ return '<a href="./OneBoard.do?eboard_no='+ data.eboard_no +'">'+data.eboard_title+'</a>'}; },
				        { data: 'eboard_title'},
				        { data: 'emp_name' },
				        { data: 'eboard_regdate' },
				        { data: 'eboard_readcount'}
				    	],
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
				     lengthMenu: [ 5, 10, 15],
				     displayLength: 5
				});
			},
			error:function(){
				alert("EboardCgory 통신실패");
			}
	});
}


</script>

</body>
</html>