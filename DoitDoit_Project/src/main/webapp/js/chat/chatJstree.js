var jsonData;//ajax로 가져온 값
var checkData;//체크 한 값
var jsTreeType;
var divId;

function treeDo(emp_id, type){
	jsTreeType = type;
	
	//어디 div에 jsTree를 뿌릴지 id 값 
	if(type == "create"){
		divId = "#createJsTree";
	}else{
		divId = "#inviteJsTree";
	}
	
	console.log("속해있는 emp_id",emp_id);
	console.log("jsTreeType",jsTreeType);
	if($(divId).html() == "" && jsTreeType == "create"){
		console.log("채팅방 생성 jstree 실행");
		treeAjax(emp_id);
	}else if($(divId).html() == "" && jsTreeType == "invite"){
		console.log("채팅방 초대 jstree 실행");
		treeAjax(emp_id);
	}
}

function treeAjax(emp_id){
	$.ajax({
		url : "./doJstree.do",
		type : "post",
		dataType : "json",
		data : "emp_id="+emp_id,
		success : function(treedata) {
			createJson(treedata);
		},
		error : function(){
			alert("통신 오류");
		}
	});
}

function createTree(treedata) {
	jsonData = treedata;
	console.log("jsonData",jsonData);
	
		$(divId).jstree({'core' : {
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
			if(jsTreeType == "create"){
				$(divId).jstree(true).search(v);
			}
		},150);
	});
}

function createJson(treedata) {
	createTree(treedata);
	
	if(jsTreeType == "create"){
		$(divId).on("changed.jstree",function(e, data) {
			checkData = data.selected;
			console.log(checkData);
		});
	}
}

function btnCreate(){
	for(var i = 0; i < checkData.length; i++){
		if(checkData[i].length < 4){
			checkData.splice(i, 1);
		}
	}
	if(jsTreeType == "create"){
		checkData.push($("#pr_emp_id").val());
		console.log(checkData);
	}
	
	if(3 > $("#roomName").val().length || $("#roomName").val().length > 20){
		alert('채팅방 제목 길이를 3~20 사이로 설정해 주세요.');
	}else if(checkData.length < 2){
		alert('채팅방 초대인원을 2명이상 선택해 주세요');
	}else{
		var urls;
		if(jsTreeType == "create"){
			urls = "./makeRoom.do";
		}else{
			urls = "./inviteRoom.do";
		}
		
		$.ajax({
			url : urls,
			type : "post",
			dataType : "text",
			data : "mems="+checkData+"&roomName="+$("#roomName").val(),
			success : function(room_id){
				console.log(room_id);
				if(jsTreeType == "create"){
					window.location.href="./chatRoom.do?room_id="+room_id;
				}
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			}
		});
	}
}