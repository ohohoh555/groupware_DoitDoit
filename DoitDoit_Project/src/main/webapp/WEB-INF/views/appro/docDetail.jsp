<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재문서 상세보기</title>
<%@include file="../comm/setting.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
#docForm{
	width: 800px;
	height: 500px;
}
table{
	border: 1px solid black;
}
td{
	border: 1px solid black;
}
.tableForm{
	background-color: #ccc;
	text-align: center;
	width: 100px;
}
.head{
	width: 780px;
	height: 30%;
	
}
.left{
	float: left;
}
.right{
	float: right;
}
</style>
</head>
<% Date now= new Date(); 
SimpleDateFormat sf = new SimpleDateFormat("yyyy년MM월dd일");
String today = sf.format(now);
%>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                    
                    <div class="rContent-normal-top" style="width: 780px; height: 780px; margin-top: 10px;">
                    <fieldset style="width:770px;">
						<legend style="margin-bottom : 3px;">
						결재 대기 문서
						</legend>
						<c:if test="${loginEmp_id ne aVo.emp_id}">
                    	<input type="button" data-toggle="modal" data-target="#approClick" value="승인">
                    	<input type="button" onclick="return()" value="반려">
						</c:if>
                    	<input type="button" onclick="javascript:history.back(-1)" value="뒤로가기">
                    	<input type="hidden" id="empId" value="${loginEmp_id}">
					</fieldset>
                    <div >
                    <div class="head">
						<div class="left">
						<table >
						<tr>
						<td class="tableForm">문서번호</td>	
						<td width="150px;" height="25px;"style="text-align: center;">${aVo.appro_no }</td>	
						</tr>
						<tr>
						<td class="tableForm">기안자</td>	
						<td width="150px;" height="25px;" style="text-align: center;">${aVo.appro_empname }</td>	
						</tr>
						<tr>
						<td class="tableForm">기안일</td>	
						<td width="150px;" height="25px;" style="text-align: center;">${aVo.appro_regdate }</td>	
						</tr>
						<tr>
						<td class="tableForm">제목</td>	
						<td width="150px;" height="25px;" style="text-align: center;">${aVo.appro_title }</td>	
						</tr>
						</table>
						</div>
						<div>
						</div>
						<div class="right">
						<table style="margin-right: 10px;">
						<tr id="appro" >
						<td class="tableForm" rowspan="3" style="writing-mode: vertical-rl; text-orientation: upright;  width:30px;">결재라인</td>
						<td class="tableForm">기안자</td>
						</tr>
						<tr>
						<td height="70px;" id="sign"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src=""><input type="hidden" id="gianja" value="${aVo.emp_id}"></td>
						</tr>
						
						<tr id="name">
						<td style="text-align: center;">${aVo.appro_empname }</td>
						</tr>
						</table>
						</div>
						<div>
						${aVo.appro_content }
						</div>
						</div>						
                    </div>
                    </div>
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
         
            </div>
        </main>
    </div>
    
        
<!-- 승인버튼 클릭 모달 -->
<div class="modal fade" id="approClick" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>결재승인</h4>
			</div>
			    <div class="modal-body">
			    <form action="post" id="frmApproClick">
			    <div id="approClick">
			    	
			    	
			    </div>
			    </form>
			    </div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
window.onload = function viewImg(){
	console.log("뷰이미지 셀렉트 함수 시작");
	var emp_id = document.getElementById("gianja").value;
	$.ajax({
		url :"./viewImg.do?",
		data : {
			"emp_id" : emp_id
		},
		type : "GET",
		async : true ,
		success : function(data){
		//	console.log(data);
		//	console.log("성공인지?");
			
			if(data.length > 0 && data != null){
				document.getElementById("image").src = data;
			}
		},
		error : function(){
			console.log("실패	ㅠㅠ");
		}
			
	});
	viewApproLine();
}

	var approlineList = '${aVo.appro_line}';
	var json = JSON.parse(approlineList);
// 결재리스트 td 생성
function viewApproLine(){
	var obj = json.approval;
	for(let i=0;i<obj.length;i++){
		$("#appro").append('<td class="tableForm">결재자</td>');
		var status = obj[i].APPRO_STATUS;
		var empId = obj[i].EMP_ID;
	//	console.log(status,typeof status);
	//	console.log(status == 'Y');
		if(status == 'Y'){
				$.ajax({
					url :"./viewImg.do?",
					data : {
						"emp_id" : empId
					},
					type : "GET",
					async : true ,
					success : function(data){
					//	console.log(data);
					//	console.log("성공인지?");
						if(data.length > 0 && data != null){
							//document.getElementById("image").src = data;
			$("#sign").after('<td height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" alt="결재자사인" src="'+data+'"></td>');
						console.log(status);
						console.log(data);
						}
					},
					error : function(){
						console.log("실패	ㅠㅠ");
					}
				});
			}else if(status != 'Y'){
		$("#sign").after('<td height="70px;">&nbsp;&nbsp;</td>');
						console.log(status);
			}
		var name = obj[i].APPRO_NAME;
		$("#name").append('<td style="text-align: center;">'+name+'</td>');
	}
}	
$(".modal-body").load("./viewImg.do");

var approLine = function(){
	var EmpId = document.getElementById("empId").value;
	$.ajax({
		url : "./viewImg.do?",
		data : {
			"emp_id" : EmpId
		},
		type : "GET",
		async : true ,
		success : function(data){
		//	console.log(data);
		//	console.log("성공인지?");
			
			if(data.length > 0 && data != null){
			//	document.getElementById("image").src = data;
				$("#frmApproClick").html(data);
			}
		},
		error : function(){
			console.log("실패	ㅠㅠ");
		}		
	});	
	
//	var approlineList = '${aVo.appro_line}';
//	console.log(approlineList, typeof(approlineList));
//	var json = JSON.parse(approlineList);
	var obj = json.approval;
//	console.log(obj);
	console.log(obj.length);
	console.log(json.approval);
	console.log(json.approval[0]);
	console.log(json.approval[1]);
	for(let i=0; i< obj.length; i++){
		console.log(obj[i]);
		console.log(obj[i].EMP_ID);
		console.log(obj[i].APPRO_STATUS);
		console.log(obj[i].APPRO_NAME);
	}
}	

function appro(){
	console.log("승인 버튼 ");
	var EmpId = document.getElementById("empId").value;
	console.log(EmpId);
	var obj = json.approval;
	console.log(obj);
	for(let i=0;i<obj.length;i++){
		var approStatus = obj[i].APPRO_STATUS;
		var approEmpId = obj[i].EMP_ID;
		if(approStatus == 'W' && approEmpId == EmpId){
			
			console.log(approStatus);
			console.log(approStatus == 'W');
			console.log(approEmpId);
			console.log(approEmpId == EmpId);
			console.log("같은가");
			approLine();
			$("#approClick").modal({backdrop:'static',keyboard:false});
		}
	}
}
	
</script>
</html>