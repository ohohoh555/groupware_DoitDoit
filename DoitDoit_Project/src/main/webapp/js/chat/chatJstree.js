var jsonData;//ajax로 가져온 값
var checkData;//체크 한 값
var jsTreeType;
var divId;
var getEmp_id = new Array();

function treeDo(type){
	jsTreeType = type;
	getEmp_id = [];
	
	if(type == "create"){
		divId = "#createJsTree";
		getEmp_id.push($("#pr_emp_id").val());
		console.log("push data",getEmp_id);
		console.log("초대하는 사람 아이디", getEmp_id);
	}else{
		divId = "#inviteJsTree";
		var emp = $(".members").children();
		for(var i = 0; i < emp.length; i++){
			getEmp_id.push($(".members").children().eq(i).attr("id"));
		}
		console.log(getEmp_id);
	}
	
	console.log("jsTreeType",jsTreeType);
	if($("#createJsTree").html() == "" && jsTreeType == "create"){
		console.log("채팅방 생성 jstree 실행");
		treeAjax(getEmp_id);
	}else if($("#inviteJsTree").html() == "" && jsTreeType == "invite"){
		console.log("채팅방 초대 jstree 실행");
		treeAjax(getEmp_id);
	}
}

function treeAjax(getEmp_id){
	$.ajax({
		url : "./doJstree.do",
		type : "post",
		dataType : "json",
		data : "emp_id="+getEmp_id,
		success : function(treedata) {
			console.log(treedata);
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
	
//	if(jsTreeType == "create"){
		$(divId).on("changed.jstree",function(e, data) {
			checkData = data.selected;
			console.log(checkData);
		});
//	}
}

function btnCreate(){
	for(var i = 0; i < checkData.length; i++){
		if(checkData[i].length < 4){
			checkData.splice(i, 1);
		}
	}

	if(jsTreeType == "create" && (3 > $("#roomName").val().length || $("#roomName").val().length > 20)){
		alert('채팅방 제목 길이를 3~20 사이로 설정해 주세요.');
	}else if(checkData.length < 1){
		alert('채팅방 초대인원을 1명이상 선택해 주세요');
	}else{
		var urls;
		if(jsTreeType == "create"){
			urls = "./makeRoom.do";
		}else{
			urls = "./inviteRoom.do";
		}
		
		var data;
		if(jsTreeType == "create"){
			getEmp_id.push(checkData);
			data = "mems="+getEmp_id+"&roomName="+$("#roomName").val();
			console.log(data);
		}else{
			data = "mems="+checkData+"&room_id="+$("#room_id").val();
			console.log(data);
		}
		
		$.ajax({
			url : urls,
			type : "post",
			dataType : "json",
			data : data,
			success : function(result){
				if(jsTreeType == "create"){
					console.log(result);
					console.log(result.room_id);
					window.location.href="./chatRoom.do?room_id="+result.room_id;
					$("#createJsTree").html("");
				}else{
					console.log("html",result.html);
					console.log("memList",result.memList);
					console.log("room_id",result.room_id);
					$("#inviteJsTree").html("");
					stomp.send('/sub/invite/room/' + result.room_id, {}, JSON.stringify({html: result.html, memList: result.memList}));
				}
			},
			error : function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);	
			}
		});
	}
}

function btnHide(type){
	if(type == "create"){
		$("#cJstree").modal("hide");
	}else{
		$("#iJstree").modal("hide");		
	}
}

function btnCancle(type){
	if(type == "create"){
		$("#cJstree").modal("hide");
		$("#createJsTree > ul > li").prop("aria-selected","false");
		$("#createJsTree > ul > li > ul > li").prop("aria-selected","false");
		$("#createJsTree > ul > li > a").prop("class","jstree-anchor");
		$("#createJsTree > ul > li > ul > li > a").prop("class","jstree-anchor");
		$("#createJsTree > ul > li > div").prop("class","jstree-wholerow");
		$("#createJsTree > ul > li > ul > li > div").prop("class","jstree-wholerow");
	}else{
		$("#iJstree").modal("hide");		
		$("#inviteJsTree > ul > li").prop("aria-selected","false");
		$("#inviteJsTree > ul > li > ul > li").prop("aria-selected","false");
		$("#inviteJsTree > ul > li > a").prop("class","jstree-anchor");
		$("#inviteJsTree > ul > li > ul > li > a").prop("class","jstree-anchor");
		$("#inviteJsTree > ul > li > div").prop("class","jstree-wholerow");
		$("#inviteJsTree > ul > li > ul > li > div").prop("class","jstree-wholerow");
	}
}