<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>Simple jsTree</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css" />
   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/jstree.min.js"></script>

<script type="text/javascript" src="./js/appro/jsTreeScript.js"></script>
</head>
<body>
    <div id="SimpleJSTree"></div>
    <button id="select" class="btn btn-default btn-xs"  onclick="sel()">select</button>
    <button id="deSelect" class="btn btn-default btn-xs"  onclick="deSel()">deselect</button>
    <button id="lineClear" class="btn btn-default btn-xs"  onclick="lineClear()">clear</button>
    <button  class="btn btn-info btn-xs"  onclick="gyuljaeClick()">결재선 올리기</button>
    <fieldset>
    	<legend>선택한 사원</legend>
    	<div id="selEmp" style="width: 230px;">
   			<ul>
   				
   			</ul>
    	</div>
    	<div>
    	<input type="hidden" id="selList" value="">
    	</div>
    </fieldset>
</body>
</html>	