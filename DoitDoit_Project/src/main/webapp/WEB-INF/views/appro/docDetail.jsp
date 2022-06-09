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
<script src="./js/appro/jspdf.min.js"></script> 
<script src="./js/appro/html2canvas.js"></script>
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
						<c:set var="aStatus" value="${aVo.appro_status_no }"/>
						<c:choose>
							<c:when test="${aStatus eq 1 }">
									결재 대기 문서
							</c:when>
							<c:when test="${aStatus eq 2 }">
									반려 문서
							</c:when>
							<c:when test="${aStatus eq 3 }">
									결재 완료 문서
							</c:when>
							<c:when test="${aStatus eq 4 }">
									임시 저장 문서
							</c:when>
						</c:choose>
						</legend>
						<c:if test="${loginEmp_id ne aVo.emp_id and aStatus eq 1}">
                    	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#appro">승인</button>
                    	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#approReturn">반려</button>
						</c:if>
						<c:if test="${aStatus eq 3}">
                    	<button type="button" class="btn btn-default btn-sm" onclick="docToPdf()" >문서출력</button>
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
						<tr id="sign" >
						<td height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src=""><input type="hidden" id="gianja" value="${aVo.emp_id}"></td>
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
			    <div id="approClick">
			    	<h6>반려사유 선택</h6>
			    	<ul>
			    		<li><input class="chk" type="checkbox"><span>주요사항 기재 누락으로 인한 반려</span></li>
			    		<li><input class="chk" type="checkbox"><span>오기입으로 인한 반려</span></li>
			    		<li><input class="chk" type="checkbox"><span>기안자의 요청으로 인한 반려</span></li>
			    		<li><input class="chk" type="checkbox"><span>기타 : <textarea class="form-control" style="width: 260px; height: 100px;"></textarea></span></li>
			    		<li style="margin-left: 40%; margin-top: 5px;" ><button class="btn btn-default" onclick="returnAppro()">선택</button></li>
			    	</ul>
			    </div>
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

		if(status == 'N'){
			$("#sign").append('<td  style="text-align: center; height : 70px;"><p style="font-size: 20px;">반려</p></td>');	
		}else if(status == 'Y'){
			$("#sign").append('<td height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src="'+obj[i].SIGN+'"></td>');
		}else{
			$("#sign").append('<td height="70px;">&nbsp;&nbsp;</td>');
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
	
	gyuljaeClick2(approlineList);
	
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

}

//반려 버튼 클릭시 
function returnAppro(){
	
	var chkList = document.querySelectorAll("input[class=chk]:checked");
	var returnList = new Array();
	
	chkList.forEach(function(ch){
		console.log(ch.parentNode.lastChild.innerHTML);
		returnList.push(ch.parentNode.lastChild.innerHTML); 
	})
		console.log("for문 밖의 : ",returnList);
		console.log(typeof returnList);
		console.log(returnList.toString());
	$.ajax({
		url : "./guyljaejaReturn.do",
		data : {
			"approlineList" : approlineList,
			"appro_line_no" : ${aVo.appro_line_no },
			"emp_id" : ${loginEmp_id},
			"approReturnreason" : returnList.toString()
		},
		type : "GET",
		async : true,
		success : function(data){
			console.log(data);
			if(data == "true"){
				alert("반려되었습니다!");
				location.href="./approMain.do";
			}
		}
	});
}
	
//완료문서 pdf출력
function docToPdf() {
	console.log("pdf시작");
	var pdfToDoc = document.getElementsByClassName("rContent-normal-top")[0];
//	console.log(pdfToDoc);
	html2canvas(pdfToDoc).then(function(canvas) {
		//캔버스를 이미지로 변환
		let imgData = canvas.toDataURL('image/png'); 
		let margin = 3; // 출력 페이지 여백설정
		let imgWidth = 210; // 이미지 가로 길이(mm) A4 기준
		let pageHeight = imgWidth * 1.414; // 출력 페이지 세로 길이 계산 A4 기준
		let imgHeight = canvas.height * imgWidth / canvas.width;
		let heightLeft = imgHeight;
		let position = margin;			
		
		//pdf생성
		let doc = new jsPDF('p', 'mm');
		
		
		doc.addImage(imgData, 'PNG', margin, position, imgWidth, imgHeight);
		heightLeft = pageHeight;
		
	
		//파일명을 날짜에서 초+밀리세컨드.pdf로 생성하게합니다
		var day = new Date();
		let s = day.getSeconds();
		let ms = day.getMilliseconds();
		
		//파일을 저장합니다
		doc.save("page"+s+ms+".pdf");
	});

}
</script>
</html>