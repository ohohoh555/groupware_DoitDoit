<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판(사원)</title>
<%@include file="../comm/setting.jsp" %>
</head>
<body>

	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
						<h3>&lt;&lt;공지게시판&gt;&gt;</h3>
						<hr>
						<table id="FildokTable" class="stripe">
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
										<td><a href="./OneBoard.do?eboard_no=${fVo.eboard_no}">${fVo.eboard_title}</a></td>
										<td>${fVo.emp_name}</td>
										<td>${fVo.eboard_regdate}</td>
										<td>${fVo.eboard_readcount}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6" style="text-align: center;"><ul><li onclick="fildokAll()">&lt;&lt;[필독]게시글 전체보기&gt;&gt;</li></ul></td>
								</tr>
							</tfoot>
						</table>
						
						<hr>
						<div id="EntrDiv">
						<table id="EntrTable" class="stripe">
							<thead>
								<tr>
									<th>No.</th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th width="80px;">등록일</th>
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
						
							<button onclick="javascript:location.href='./entrBoard.do'" class="btn btn-default">전체</button>
							<button value="101" onclick="cgoryAction(this.value)" class="btn btn-default">일반</button>
							<button value="103" onclick="cgoryAction(this.value)" class="btn btn-default">인사</button>
							<button value="302" onclick="cgoryAction(this.value)"class="btn btn-default"> 일정</button>
							<button onclick="insertBoard()" class="btn btn-success">글쓰기</button>
						</div>
					</div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
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
        displayLength: 5,
        order:[ [ 4, "desc"]]

	});
	
	
	$("#FildokTable").DataTable({
		lengthChange: false,
		searching: false,
		ordering: false,
		info: false,
		paging:false,
		order:[ [ 4, "desc"]]
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
				        { data: 'eboard_title',
				        	render: function (data,type,row,meta) {
				        	      return '<a href="./OneBoard.do?eboard_no='+row.eboard_no+'">'+data+'</a>';
				        	    }
				        },
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
				     displayLength: 5,
				     order:[ [ 4, "desc"]]
				});
			},
			error:function(){
				alert("EboardCgory 통신실패");
			}
	});
}


function insertBoard(){
	console.log("insertBoard 글쓰기 작동");
	location.href ="./insertBoard.do"
}

function fildokAll(){
	console.log("필독 전체보기 작동");
	$("#FildokTable").DataTable().destroy();
	
	$.ajax({
		url:"./FildocAll.do",
		type:"get",
		success:function(data){
			console.log(data);
			$("#FildokTable").DataTable({
				data:data,
				columns: [
					{data:"eboard_no", render: function (data,type,row,meta) {return "★"}},
					{data:"cgory_no", render: function (data,type,row,meta) {return "[필독]"}},
					{data:"eboard_title", render:function(data,type,row,meta){return '<a href="./OneBoardAdmin.do?eboard_no='+row.eboard_no+'">'+data+'</a>'}},
					{data:"emp_name"},
					{data:"eboard_regdate"},
					{data:"eboard_readcount"}
				],
				lengthChange: false,
				searching: false,
				ordering: false,
				info: false,
				paging:false,
				order:[ [ 4, "desc"]]
			});
		},
		error:function(){
			alert("필독전체보기 통신에러");
		}
		
		
	});
	
	$("#FildokTable tfoot>tr").remove();
	$("#FildokTable>tfoot").append('<tr><td colspan="6" style="text-align: center;" onclick="fildokClose()">필독게시판 닫기</td></tr>');
	$("#EntrDiv").remove();

}

function fildokClose(){
	location.href="./entrBoard.do";
}

</script>

</body>
</html>