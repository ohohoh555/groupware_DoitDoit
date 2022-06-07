<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기(관리자)</title>
<%@include file="../comm/setting.jsp" %>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
					<div id="adminContent">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th colspan="2">
								작성자 : ${entrOne.emp_name} &nbsp; &nbsp;/ &nbsp; &nbsp;    
								조회수 : ${entrOne.eboard_readcount} &nbsp; &nbsp;/ &nbsp; &nbsp;    
								등록일자 : ${entrOne.eboard_regdate}  &nbsp; &nbsp;/ &nbsp; &nbsp;
								숨김여부 : ${entrOne.eboard_delflag}
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td width="100px;">분류</td>
								<c:choose>
									<c:when test="${entrOne.cgory_no=='101'}"><td>일반</td></c:when>
									<c:when test="${entrOne.cgory_no=='102'}"><td>필독</td></c:when>
									<c:when test="${entrOne.cgory_no=='103'}"><td>인사</td></c:when>
									<c:when test="${entrOne.cgory_no=='302'}"><td>일정</td></c:when>
								</c:choose>
							</tr>
							<c:if test="${entrOne.cgory_no =='302'}">
								<tr>
									<td>일시</td>
									<td>${entrOne.cald_start} ~ ${entrOne.cald_end}</td>
								</tr>
							</c:if>
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
							<c:choose>
								<c:when test="${entrOne.eboard_delflag eq 'N'}">
									<button onclick="changeDelOne(${entrOne.eboard_no})" class="btn btn-default">숨김처리</button>
								</c:when>
								<c:when test="${entrOne.eboard_delflag eq 'Y'}">
									<button onclick="changeDelOne(${entrOne.eboard_no})" class="btn btn-default">보임처리</button>
								</c:when>
							</c:choose>
								<button onclick="deletOne(${entrOne.eboard_no})"   class="btn btn-default">완전삭제</button>
								<button onclick="javascript:location.href='./entrBoardAdmin.do'" class="btn btn-default">목록</button>
						</div>
					</div>
            </div><!-- content 끝 -->
        </main>
    </div><!-- container 끝 -->
    
    
    
<script type="text/javascript">
function changeDelOne(eboard_no){
	alert("해당 글의 숨김여부를 변경하였습니다.");
	location.href="./changeDelOne.do?eboard_no="+eboard_no;
}

function deletOne(eboard_no){
	var con = confirm("해당 글을 완전히 삭제하시겠습니까? (DB에서 삭제되어 복구가 불가능합니다.)");
	if(con){
		location.href = "./deletOne.do?eboard_no="+eboard_no;
	}
}

</script>    
</body>
</html>