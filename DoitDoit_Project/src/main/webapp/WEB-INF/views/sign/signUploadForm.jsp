<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>

<title>사인이미지 업로드폼</title>
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
                    <div class="rContent-normal-top" style="width: 600px; height: 80px; margin-top: 10%;">
                    	<h2 style="text-align: center;">서명 이미지 업로드</h2>
                    </div>
                    <div class="rContent-normal-bottom" style="width: 600px; height: 500px; margin-top: 50px;">
                    	<div>
		                    <form:form action="./signUpload.do" method="post" enctype="multipart/form-data" modelAttribute="signVo" >
								<h3 style="text-align: center;">업로드할 서명 파일</h3>
								&nbsp;&nbsp;&nbsp;&nbsp;파일 : <input class="form-control form-control-sm" id="selectfile" type="file" name="filename" accept="image/png, image/jpeg, image/jpg" ><br>
								<p style="color: red; font-weight: bold;">
								</p>
								<h3 style="text-align: center;">업로드 할 서명</h3> 
								&nbsp;&nbsp;&nbsp;&nbsp;이미지 미리보기 : <br> 
								<div style=" width:400px; height: 200px; margin-left: 100px;">
								<img style="width: 200px; height: 200px; margin-left: 100px;" class="img" id="preview" /><input type="hidden" id="b64" name="sign_img"><br>
								</div>
								<div style="width: 400px; height: 80px; margin-left: 275px; margin-top: 20px;">
								<input type="submit" class="btn btn-default" value="업로드">
								</div>
							</form:form>			
                    	</div>
                    </div>
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
          <%--   <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal" var="emp_id"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize> --%>
            </div>
        </main>
    </div>
</body>
<script type="text/javascript">

const preview = document.getElementById("preview");
const b64 = document.getElementById("b64");
const selectfile = document.getElementById("selectfile");

//FileReader  File 혹은 Blob 객체를 이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 사용자의 컴퓨터에 저장
const uploadImage = async (event) => {
          const file = event.target.files[0];
          fileReader = new FileReader();
      	  fileReader.readAsDataURL(file);
      	  var base64; 
      	  fileReader.onload  = function(){
      		base64 =  fileReader.result
      		base64 = base64.toString()
      		console.log("---------------------",base64)
            preview.src = base64;
      		b64.value = base64;
      	  }
        };

        selectfile.addEventListener("change", (e) => {
            uploadImage(e);
        });

</script>
</html>