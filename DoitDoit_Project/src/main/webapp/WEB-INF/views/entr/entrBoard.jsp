<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판(사원)</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/entr/entrBoard.js"></script>
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
						<h3>&lt;&lt;공지게시판&gt;&gt;</h3>
						<hr>
						<div id="FilDiv">
						<table id="FildokTable" class="stripe">
							<thead>
								<tr>
									<th>No.</th>
									<th>분류</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>조회</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="fVo" items="${fList}">
									<tr>
										<td>★</td>
										<td>[필독]</td>
										<td><a href="./OneBoard.do?eboard_no=${fVo.eboard_no}">${fVo.eboard_title}</a></td>
										<td>${fVo.emp_name}</td>
										<td>${fVo.eboard_regdate}</td>
										<td>${fVo.eboard_readcount}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="6" style="text-align: center;"><ul><li onclick="fildokAll()">&lt;&lt;[필독]게시글 전체보기&gt;&gt;</li></ul></td>
								</tr>
							</tfoot>
						</table>
						</div>
						<hr>
						<div id="EntrDiv">
						<table id="EntrTable" class="stripe">
							<thead>
								<tr>
									<th>No.</th>
									<th width="30px">분류</th>
									<th>제목</th>
									<th width="40px">작성자</th>
									<th width="70px;">등록일</th>
									<th width="25px ">조회</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="eVo" items="${eList}" varStatus="vs">
									<tr>
										<td>${vs.count}</td>
										<td>${eVo.cgory_no}</td>
										<td><a href="./OneBoard.do?eboard_no=${eVo.eboard_no}">${eVo.eboard_title}</a></td>
										<td>${eVo.emp_name}</td>
										<td>${eVo.eboard_regdate}</td>
										<td>${eVo.eboard_readcount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
							<button onclick="javascript:location.href='./entrBoard.do'" class="btn btn-default">전체</button>
							<button value="101" onclick="cgoryAction(this.value)" class="btn btn-default">일반</button>
							<button value="103" onclick="cgoryAction(this.value)" class="btn btn-default">인사</button>
							<button value="302" onclick="cgoryAction(this.value)"class="btn btn-default"> 일정</button>
							<button onclick="insertBoard()" class="btn btn-success">글쓰기</button>
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