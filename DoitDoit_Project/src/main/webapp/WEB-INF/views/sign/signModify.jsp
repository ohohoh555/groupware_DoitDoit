<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>서명관리 화면</title>
<%@include file="../comm/setting.jsp" %>

<style type="text/css">
.signLists{
	width: 550px;
	height: 250px;
	border:2px solid  #6667AB;
	margin-left: 25px;
	margin-top: 50px;
	margin-bottom: 50px;
}
#images> td{
	width: 180px;
	height: 180px;
	margin-left: 40px;
}
#images> td > img{
	margin : 20px;
	border: 1px solid #6667AB;
	border-radius: 10%;
}


</style>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                    <div>
             	 	  <img onclick="javascript:history.back(-1);" alt="돌아가기" style="cursor: pointer; margin: 10px;" src="./img/sign/back.png">
               		 </div>
                    <div class="rContent-normal-top" style="width: 600px; height: 100px; margin-top: 10%;">
                    	<h2 style="text-align: center; ">나의 서명 이미지</h2>
                    </div>
                    <div class="rContent-normal-bottom" style="width: 600px; height: 500px;">
	                   <div class="signLists">
	                   <table class="signTable">
	                   <thead>
	                   <tr id="names">
	                   </tr>
	                   </thead>
	                   <tbody>
	                   <tr id="images">
	                   </tr>
	                   </tbody>
	                   <tfoot>
	                   <tr id="buttons">
	                   </tr>
	                   </tfoot>
	                   </table>
	                   </div>
	                   <div style="text-align: center;" >
	                   <input type="button" class="btn btn-danger" value="새로운 서명 이미지 추가" onclick="location.href='./signUploadForm.do';">
	                   </div>
                    </div> 
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
         <%--    <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal" var="emp_id"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize> --%>
            <input type="hidden"  id="emp_id" value="${emp_id}">
            <input type="hidden"  id=defaultsign value="기본서명.png">
            </div>
        </main>
    </div>
</body>
<script type="text/javascript">
var emp_id = document.getElementById("emp_id").value;
console.log(emp_id);
window.onload = function signList(){
	console.log("서명리스트 아작스 시작");
	$.ajax({
		url : "./signList.do",
		data : {
			"emp_id" : emp_id
		},
		type : "GET",
		async : true,
		success : function(data){
	//		console.log("성공");
			var obj = JSON.parse(data);
	//		console.log(data);
	//		console.log(obj.images);
	//		console.log(obj.names);
			var images = obj.images;
			var names = obj.names;
			
			for(let j=0; j< names.length; j++){
				$("#names").append('<td><input type="hidden" value="'+names[j]+'"></td>');
				$("#buttons").append('<td style="text-align: center; "><input type="button" id="removebtn" class="btn btn-info -sm" value="삭제"><input type="hidden" value="'+names[j]+'"></td>');
			}
			for(let i=0; i< images.length; i++){
				$("#images").append('<td><img style="width: 150px; height: 150px;" src="'+images[i]+'"></td>');
			}
			
			
		}
	});
}

$(document).on("click","#removebtn",function (){ 
	var namesign = $(this).next().val();
	var n = new String(namesign);
	console.log(namesign,typeof namesign);
	var design = document.getElementById("defaultsign").value;
	console.log(design,typeof design);
	var d = new String(design);
	console.log(d === n);
	if(namesign === '기본서명.png'){
	console.log($(this).next().val());
	console.log("같다");
	}
});

/* btn.onclick = function(){
	console.log("click 동작 시작");
	$(this).next().val();
	console.log($(this).next().val());
}; */
/* function remove(){
	console.log("서명 삭제 시작");
	var btn = document.getElementById("removebtn").next();
	console.log(btn);
} */

</script>
</html>