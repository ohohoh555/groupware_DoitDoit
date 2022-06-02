<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시글 상세보기</title>
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
		 
                <div id="rContent">
					<div class="rContent-full">
					
					<table class="table table-striped">
						<thead>
							<tr>
								<th colspan="2">
								작성자 : ${entrOne.emp_name} &nbsp; &nbsp;/ &nbsp; &nbsp;
								조회수 : ${entrOne.eboard_readcount} &nbsp; &nbsp;/ &nbsp;&nbsp; 
								등록일자 : ${entrOne.eboard_regdate}
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>분류</td>
								<td>${entrOne.cgory_no}</td>
							</tr>
							<tr>
								<td>제목</td>
								<td>${entrOne.eboard_title}</td>
							</tr>
							<tr>
								<td>내용</td>
								<td>${entrOne.eboard_content}</td>
							</tr>
						</tbody>
					</table>
						<div style="text-align: center;">
						   <sec:authorize access="isAuthenticated()">
					        <sec:authentication property="principal.emp_id" var="emp_id"/>
	           					<c:if test="${emp_id eq entrOne.emp_id}">
			           				<button onclick="modifyOne(${entrOne.eboard_no})" class="btn btn-default">수정</button>
									<button onclick="hideOne(${entrOne.eboard_no})" class="btn btn-default">삭제</button>
	           					</c:if>
	 			           </sec:authorize> 
						<button onclick="javascript:history.back(-1)" class="btn btn-default">확인</button>
						</div>
					</div> <!-- rContent-full 끝 -->
                </div>
            <%@include file="../comm/aside.jsp" %>   

            </div><!-- content 끝 -->
        </main>
    </div><!-- container 끝 -->
    
    
    
<script type="text/javascript">
function hideOne(eboard_no){
	console.log("hideOne 사용자 글숨김",eboard_no);
	var con = confirm("선택된 글을 삭제합니다. (복구는 관리자를 통해서만 가능합니다.)");
	if(con){
		location.href="./delflag.do?eboard_no="+eboard_no;
	}else{
		alert("글 삭제가 취소되었습니다.");
	}
}

function modifyOne(eboard_no){
	console.log("modifyOne 사용자 글수정",eboard_no);
	location.href="./modify.do?eboard_no="+eboard_no;
}

</script>
</body>
</html>