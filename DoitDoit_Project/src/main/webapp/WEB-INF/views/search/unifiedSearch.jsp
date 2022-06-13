<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통합검색</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./js/search/unifiedSearch.js"></script>
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
					<h3>통합검색</h3>
						<div style="float: left;">	
								<select class="form-control" style="width: 100px;" name="searchOp" >
									<option value="uniTitle">문서명</option>
									<option value="uniContent">내용</option>
								</select>
						</div>
						<div style="float: left;"><input type="text" class="form-control" width="70%" id="searchWord"></div>	
						<div style="float: left;"><button class="btn btn-success" onclick="searchAction()">검색</button></div>	
<!-- 					<div><h3>총 <span class="label label-default" id="boardCnt"></span> 건의 게시글이 있습니다</h3></div> -->
					
					<div>
					<br>
					<br>
					<br>
					<div class="panel panel-default">
						<div class="panel-heading"><h4>게시판&nbsp;<span class="badge" id="entrCnt">0</span></h4></div>
						<div class="panel-body" style="padding: 0px">
						<table id="entrResult" class="stripe dataTable">
						<thead>
							<tr>
								<th width="150px;">제목</th>
								<th>미리보기</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					</div>
					</div><!--panel panel-default 끝  -->
					
					<div class="panel panel-default">
						<div class="panel-heading"><h4>전자결재&nbsp;<span class="badge" id="approCnt">0</span></h4></div>
						<div class="panel-body" style="padding: 0px">
						<table id="approResult" class="stripe dataTable">
						<thead>
							<tr>
								<th width="150px;">제목</th>
								<th>미리보기</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					</div>
					</div><!--panel panel-default 끝  -->

					
					</div>
					</div><!--rContent-full 끝 -->
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
</html>