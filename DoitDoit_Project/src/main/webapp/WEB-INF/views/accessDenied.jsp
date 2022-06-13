<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Access Denied</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta http-equiv="refresh" content="5; url=${pageContext.request.contextPath}/comm/logout.do">
<meta charset="UTF-8">
<style type="text/css">
body{
	background-color: black;
	color: white;
}

h1 {
	color: red;
}

h6{
	color: red;
	text-decoration: underline;
}
	
</style>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div class="w3-display-middle">
<h1 class="w3-jumbo w3-animate-top w3-center"><code>접근 권한이 없습니다!</code></h1>
<hr class="w3-border-white w3-animate-left" style="margin:auto;width:50%">
<h3 class="w3-center w3-animate-right">관리자에게 문의하세요</h3>
<h3 class="w3-center w3-animate-zoom">🚫🚫🚫🚫</h3>
<h6 class="w3-center w3-animate-zoom">error code:403 forbidden</h6>
</div>
</body>
</html>