<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/comm/updPassPhone.do" method="post">
		<label for="emp_password">비밀번호 :</label>
		<input type="password" name="emp_password" placeholder="변경할 비밀번호를 입력하세요">
		<label for="emp_phone">휴대폰번호 :</label>
		<input type="text" maxlength="13" onKeyup="inputPhoneNumber(this);" name="emp_phone" placeholder="휴대폰 번호를 입력하세요">
		<input type="submit" value="입력">
		<input type="button" value="홈으로가기" onclick="location.href='${pageContext.request.contextPath}/comm/loginPage.do'">
	</form>
	<script>
	function inputPhoneNumber(obj) {
	
	    var number = obj.value.replace(/[^0-9]/g, "");
	    var phone = "";
	
	    if(number.length < 4) {
	        return number;
	    } else if(number.length < 7) {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3);
	    } else if(number.length < 11) {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3, 3);
	        phone += "-";
	        phone += number.substr(6);
	    } else {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3, 4);
	        phone += "-";
	        phone += number.substr(7);
	    }
	    obj.value = phone;
	}
</script>
</body>
</html>