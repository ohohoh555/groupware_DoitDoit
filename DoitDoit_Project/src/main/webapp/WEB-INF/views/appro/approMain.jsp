<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문서함 메인 화면</title>
</head>
<%@include file="../comm/setting.jsp" %>

<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                    
                    <div class="rContent-normal-top" style="width: 780px; height: 300px; margin-top: 10px;">
                    <fieldset style="width:770px;">
						<legend style="margin-bottom : 3px;">결재 대기 문서</legend>
						<span id="menu">
                    	<input type="button"  class="btn btn-default"  onclick="myDoc()" value="상신">
                    	<input type="button"  class="btn btn-default"  onclick="allDoc()" value="송신">
						</span>
                    	<input type="hidden" id="emp_id" value="${emp_id}">
					</fieldset>
                    <div style="width: 774px; height: 225px; border: 1px solid black;margin-left:1px;">
                    <table id="approList" class="cell-border dataTable">
                    <thead>
                    <tr>
                    <th>No.</th>
                    <th>문서번호</th>
                    <th>결재상태</th>
                    <th>제목</th>
                    <th>기안자</th>
                    <th>기안일</th>
                    </tr>
                    </thead>
                    <tbody id="myDocList" >

                    </tbody>
                    </table>
                    </div>
                    </div>
                    
                     <div class="rContent-normal-bottom" style="width: 780px; height: 460px;">
                      <fieldset style="width:770px;">
						<legend style="margin-bottom : 3px;">결재 문서 조회</legend>
						<select style="width: 150px; float: left;" class="form-control" id="statusSelect">
								<option>==== 선택 ====</option>
								<option value="1">결재대기</option>
								<option value="2">반려</option>
								<option value="3">결재완료</option>
								<option value="4">임시저장</option>
							</select>
                    		<input type="button"  class="btn btn-default"  onclick="docClick()" value="선택">
					</fieldset>
					   <div style="width: 774px; height: 383px; border: 1px solid black;margin-left:1px;">
					 <table id="approStatusList" class="cell-border dataTable">
                    <thead>
                    <tr>
                    <th>No.</th>
                    <th>문서번호</th>
                    <th>결재상태</th>
                    <th>제목</th>
                    <th>기안자</th>
                    <th>기안일</th>
                    </tr>
                    </thead>
                    <tbody id="statusDocList" >
                    
                   	</tbody>
                    </table>
                     </div>
                    </div>
                </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
         
            </div>
        </main>
    </div>
</body>
<script type="text/javascript">
window.onload = function approDocList(){
	myDoc();
}

$("#menu > input").each(function(){
    $(this).click(function(){
       $(this).attr("class", "btn btn-primary");
       $(this).siblings().attr("class", "btn btn-default"); 
    });
 });

function myDoc(){
	var menu = document.querySelectorAll('#menu');
	$("#menu input:eq(0)").attr("class", "btn btn-primary");
	
//	console.log("아작스 실행전");
	var emp_id = document.getElementById("emp_id").value;
//	console.log(emp_id);
	$.ajax({
		url : "./myDocList.do",
		type : "GET",
		data : "emp_id="+emp_id,
		async : true,
		success : function(data){
//			console.log("ajax성공");
//			console.log(data);
			var tbody = document.getElementById("myDocList")	//제거하고자 하는 엘리먼트
			while ( tbody.hasChildNodes() )
			{
				tbody.removeChild(tbody.firstChild );       
			}
			var json = JSON.parse(data);
//			console.log(json);
//			console.log(json.lists);
			
			var lists = json.lists;
//			console.log(lists[0]);
			for(let i=0; i<lists.length; i++){
				if(lists[i].appro_status == '결재대기'){
				html = '';
				html += '<tr>';
				html += '<td>'+(i+1)+'</td>';
				html += '<td><a href="./selDocDetail.do?emp_id='+emp_id+'&appro_no='+lists[i].appro_no+'">'+lists[i].appro_no+'</td>';
				html += '<td>'+lists[i].appro_status+'</td>';
				html += '<td>'+lists[i].appro_title+'</td>';
				html += '<td>'+lists[i].appro_empname+'</td>'
				html += '<td>'+lists[i].appro_regdate+'</td>';
				html += '</tr>';
				$("#myDocList").append(html);
				} 
			} 
			
		}	
	});
}

