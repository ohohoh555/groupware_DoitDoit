<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판(사원)</title>
</head>
<body>
<h1>커밋테스트</h1>
	<table id="EntrTable" class="display compact">
		<thead>
			<tr>
				<td>번호<td>
				<td>분류<td>
				<td>제목<td>
				<td>작성자<td>
				<td>날짜<td>
				<td>조회<td>
			</tr>
		</thead>
		<tbody>
			
		
		</tbody>
	</table>



<script type="text/javascript">
$(document).ready(function(){
	$("#EntrTable").DataTables({
		
	});
});
</script>

</body>
</html>