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
                    	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#appro">승인</button>
                    	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#approReturn">반려</button>
						</c:if>
                    	<input type="button" class="btn btn-default btn-sm"  onclick="javascript:history.back(-1)" value="뒤로가기">
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
						<tr id="approGianja" >
						<td class="tableForm" rowspan="3" style="writing-mode: vertical-rl; text-orientation: upright;  width:30px;">결재라인</td>
						<td class="tableForm">기안자</td>
						</tr>
						<tr >
						<td id="sign" height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src=""><input type="hidden" id="gianja" value="${aVo.emp_id}"></td>
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
<div class="modal fade" id="appro" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>결재승인</h4>
			</div>
			    <div class="modal-body" id="approModalbody">
			  <!--   <form action="post" id="frmApproClick"> -->
			    <div id="gyuljaeSign" >
			    <div style="width: 100px; height: 100px; border:1px solid black; border-radius: 10px;">
			    	 <img style=" width: 90px; height: 90px; margin: 5px; " id="gyuljaeSignImg" alt="" src="">
			    </div>
			    	
			    </div>
		<!-- 	    </form> -->
			    </div>
		</div>
	</div>
</div>

<!-- 반려버튼 클릭 모달 -->
<div class="modal fade" id="approReturn" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>결재반려</h4>
			</div>
			    <div class="modal-body">
			  <!--   <form action="post" id="frmApproClick"> -->
			    <div id="approClick">
			   
			    	
			    </div>
		<!-- 	    </form> -->
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
		$("#approGianja").append('<td class="tableForm">결재자</td>');
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
			$("#sign").after('<td height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src="'+data+'"></td>');
						}
					},
					error : function(){
						console.log("실패	ㅠㅠ");
					}
				});
			}else{
		$("#sign").after('<td height="70px;">&nbsp;&nbsp;</td>');
			}
		var name = obj[i].APPRO_NAME;
		$("#name").append('<td style="text-align: center;">'+name+'</td>');
	}
}	

//승인 버튼 클릭시 모달에 서명 이미지 로드하기
$("#approModalbody").load(gyuljaeSignClick());
		
function gyuljaeSignClick(){
	console.log("결재자 승인모달");
	$.ajax({
		url : "./viewImg.do?",
		data : {
			"emp_id" : ${loginEmp_id}
		},
		type : "GET",
		async : true ,
		success : function(data){
			if(data.length > 0 && data != null){
				document.getElementById("gyuljaeSignImg").src = data;
				$("#gyuljaeSign").append("<br><button style='margin-left:30px;' onclick='gyuljaeClick()'>선택</button>");
			}
		},
		error : function(){
			console.log("실패	ㅠㅠ");
		}		
	});			
}

function gyuljaeClick(){
	console.log("결재선택 시작");
	console.log(approlineList);
	console.log(${aVo.appro_line_no });
	$.ajax({
		url : "./guyljaejaApprove.do",
		data : {
			"approlineList" : approlineList,
			"appro_line_no" : ${aVo.appro_line_no },
			"emp_id" : ${loginEmp_id}
		},
		type : "GET",
		async : true,
		success : function(data){
			console.log("아작스 성공했는지~~")
			console.log(data)
			if(data == "true"){
				alert("승인되었습니다!");
				location.href="./approMain.do";
			}
		}
	});
	
	//승인시 연차 사용
// 	var iscAnn = $("table tr td:eq(16)").text();//연차 판단
// 	var annPeriod = $("table tr td:eq(18)").text();//사용 기간
// 	var annDays = $("table tr td:eq(22)").text();//연차 신청일수
// 	console.log("승인 emp_id="+data);
// 	if(iscAnn=="연차"){
// 		$.ajax({
// 			url:"./test.do",
// 			type:"post",
// 			data:{"use_days":annDays, "use_period":annPeriod, "emp_id" : data},
// 			success : function() {
			
// 			}
// 		});
// 	}	
}
</script>
</html>