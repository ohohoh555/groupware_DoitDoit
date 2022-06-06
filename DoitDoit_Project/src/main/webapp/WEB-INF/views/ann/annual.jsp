<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연차 현황</title>
</head>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/ann/annual.js"></script>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
						<div style="height: 28%">
							<h3 style="text-align: center;">연차 현황</h3>
							<hr>
							<table class="table" style="text-align: center; border-bottom-style: none; border-top-style: none; border: none;">
								<tr>
									<td style="font-size: 50pt; color: #6667AB;">
										${annualVo.ann_add}
									</td>
									<td style="font-size: 50pt; color: #6667AB;">
										${annualVo.ann_use}
									</td>
									<td style="font-size: 50pt; color: #6667AB;">
										${annualVo.ann_rest}
									</td>
								</tr>
								<tr>
									<td>발생</td>
									<td>사용</td>
									<td>잔여</td>
								</tr>
							</table>
						</div>
						<hr style="border: 1px solid black">
						<div style="height: 28%;">
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
						<hr style="border: 1px solid black">
						<div style="height: 28%">
							<h3 style="text-align: center;">사용 연차</h3>
							<table id="annUseTable" class="table table-bordered" style="text-align: center;">
								<thead>
									<tr>
										<th  style="text-align: center;">NO</th>
										<th  style="text-align: center;" data-orderable="false">이름</th>
										<th  style="text-align: center;" data-orderable="false">사용 내용</th>
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
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>