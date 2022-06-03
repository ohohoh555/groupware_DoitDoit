<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문서작성 화면</title>
<%@include file="../comm/setting.jsp" %>
<script type="text/javascript" src="./dist/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/jstree.min.js"></script>
<script type="text/javascript" src="./js/appro/jsTreeScript.js"></script>	
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
#docForm{
	width: 800px;
	height: 500px;
}
table{
	border: 1px solid #ccc;
}
td{
	border: 1px solid #ccc;
}
.tableForm{
	background-color: #ccc;
	text-align: center;
	width: 100px;
}
.head{
	width: 780px;
	height: 30%;
	
}
.left{
	float: left;
}
.right{
	float: right;
}
</style>
</head>
<% Date now= new Date(); 
SimpleDateFormat sf = new SimpleDateFormat("yyyy년MM월dd일");
String today = sf.format(now);
%>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                    
                    <div id="docSelect" style="width: 800px; height: 100px;">
<fieldset style="float: left;">
		<legend>문서작성</legend>
</fieldset>	
</div>
<div style="float: left;">
		<div style="float: left;" >
		<select class="form-control" style="width: 200px;" id="docFormSelect">
			<option value="DOC0001">휴가신청서</option>
			<option value="DOC0002">교육훈련신청서</option>
			<option value="DOC0003">증명서신청서</option>
		</select>
		</div>
		<button style="margin-left: 5px; margin-right: 5px;" class="btn btn-danger" onclick="docSel()">선택</button>
</div>
<form action="./approval.do" method="post">
	<div style="margin-left: 3px;">
	<input type="button" class="btn btn-info" data-toggle="modal" data-target="#jstree"value="결재선 선택">	
	<input type="submit" class="btn btn-info" value="결재요청">
	<input type="button" class="btn btn-info" value="임시저장" onclick="return draft(this.form);">
	<input type="button" class="btn btn-info" value="취소" onclick="self.close()">
		<input type="hidden" id="doc_form_no" name="doc_form_no">
		<input type="hidden" id="selList" name="appro_line">
	</div>

	<div id="docForm">
		<div class="head">
		<div class="left">
		<table>
		<tr>
		<td class="tableForm">기안자</td>	
		<td width="150px;" height="25px;" style="text-align: center;">${eVo.emp_name }</td>	
		</tr>
		<tr>
		<td class="tableForm">기안부서</td>	
		<td width="150px;" height="25px;"style="text-align: center;">${eVo.dept_name }</td>	
		</tr>
		<tr>
		<td class="tableForm">기안일</td>	
		<td width="150px;" height="25px;" style="text-align: center;"><%=today %></td>	
		</tr>
		<tr>
		<td class="tableForm">문서번호</td>	
		<td width="150px;" height="25px;"style="text-align: center;">(문서번호+문서작성일)</td>	
		</tr>
		</table>
		</div>
		<div>
		</div>
		<div class="right">
		<table>
		<tr id="appro">
		<td class="tableForm" rowspan="3" style="writing-mode: vertical-rl; text-orientation: upright;  width:30px;">결재라인</td>
		<td class="tableForm">기안자</td>
		</tr>
		<tr id="sign">
		<td height="70px;"><img style=" width: 50px; height: 50px; margin-left: 25px;" id="image" alt="기안자사인" src=""><input type="hidden" id="imagename" value="${emp_id}" name="emp_id"></td>
		</tr>
		
		<tr id="name">
		<td style="text-align: center;">${eVo.emp_name }</td>
		</tr>
		
		</table>
		</div>
		</div>
		<div style="display: block;">
		<table >
		<tr>
		<td class="tableForm" >수신부서</td>
		<td width="800px;">&nbsp;&nbsp;</td>	
		</tr>
		<tr>
		<td class="tableForm" >참조자</td>
		<td width="800px;"><input type="text" name="appro_refer"></td>	
		</tr>
		<tr>
		<td class="tableForm" >제목</td>
		<td width="800px;"><input type="text" name="appro_title"></td>	
		</tr>
		<tr>
		<td colspan="2">
		<div style="width: 780px; height: 200px;">
		<textarea class="ckeditor" name="appro_content" id="ck_content"></textarea> 
		</div>
	
		</td>
		</tr>
		</table>
		</div>
	</div>
	</form>
                    
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
           <%--  <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize> --%>
            </div>
        </main>
    </div>
	
<!-- jstree 결재선 모달 -->
<div class="modal fade" id="jstree" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>결재선 선택</h4>
			</div>
			    <div class="modal-body">
			    <form action="post" id="frmJstree">
			    <div id="jstree">
			    	
			    	<div id="selEmp" style="width: 230px;">
   						<ul>
   						
			   			</ul>
			    	</div>
			    </div>
			    </form>
			    </div>
		</div>
	</div>
