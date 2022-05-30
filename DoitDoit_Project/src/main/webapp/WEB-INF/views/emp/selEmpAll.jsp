<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세 조회</title>
<link rel="stylesheet" type="text/css" href="./css/home.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" 
href="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.css"/>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.11.5/datatables.min.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            	<div id="adminContent">
            	<input id="insEmpBtn" type="button" value="회원등록" class="btn btn-info" onclick="location.href='./insEmpPage.do'">
            	<table id="dataTable" class="stripe">
            		<thead>
            			<tr>
            				<th>NO</th>
            				<th>사번</th>
            				<th>이름</th>
            				<th>부서</th>
            				<th>직급</th>
            			</tr>
            		</thead>
            		<tbody>
            			<c:forEach var="mVo" items="${lists}" varStatus="vs">
            				<tr>
            					<td>${vs.count}</td>
            					<td>${mVo.emp_id}</td>
            					<td><a href="#">${mVo.emp_name}</a></td>
            					<td>${mVo.dept_no}</td>
            					<td>${mVo.rank_no}</td>
            				</tr>
            			</c:forEach>
            		</tbody>
            	</table>
            	</div>  
            </div>
        </main>
    </div>
    <script type="text/javascript">
$(document).ready( function () {
    $('#dataTable').DataTable({
		//https://datatables.net/reference/option/language
		// DataTable은 기본적으로 영어로 표시되기 때문에 별도로 language를 통해서 변경해줘야 함
    	"language": { 
            "emptyTable": "데이터가 없어요.",
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
            "info": "현재 _START_ - _END_ / _TOTAL_건",
            "infoEmpty": "데이터 없음",
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 데이터가 없어요.",
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        
        lengthChange: true, // 표시 건수기능 숨기기
        searching: true, // 검색 기능 숨기기
        ordering: true, // 정렬 기능 숨기기
        info: true, // 정보 표시 숨기기
        paging:true, // 페이징 기능 숨기기
        order: [ [ 3, "asc" ], [ 1, "desc"] ], //초기표기시 정렬, 만약 정렬을 안하겠다 => order: []
     columnDefs: [{ "targets": "1", "width": "50px" }], //열의 넓이 조절 
//		lengthMenu: [ 10, 20, 30, 40, 50 ], //표시건수 
//      displayLength: 50, //기본표시건수 설정
        pagingType: "simple_numbers" // 페이징 타입 설정 : simple, simple_numbers, full_numbers 등
    
    });
} );
</script>
    
</body>
</html>