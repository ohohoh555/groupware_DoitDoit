<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통합검색</title>
<%@include file="../comm/setting.jsp" %>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
					<h3>&lt;&lt;통합검색&gt;&gt;</h3>
						<div style="display: table;" >
							<div style="float: left;">	
								<select class="form-control" style="width: 100px;">
									<option>문서명</option>
									<option>내용</option>
								</select>
							</div>
							<div style="float: left;"><input type="text" class="form-control" width="70%" id="keyword"></div>	
							<div style="float: left;"><input type="button" class="btn btn-success" value="검색"></div>	
						</div>
					</div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>