</div>
</body>
<script>

window.onload = function viewImg(){
	console.log("뷰이미지 셀렉트 함수 시작");
	var emp_id = document.getElementById("imagename").value;
	console.log("emp_id :",emp_id);
//	console.log(doit_emp_id);
	$.ajax({
		url :"./viewImg.do?",
		data : {
			"emp_id" : emp_id
		},
		type : "GET",
		async : true ,
		success : function(data){
		//	console.log(data);
		//	console.log("성공인지?");
			
			if(data.length > 0 && data != null){
				document.getElementById("image").src = data;
			}
		},
		error : function(){
			console.log("실패	ㅠㅠ");
		}
			
	});
}
//모달창 로드하기
$(".modal-body").load("./jstree.do");

function jstree(){
	console.log('jstree 시작!');
	ajaxJstree();
	$("#jstree").modal({backdrop:'static',keyboard:false});
}
var ajaxJstree = function(){
	$.ajax({
		url : "./jstree.do",
		type : "get",
		success : function(data){
			console.log(data);
			console.log('성공');
		/* 	html = "";
			html +=  " <div id='SimpleJSTree'></div>";
			html +=  "    <button id='select' onclick='sel()'>select</button>";
			html +=  "    <button id='deSelect' onclick='deSel()'>deselect</button>";
			html +=  "    <button onclick='gyuljae()'>결재선 올리기</button>";
			html +=  "    <fieldset>";
			html +=  "    	<legend>선택한 사원</legend>";
			html +=  "    	<div id='selEmp' style='width: 230px;'>";
			html +=  "   			<ul>";
			html +=  "   			</ul>";
			html +=  "    	</div>"; */
		$("#frmJstree").html(html);
		
			
		}
	});
}
//문서양식 선택 ajax
function docSel(){
	var docForm = $("#docFormSelect option:selected").val();
	console.log(docForm);
	$.ajax({
		url : "./selDocForm.do?",
		type : "get",
		data : "doc_form_no="+docForm,
		async : true ,
		success : function(data){
			console.log("성공");
		//	console.log(data);
			CKEDITOR.instances.ck_content.setData(data); 
			document.getElementById("doc_form_no").value = docForm;
			console.log(document.getElementById("doc_form_no").value);
		}
		
	});
	
}
CKEDITOR.replace( 'ck_content' ,{
									//language: 'en', //에디터의 언어 설정
									uiColor: '#9AB8F3', // 에디터 색상 변경
									extraPlugins: 'editorplaceholder', 
								    editorplaceholder: // 에디터 화면에 띄운 글귀
								    '여기에 글을 입력하거나 파일을 드래그해주세요...', 
  									filebrowserUploadUrl: "fileupload.do",
									uploadUrl:"fileupload.do",
							
}
);

CKEDITOR.on('dialogDefinition', function( ev ){
	var dialog = ev.data.definition.dialog;
	var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
  
    switch (dialogName) {
        case 'image': 
        	
			dialogDefinition.removeContents('advanced'); // 자세히탭 제거
			dialogDefinition.removeContents('Link'); // 링크탭 제거
            
            dialog.on('show', function (obj) {
        		this.selectPage('Upload'); //업로드탭으로 시작
            });
			
            break;
    }
});


function search(){
	// <textarea>의 입력값 가져오기
	// CKEDITOR.instances.content.getData(); 의 content는 <textarea>에 설정된 id값
	 var data = CKEDITOR.instances.content.getData();
	 document.getElementById("result").innerHTML = data;
}

function insert(){
	var data = document.getElementById("inputContent").value;
	console.log(data);
	// <textarea>에 넣어줄 값을 data 부분에 넣어주면 됨 
	CKEDITOR.instances.content.setData(data); 
}

function resetCon(){
	CKEDITOR.instances.content.setData("");
}

function editorAction(){
	var editorFrm = document.getElementById("editorFrm");
	editorFrm.action = "./editorFrm.do";
	
	var title = document.getElementById("title").value;
	var content = CKEDITOR.instances.content.getData();
	console.log(title, content);
	
	if(title==""){
		alert("제목을 입력해주세요");
		return false;
	}else if(content==""){
		alert("내용을 입력해주세요");
		return false;
	}else{
		editorFrm.submit();
	}

}
function draft(frm){
	console.log("임시저장 버튼 들어오는지 확인");
	frm.action = './draft.do';
	frm.submit();
	return true;
}
</script> 
</html>