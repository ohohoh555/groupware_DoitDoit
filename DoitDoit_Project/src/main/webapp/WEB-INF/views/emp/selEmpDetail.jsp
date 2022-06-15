<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" type="text/css" href="./css/selEmp.css">
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
            	<form action="./upEmpDetail.do" method="POST" id="selDetailFrm">
            		<div class="profile"></div>
            		<c:forEach items="${lists}" var="empVo">
            		<div class="inputUser">
            			<label for="emp_id">사번 :</label>
            			<input type="text" class="selEmp" id="emp_id" name="emp_id" value="${empVo.emp_id}" readonly>
            			<label for="emp_name">이름 :</label>
            			<input type="text" class="selEmp" name="emp_name" value="${empVo.emp_name}" readonly>
            			<label for="emp_email">이메일 :</label>
            			<input type="text" class="selEmp" name="emp_email" value="${empVo.emp_email}" readonly>
            			<label for="dept_no">부서 :</label>
            			<input type="text" class="selEmp" name="dept_no" value="${empVo.dept_no}" readonly>
            			<label for="rank_no">직급 :</label>
            			<input type="text" class="selEmp" name="rank_no" value="${empVo.rank_no}" readonly>
            			<label for="emp_regdate">입사일 :</label>
            			<input type="text" class="selEmp" name="emp_regdate" value="${empVo.emp_regdate}" readonly>
            		</div>
            		<div class="inputAdd">
            			<label for="emp_address">주소 :</label>
            			<input type="text" class="selEmp" name="emp_address" value="${empVo.emp_address}" readonly>
            			<label for="emp_nfc">NFC :</label>
            			<input type="text" class="selEmp" name="emp_nfc" value="${empVo.emp_nfc}" readonly>
            		</div>
            		</c:forEach>
            		<div class="buttonArea">
            			<input class="btn btn-default" type="submit" value="수정하기">
            			<input class="btn btn-default" type="button" value="비밀번호 초기화" onclick="resetPassword()">
            			<input class="btn btn-default" type="button" value="뒤로가기" onclick="history.back(-1)">
            		</div>
            	</form>
            </div>
        </main>
    </div>
    <script type="text/javascript">
    	var emp_id = $("#emp_id").val();
    	
    	function resetPassword(){
    		$.ajax({
        		url:"./resetPassword.do",
        		data:"emp_id=" + emp_id,
        		method:"GET",
        		success:function(msg){
        			console.log(msg);
        			alert("비밀번호가 초기화 되었습니다");
        			location.href="./selAllEmp.do";
        		},
        		error:function(){
        			
        		}
        	})
    	}
    
    	
    </script>
</body>
</html>