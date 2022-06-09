var empData;
var empNo;
var rank = 0;
var deselEmp;

$(function() {
	$.ajax({
		url : "./jstreeToJson.do",
		type : "post",
		dataType : "json",
		success : function(jsondata) {
			createData(jsondata);
			console.log(jsondata);
		},
		error : function() {
			alert("통신 오류 입니다");
		}
	});
	
	$(document).on("click",".empList", function(){
		deselEmp = $(this);
		$(this).css("background","red");
		$(this).siblings().css("background","white");
	});
});

//jsTree 생성
function createJSTree(jsondata) {
	empData = jsondata;
	console.log("createJSTree");
	$('#SimpleJSTree').jstree({
		'core' : {
			//jsondata의 값을 tree 구조로 만들어 줌
			'data' : jsondata
		},
		"plugins" : [ "wholerow", "changed" ]
	});
}

function createData(jsondata) {
	var isc;
	
	empData = jsondata;
	
	createJSTree(jsondata);

	//select의 이벤트가 변경 될 떄마다 실행
	$("#SimpleJSTree").on("changed.jstree",function(e, data) {
		var row = data.node;
		//console.log("changed",row);
		
		if(data.node.id.length >= 3){
			isc = boolDisabled($(row).attr("id"));
			
			//선택 가능한 사원이 아니면 사원 등록 해제
			if(isc){
				setDisabled(true,row);
				empNo = null;
			}else{
				empNo = data.selected;//선택된 사원번호(empNo) empNo에 대입d	
			}	
		}
	});
	
	//닫힌 노드(ul) 열기 전에 nodeTree 탐색하여 실행
	$("#SimpleJSTree").on("before_open.jstree",function(e, data) {
		//열은 node에 id 값 들고와서 children 탐색
		
		$.each($("li[id = '"+data.node.id+"'] > ul > li"), function(idx, row) {
			isc = boolDisabled($(row).attr("id"));
			setDisabled(isc,row);
			//console.log("before_open",row);
		});
	});
	
	//hover 했을때 disabled면 hover 안되게
	$("#SimpleJSTree").on("hover_node.jstree",function(e, data) {
		var row = $("li[id = '"+data.node.id+"']")[0];
		if(data.node.id.length >= 1){
			//console.log("hover_node", row);
			isc = boolDisabled($(row).attr("id"));
			//hover 안되게
			if(isc){
				setDisabled(true,row);
			}	
		}
	});
}

//disabled 판단
function boolDisabled(liId){
	//liRank 값이랑 rank 값보다 작으면
	// disabled 처리
	var liRank = findLiRank(liId);//liId에 해당하는 rank값 들고옴
	
	//liRank(dom의 rank 값)가 rank(선택한 id의 rank)
	var isc;
	if(liRank <= rank){
		isc = true;
	}else{
		isc = false;
	}
	//console.log(liRank,"<=",rank," - isc =",isc);
	return isc;
}

//liId = Dom 탐색하면서 li의 id 값을 찾는다.
//jsonData를 돌면서 liId 값과 같은 id의 rank를 찾아온다
function findLiRank(liId){
	var liRank;
	//empData : Controller에서 들고온 jsonArray의 length만큼 돔
	$.each(empData, function(idx, row) {
		//탐색한 jsonArray와 노드에 Id 값 일하는지
		if(row.id == liId){
			liRank = row.rank;
		}	
	});
	
	return liRank;
}

//disabled로 바꿔줌
function setDisabled(isc,row){
	if(isc){
		$(row).attr("aria-disabled","true");
		$(row).children("div").prop("class","jstree-wholerow");
		$(row).children("a").attr("class","jstree-anchor jstree-disabled");
		$(row).children("a").css("pointer-events","none");
	}else{
		$(row).attr("aria-disabled","false");
		$(row).children("a").attr("class","jstree-anchor");
		$(row).children("a").css("pointer-events","auto");	
	}
}

function sel() {

console.log("sel이 동작되는지");
console.log(empNo);
	if(empNo[0].length >= 3){	
		//해당 id값의 rank를 찾음
		rank = findLiRank(empNo);
		
		//선택 가능한 사원인지 boolean 값 판단
		var isc;
		$.each($("ul>li>ul>li"), function(idx, row) {
			isc = boolDisabled($(row).attr("id"));
			setDisabled(isc,row);
		});
		
		var empLists = $(".empList");
		$.each(empData, function(idx,row){
			if(row.id == empNo){
				console.log('if문 안의 길이 :',empLists.length);
				if(empLists.length >= 3){
				alert("결재자는 최대 3명까지 선택할 수 있습니다.");
				}else{
				$("#selEmp").children("ul").append("<li class='empList'>"+row.text+"</li>");
				}
			}
		});
		
		console.log(empLists.length);
		console.log(empNo);
		empNo = "0";
	}else if(empNo[0].length < 3){
		alert('사원을 선택해 주세요.');
	}else{
		alert('선택 가능한 사원을 선택한 후에 추가를 눌러주세요');
	}
}

function deSel(){
	$(deselEmp).remove();
	
	rank = 0;
	
	//선택된 emp들의 최고 rank 찾는 부분
	$.each($("#selEmp>ul>li"), function(idx,row){
		$.each(empData, function(idxs,rows){
			if($(row).text() == rows.text){
				var liRank = findLiRank(rows.id);
				if(rank < liRank){
					rank = liRank;
				}
				
				return false;
			}
		});
	});
	
	console.log("최고 rank는 ", rank);
	
	var isc;
	$.each($("ul>li>ul>li"), function(idx, row) {
		isc = boolDisabled($(row).attr("id"));
		setDisabled(isc,row);
	});
}

function gyuljaeClick(){
	var empLists = $(".empList");
		console.log(empLists.length); 
		for(var i=0; i<empLists.length; i++ ){
			$("#appro").append('<td class="tableForm">결재자</td>');
			$("#sign").append('<td height="70px;">&nbsp;&nbsp;</td>');
			$("#name").append('<td style="text-align: center;">'+empLists[i].innerHTML+'</td>');
		}
	//	$('#jstree').modal('hide');
		gyuljae();
		
		console.log(empLists);
		console.log(empLists[0]);
		console.log(empLists[0].innerHTML);
		console.log(empLists[1]);
		console.log(empLists[1].innerHTML);
		
}

function gyuljae(){
	//화면에서 선택된 
	var emps = new Array();
//	const emps = new Map();
	$.each($(".empList"), function(idx,row){
		$.each(empData, function(idxs,rows){
			 console.log("------");
			 console.log(empData);
			if($(row).text() == rows.text){
			//	var map = new Map();
				emps.push(rows.id);
				emps.push(rows.text);
				emps.push(rows.rank);
				
//				emps.set("id",rows.id);
//				emps.set("text",rows.text);
//				emps.set("rank",rows.rank);
//추가할 데이터는 push로 여기에 넣고 List가 만들어지면 아래 gyuljae.do로 		
				return false;
			}
		});
	});
	
	console.log("------");
	console.log(emps);

	
	$.ajax({
		url : "./gyuljae.do",
		type : "post",
		data : "emps="+emps,
		dataType : "text",
		success : function(isc) {
			console.log(isc);
			if(isc == null){
				alert("전송 실패");
			}else{
				alert("전송 성공");
				console.log(emps);
				console.log(isc);
				document.getElementById("selList").value = isc;
				console.log(document.getElementById("selList").value);
	//			$("#selList").val = emps;
				//$.each($(".empList"),function(idx, row){
					
				//});
			}
		},
		error : function() {
			alert("통신 오류 입니다");
		}
	});
}