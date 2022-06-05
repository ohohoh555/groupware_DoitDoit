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
						<ul class="nav nav-tabs">
							<li class="active" id="navGongji"><a href="./entrBoardAdmin.do">공지게시판</a></li>
							<li id="navJaryo"><a onclick="adminJaryo()">자료게시판</a></li>
						</ul>
						<br>
					<div id="boardResult">
						<div>
							<button onclick="javascript:location.href='./entrBoardAdmin.do'" class="btn btn-default">전체</button>
							<button value="101" onclick="cgoryBoard(this.value)" class="btn btn-default">일반</button>
							<button value="102" onclick="cgoryBoard(this.value)" class="btn btn-default">필독</button>
							<button value="103" onclick="cgoryBoard(this.value)" class="btn btn-default">인사</button>
							<button value="302" onclick="cgoryBoard(this.value)" class="btn btn-default">일정</button>
						</div>
						
						<form action="#" method="post" id="delFrm" onsubmit="return ChangDel()">
						<input type="submit" class="btn btn-success" value="숨김/보임" >
						<br>
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
					</div><!-- rContent-full 끝 -->
                </div> <!-- rContent 끝 -->
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
}); //$(document).ready(function() 끝


function cgoryBoard(val){
	console.log("cgoryBoard 작동", val);
	
	$("#entrTable").DataTable().destroy();
	
	$.ajax({
		url:"./selEboardCgoryAdmin.do?cgory_no="+val,
		type:"get",
		success:function(data){
			console.log("cgoryBoard 통신성공",data);
			$("#entrTable").DataTable({
				data:data,
				columns:[
					{data:"eboard_no", render:function(data){return '<input type="checkbox" name="chk" value='+data+'>'}},
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
			console.log("cgoryBoard 통신실패");
		}
		
	});
}


//전체체크 allChk 클릭시 모든 체크박스 선택 또는 해제
function allSelect(bool){
	console.log("checkAll 작동", bool);
	var chks = document.getElementsByName("chk");
	for(let i in chks){
		chks[i].checked=bool;
	}
}


function ChangDel(){
	//선택한 체크박스의 갯수 가져오기
	var chks = $("input:checkbox[name='chk']:checked");
	console.log(chks.length);
	
	//선택한 체크박스의 값 가져오기
	var chksVal = '';
    $('input[type="checkbox"]:checked').each(function (index) {
        if (index != 0) {
        	chksVal += ', ';
        }
        chksVal += $(this).val();
    });
    
    
	
	if(chks.length > 0){
		document.getElementById("delFrm").action = "./changeDel.do";
		var con = confirm("선택한 글의 숨김여부를 변경하시겠습니까? 선택한 글 번호: " +chksVal);
		if(con){
			alert("선택한 글의 숨김여부가 변경되었습니다.");
			return true;
		}
		return false;
	}else{
		alert("하나이상의 글을 선택해주세요");
		return false;
	}

}

function adminJaryo(){
	console.log("자료게시판 이동");
	$("#navGongji").removeClass("active");
	$("#navJaryo").addClass("active");
	$("#boardResult").html("");
	
	$.ajax({
		url:"./jaryoBoardAdmin.do",
		method:"get",
		dataType:"json",
		success:function(msg){
			console.log("자료게시판 아작스 성공",msg);
			
			var html = "";
			html += "<table>";
			html += "<thead>";
			html += "<tr>";	
			html += "<th><input type='checkbox'></th>";		
			html += "<th>파일명</th>";		
			html += "<th>크기</th>";		
			html += "<th>등록자</th>";		
			html += "<th>숨김여부</th>";		
			html += "<th>등록일자</th>";		
			html += "</tr>";	
			html += "</thead>";
			html += "<tbody>";
			$.each(msg, function(key, value){
				console.log(value.eboard_no);
			
				html += "<tr>";	
				html += "<td><input type='checkbox'></td>";		
				html += "<td></td>";		
				html += "<td></td>";		
				html += "<td></td>";		
				html += "<td></td>";		
				html += "<td></td>";		
				html += "</tr>";	
			});
			html += "</tbody>";
			html += "</table>";
			
			$("#boardResult").html(html);
			
		},
		error:function(){
			alert("자료게시판 통신실패");
		}
		
	});
}

</script>
</body>
</html>