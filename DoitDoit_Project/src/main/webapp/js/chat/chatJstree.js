var jsonData;//ajax로 가져온 값
var checkData;//체크 한 값
var selData;//선택한 값
$(function(){
	
	console.log($("#pr_emp_id").val());
	
	$.ajax({
		url : "./doJstree.do",
		type : "post",
		dataType : "json",
		data : "emp_id="+$("#pr_emp_id").val(),
		success : function(treedata) {
			createJson(treedata);
		},
		error : function(){
			alert("통신 오류");
		}
	});
});

function createTree(treedata) {
	jsonData = treedata;
	console.log("jsonData",jsonData);
	$("#makeJsTree").jstree({
		'core' : {
			//treedata의 값을 tree 구조로 만들어 줌
			'data' : treedata
		},
		"plugins" : [ "wholerow", "changed", "checkbox", "search" ]
	});
	
    var to = false;
	$("#searchPeople").keyup(function (){
		if(to){
			clearTimeout(to);
		}
		
		to = setTimeout(function () {
      		var v = $('#searchPeople').val();
			$("#makeJsTree").jstree(true).search(v);
		},150);
	});
}

function createJson(treedata) {
	createTree(treedata);
	
	$("#makeJsTree").on("changed.jstree",function(e, data) {
		checkData = data.selected;
	});
}

function peopleAdd(){
	if(checkData.length < 1){
		alert('인원을 선택해 주세요.');
	}else{
		for(var i = 0; i < checkData.length; i++){
			$.each($(jsonData), function(idx, row){
				if($(row).attr("id") == checkData[i]){
					console.log($(row).attr("text"));
				}
			});
		}
	}
}

function btnInvite(){
	console.log($("#roomName").val().length);
	if(3 > $("#roomName").val().length || $("#roomName").val().length > 20){
		alert('채팅방 제목 길이를 3~20 사이로 설정해 주세요.');
	}else if(checkData.length < 2){
		alert('채팅방 초대인원을 2명이상 선택해 주세요');
	}else if($.array){
		
	}else{
		$.ajax({
			url : "./makeRoom.do",
			type : "post",
			dataType : "text",
			data : "mems="+selData+"&roomName="+$("#roomName").val(),
			success : function(room_id){
				window.location.href="./chatRoom.do?room_id="+room_id;
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			}
		});
	}
}