function allDoc(){
//	console.log("아작스 실행전");
	var emp_id = document.getElementById("emp_id").value;
//	console.log(emp_id);
	$.ajax({
		url : "./docAllList.do",
		type : "GET",
		data : "emp_id="+emp_id,
		async : true,
		success : function(data){
	//		console.log("ajax성공");
	//		console.log(data);

			var tbody = document.getElementById("myDocList")	//제거하고자 하는 엘리먼트
			while ( tbody.hasChildNodes() )
			{
				tbody.removeChild(tbody.firstChild );       
			}
			
			var json = JSON.parse(data);
	//		console.log(json.lists);
			
			var lists = json.lists;
	//		console.log(lists[0]);
			for(let i=0; i<lists.length; i++){
				if(lists[i].appro_status == '결재대기'){
					html = '';
					html += '<tr>';
					html += '<td>'+(i+1)+'</td>';
					html += '<td><a href="./selDocDetail.do?emp_id='+emp_id+'&appro_no='+lists[i].appro_no+'">'+lists[i].appro_no+'</td>';
					html += '<td>'+lists[i].appro_status+'</td>';
					html += '<td>'+lists[i].appro_title+'</td>';
					html += '<td>'+lists[i].appro_empname+'</td>'
					html += '<td>'+lists[i].appro_regdate+'</td>';
					html += '</tr>';
					$("#myDocList").append(html);
				} 
			}	
		}
	});
 }
 
function docClick(){
	var status = $("#statusSelect option:selected").val();
	var emp_id = document.getElementById("emp_id").value;
	console.log(status);
	console.log(emp_id);
	$.ajax({
		url : "./statusDocList.do",
		data : {
			"appro_status_no" : status,
			"emp_id" : emp_id
		},
		type : "GET",
		async : true,
		success : function(data){
			console.log(data);
			
			var tbody = document.getElementById("statusDocList")	//제거하고자 하는 엘리먼트
			while ( tbody.hasChildNodes() )
			{
				tbody.removeChild(tbody.firstChild );       
			}
			
			var obj = JSON.parse(data);
			var objlists = obj.lists;
			var guyljaejalists = obj.guyljaejaLists;
			
			for(let i=0; i<objlists.length; i++){
				console.log(objlists[i]);
		//		console.log(objlists[i].appro_empname);
				html = '';
				html += '<tr>';
				html += '<td>'+(i+1)+'</td>';
				html += '<td><a href="./selDocDetail.do?emp_id='+emp_id+'&appro_no='+objlists[i].appro_no+'">'+objlists[i].appro_no+'</td>';
				html += '<td>'+objlists[i].appro_status+'</td>';
				html += '<td>'+objlists[i].appro_title+'</td>';
				html += '<td>'+objlists[i].appro_empname+'</td>'
				html += '<td>'+objlists[i].appro_regdate+'</td>';
				html += '</tr>';
				$("#statusDocList").append(html);
			}

			if(status == 3){
				for(let i=0; i<guyljaejalists.length; i++){
		//			console.log(guyljaejalists[i]);
		//			console.log(guyljaejalists[i].appro_empname);
					html = '';
					html += '<tr>';
					html += '<td>'+(i+1)+'</td>';
					html += '<td><a href="./selDocDetail.do?emp_id='+emp_id+'&appro_no='+guyljaejalists[i].appro_no+'">'+guyljaejalists[i].appro_no+'</td>';
					html += '<td>'+guyljaejalists[i].appro_status+'</td>';
					html += '<td>'+guyljaejalists[i].appro_title+'</td>';
					html += '<td>'+guyljaejalists[i].appro_empname+'</td>'
					html += '<td>'+guyljaejalists[i].appro_regdate+'</td>';
					html += '</tr>';
					$("#statusDocList").append(html);
				}	
			}	
		}
	});
} 

$('#approList').DataTable({
    lengthChange: false, // 표시 건수기능 숨기기
    searching:false, // 검색 기능 숨기기
    ordering: false, // 정렬 기능 숨기기
    info: false, // 정보 표시 숨기기
    paging:true,// 페이징 기능 숨기기
	"language": {
        "emptyTable": "검색된 결과가 없어요.",
        "loadingRecords": "로딩중...",
        "processing":     "잠시만 기다려 주세요...",
        "paginate": {
            "next": "다음",
            "previous": "이전"
        }
    },
    displayLength: 5,
	scrollCollapse:true,
	scrollY:"145px",
	columnDefs: [
        { width: 40, targets: 0 }
    ]
});

$('#approStatusList').DataTable({
    lengthChange: false, // 표시 건수기능 숨기기
    searching:false, // 검색 기능 숨기기
    ordering: false, // 정렬 기능 숨기기
    info: false, // 정보 표시 숨기기
    paging:true,// 페이징 기능 숨기기
	"language": {
        "emptyTable": "검색된 결과가 없어요.",
        "loadingRecords": "로딩중...",
        "processing":     "잠시만 기다려 주세요...",
        "paginate": {
            "next": "다음",
            "previous": "이전"
        }
    },
    displayLength: 5,
	scrollCollapse:true,
	scrollY:"300px",
	columnDefs: [
        { width: 40, targets: 0 }
    ]
});
</script>
</html>