<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인 페이지</title>
	<link rel="stylesheet" type="text/css" href="./css/loginPage.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 베리 페리 색상 코드 : #6667AB -->
    <div id="container">
        <form action="./login.do" method="post" id="loginForm">
            <div class="loginLogo"></div>
            <div class="loginAlert">
                <span>${msg}</span>
            </div>
            <label class="switch-button">
				<input name="remember-me" type="checkbox" value="true">
			    <span class="onoff-switch"></span>
			</label>
			<span class="rememberMe">Remember-Me</span>
            <input id="loginId" name="username" type="text" placeholder="계정" required="required" pattern="[0-9]+">
            <input id="loginPw" name="password" type="password" placeholder="비밀번호" required="required">
            <input id="loginBtn" type="submit" value="로그인">
            <input id="nfcBtn" type="button" onclick="nfcWork()" value="출/퇴근">
        </form>
    </div>
    
	<!-- 출퇴근 등록 모달 -->
	<div class="modal fade" id="workModal" role="dialog">
	  <div class="modal-dialog modal-md" style="width: 500px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title" style="text-align: center;">출/퇴근 등록</h4>
	      </div>
	      <div class="modal-body">
	        	<table class="table table-bordered">
	        		<tr>
		       			<td style="text-align: center;">
			      			<input type="password" id="empNfc" name="emp_nfc" onkeypress="nfc(event)">
		       			</td>
	        		</tr>
	        	</table>
	      </div>
	    </div>
	  </div>
	</div><!-- 모달 영역 끝 -->    
</body>
<script type="text/javascript">
function nfcWork(){
	$("#empNfc").val("");
	$("#workModal").modal();
	$("#workModal").on("shown.bs.modal", function () {
		$("#empNfc").focus();
	})
}
function nfc(e) {
	if(e.keyCode==13){
		var emp_nfc = $("#empNfc").val();
		$.ajax({
			url:"./annualWork.do",
			type:"post",
			data:{"emp_nfc":emp_nfc},
			success:function(data){
				if(data=="문자"){
					alert("잘못된 nfc 형식입니다.");
					$("#empNfc").val("");
				}else if(data=="0"){
					alert("등록되지 않은 nfc 번호입니다");
					$("#empNfc").val("");
				}else{
					alert("출/퇴근이 등록되었습니다.");
					$("#workModal").modal("hide");
				}
			}
		});
	}
}
</script>
</html>