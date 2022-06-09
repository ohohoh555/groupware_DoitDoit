<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
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
            		<label class="form-label">부서</label>
		            <select name="dept_no" onchange="javascript:location.href='./annualAdmin.do?dept_no='+$('select option:selected').val();">
		            	<option ${dept_no == null?'disabled selected':'disabled'}>==선택==</option>
		            	<option value="01" ${dept_no eq '01'?'selected="selected"':''}>인사부</option>
		            	<option value="02" ${dept_no eq '02'?'selected':''}>관리부</option>
		            	<option value="03" ${dept_no eq '03'?'selected':''}>개발부</option>
		            	<option value="04" ${dept_no eq '04'?'selected':''}>영업부</option>
		            </select>
<!-- 		            </form> -->
            	<hr style="border: solid 1px #6667AB;">
            	<div style="height: 60%;">
	            	<table id="annualTable" class="table table-bordered" style="text-align: center;">
	            		<thead>
	            			<tr>
	            				<th  style="text-align: center;" data-orderable="false"><input type="checkbox" id="chkAll" onclick="checkAll(this.checked)"></th>
	            				<th  style="text-align: center;">NO</th>
	            				<th  style="text-align: center;" data-orderable="false">부서</th>
	            				<th  style="text-align: center;">사원번호</th>
	            				<th  style="text-align: center;">이름</th>
	            				<th  style="text-align: center;" data-orderable="false">직급</th>
	            				<th  style="text-align: center;" data-orderable="false">발생연차</th>
	            				<th  style="text-align: center;" data-orderable="false">사용연차</th>
	            				<th  style="text-align: center;" data-orderable="false">잔여연차</th>
	            			</tr>
	            		</thead>
	            		<tbody >
	            			<c:forEach var="annualVo" items="${lists}" varStatus="vs">
	            				<tr>
	            					<td><input type="checkbox" name="chk" value="${annualVo.emp_id}" onclick="checkEmp()"></td>
	            					<td>${vs.count}</td>
	            					<td>${annualVo.dept_name}</td>
	            					<td>${annualVo.emp_id}</td>
	            					<td><a href="./annualAdminDetail.do?emp_id=${annualVo.emp_id}">${annualVo.emp_name}</a></td>
	            					<td>${annualVo.rank_name}</td>
	            					<td>${annualVo.ann_add}</td>
	            					<td>${annualVo.ann_use}</td>
	            					<td>${annualVo.ann_rest}</td>
	            				</tr>
	            			</c:forEach>
	            		</tbody>
	            	</table>
            	</div>
            	<hr style="border: solid 1px #6667AB;">
	            <button class="btn btn-default" style="float: right;" onclick="addModal()">연차 부여</button>
            	</div>  
            </div>
        </main>
    </div>
    
    
	<!-- 연차 모달 -->
	<div class="modal fade" id="annual" role="dialog">
	  <div class="modal-dialog modal-lg" style="width: 700px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title" style="text-align: center;">연차 부여</h4>
	      </div>
	      <div class="modal-body">
	        <form action="./annualAdd.do" method="post" id="frmAnn">
	        	<table class="table table-bordered" style="height: 300px;">
	        		<tr>
		       			<th style="text-align: center; color: white; background-color: #6667AB; vertical-align: middle;">사원 번호</th>
		       			<td style="text-align: center; vertical-align: middle;">
			      			<input type="text" id="empId" name="emp_id" readonly style="width: 550px; background-color: white; border: 1px solid black; font-size: 13pt;">
		       			</td>
	        		</tr>
	        		<tr>
		       			<th style="text-align: center; color: white; background-color: #6667AB; vertical-align: middle;">연차 수</th>
		       			<td style="text-align: center; vertical-align: middle;">
			      			<input type="text" id="annDays" name="ann_add_days" style="width: 550px; background-color: white; border: 1px solid black; font-size: 13pt;">
		       			</td>
	        		</tr>
	        		<tr>
	        			<th style="text-align: center; color: white; background-color: #6667AB; vertical-align: middle;">연차 내용</th>
	        			<td style="text-align: center; vertical-align: middle;">
			      			<textarea id="annContent" name="ann_add_content" rows="5" cols="59" style="font-size: 13pt;"></textarea>
			      			<input type="hidden" name="dept_no" value="${dept_no}">
	        			</td>
	        		</tr>
	        	</table>
	       	</form>
	      </div>
		  <div class="modal-footer">
		    <button type="button" class="btn btn-default" onclick="annAdd()">연차 부여</button>
	      </div>
	    </div>
	  </div>
	</div><!-- 모달 영역 끝 -->
</body>
</html>