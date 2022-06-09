<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자료게시판(사용자)</title>
<%@include file="../comm/setting.jsp" %>
<link rel="stylesheet" type="text/css" href="./css/jaryo/dragAndDrop.css">
<script type="text/javascript" src="./js/jaryo/Map.js"></script>
<script type="text/javascript" src="./js/jaryo/dragAndDrop.js"></script>
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
						<h3>자료게시판</h3><h2></h2>
						<sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal" var="principal"/>
					        <input type="hidden" value="${principal.emp_id}" id="emp_id" name="emp_id"> 
					        <input type="hidden" value="${principal.emp_name}" id="emp_name" name="emp_name">
 			           </sec:authorize> 
						<div>
							<div class="div1">
								<button class='btn btn-success' onclick="slideDown()">파일 업로드</button>
								<button class='btn btn-success' onclick="multiJaryo()">다중다운로드</button>
								<div id="fileUpload" class="dragAndDropDiv hide10" style="position: relative;" >
									<table id='fileTable' class='fileList table-bordered'>
										<tr>
											<td id='tabFileName'>파일명</td>
											<td id='tabFileSize'>사이즈</td>
											<td id='tabFileDel'>삭제</td>
										</tr>	
									</table>
									<div class="div2" style="position: absolute; bottom: 0px; width: 95% ;">
									<input type="button" class="btn btn-default" value="등록" onclick="submitFile()" style="margin: 0px auto;">
									</div>
								</div>
							</div> <!-- div1 끝 -->

						</div>
						<div>
						<form id="jaryoFrm" method="post">
							<table id="jaryoTable" class="stripe">
								<thead>
									<tr>
										<th><input type="checkbox" id="allchk"></th>
										<th>번호</th>
										<th>파일명</th>
										<th>크기</th>
										<th>등록일자</th>
									</tr>
								</thead>
							</table>
						</form>	
						</div>
					</div><!-- rContent-full 끝 -->
                </div><!-- rContent 끝 -->
           <%@include file="../comm/aside.jsp" %>  
            </sec:authorize>
            </div><!-- content 끝 -->
               
        </main>
    </div> <!-- container 끝 -->
    
    
<script type="text/javascript">
$(document).ready(function(){
	var emp = document.getElementById("emp_id").value;
	var megaByte = 1024*1024;
	console.log(emp);
	$("#jaryoTable").DataTable({
	     displayLength: 10,
	     lengthChange: false,
		 "language": {
	        "emptyTable": "자료가 없어요.",
	        "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
	        "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
	        "infoEmpty": "자료 없음",   // info부분이 비었을 경우 
	        "infoFiltered": "( _MAX_건의 게시글에서 필터링됨 )",
	        "search": "검색: ",
	        "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
	        "loadingRecords": "자료를 불러오는 중입니다",
	        "processing":     "잠시만 기다려 주세요...",
	        "paginate": {
	            "next": "다음",
	             "previous": "이전"
	            }
	    }, 
	        
	    
	    ajax:{
	       	url:"./selJaryoAllUser.do",
	        type:"post",
	        dataSrc:""
	    },
	    columns:[
	    	{data:"eboard_no",render:function(data,type,row,meta){return "<input type='checkbox' name='chk' value='uid="+row.flist_uuid+"&fileName="+row.flist_originname+"'>" } },
	        {data:"eboard_no",render:function(data,type,row,meta){return meta.row+1 } },
	        {data:"flist_originname",render:function(data,type,row,meta){
	        	if(row.emp_id==emp){
	        		return '<a href="./download.do?uid='+row.flist_uuid+'&fileName='+data+'">'
	        						+data+'</a>&nbsp;&nbsp;&nbsp;<button onclick="hideJaryo('+row.eboard_no+')">삭제</button>'
	        	}else{
	        		return '<a href="./download.do?uid='+row.flist_uuid+'&fileName='+data+'">'+data+'</a>'
	        		}
	
	        	}	
	        },
	        {data:"flist_size", render:function(data,type,row,meta){return Math.round(data/megaByte*100)/100+'MB'}},
	        {data:"eboard_regdate"},
	       ]
	});
});


function hideJaryo(eboard_no){
	console.log("hideJaryo 작동");
	var con = confirm("선택한 자료를 삭제하시겠습니까?(복구는 관리자를 통해서만 가능합니다.)");
	if(con){
		location.href="./jaryoDel.do?eboard_no="+eboard_no;
	}
}

function downloadJaryo(data){
	console.log(" downloadJaryo 작동",data);
}

function multiJaryo(){
	console.log("multiJaryo 작동");
	
	

	var len = $("input[name=chk]:checked").length;
	var list = new Array(len);
	if(len<=0){
		alert("하나 이상의 자료를 선택해주세요!");
		return false;
	}else{
//		$("#jaryoFrm").attr("action","./multiJaryo.do").submit();

		let fileDown = "";
		let link ="";
		for(let i=0; i<len;i++){
			list[i]= $("input[name=chk]:checked").eq(i).val();
			fileDown ="./download.do?"+list[i];
			let encodedUri = encodeURI(fileDown);
			link = document.createElement("a");
			link.setAttribute("href", encodedUri);
			link.setAttribute("download", "");
			document.body.appendChild(link);
			link.click();
		}
		
		$("input[name=chk]").prop("checked", false);
 //		document.body.removeChild(link);

	
	}
}

</script>   
</body>
</html>