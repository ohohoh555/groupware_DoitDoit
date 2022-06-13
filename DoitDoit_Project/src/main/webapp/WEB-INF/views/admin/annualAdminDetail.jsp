<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연차 관리</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/ann/annual.js"></script>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            	<div id="adminContent">
            		<div style="height: 43%">
            		<h3 style="text-align: center;">발생 연차</h3>
						<table id="annAddTable" class="table table-bordered" style="text-align: center;">
							<thead>
								<tr>
									<th  style="text-align: center;">NO</th>
									<th  style="text-align: center;" data-orderable="false">이름</th>
									<th  style="text-align: center;" data-orderable="false">발생 내용</th>
									<th  style="text-align: center;" data-orderable="false">발생 연차</th>
									<th  style="text-align: center;" data-orderable="false">발생일</th>								
								</tr>
							</thead>
							<tbody>
								<c:forEach var="vo" items="${annAddVo}" varStatus="vs">
								<tr>
									<td>${vs.count}</td>	
									<td>${vo.emp_name}</td>										
									<td>${vo.ann_add_content}</td>	
									<td>${vo.ann_add_days}</td>
									<fmt:parseDate value="${vo.ann_add_date}" var="date" pattern="yyyy-MM-dd"/>
									<td><fmt:formatDate value="${date}" pattern="yyyy년MM월dd일"/></td>	
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
            	<hr style="border: solid 1px #6667AB;">
            	<hr>
            		<div style="height: 43%">
            		<h3 style="text-align: center;">생성 연차</h3>
						<table id="annUseTable" class="table table-bordered" style="text-align: center;">
							<thead>
								<tr>
									<th  style="text-align: center;">NO</th>
									<th  style="text-align: center;" data-orderable="false">이름</th>
									<th  style="text-align: center;" data-orderable="false">반차/연차</th>
									<th  style="text-align: center;" data-orderable="false">사용 연차</th>
									<th  style="text-align: center;" data-orderable="false">사용 기간</th>
									<th  style="text-align: center;" data-orderable="false">사용일</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="vo" items="${annUseVo}" varStatus="vs">
								<tr>
									<td>${vs.count}</td>	
									<td>${vo.emp_name}</td>	
									<td>${vo.ann_use_content}</td>	
									<td>${vo.ann_use_days}</td>
									<td>${vo.ann_use_period}</td>
									<fmt:parseDate value="${vo.ann_use_date}" var="date" pattern="yyyy-MM-dd"/>
									<td><fmt:formatDate value="${date}" pattern="yyyy년MM월dd일"/></td>	
								</tr>
								</c:forEach>
							</tbody>
						</table>    
					</div>        	
            	</div>  
            </div>
        </main>
    </div>
</body>
</html>