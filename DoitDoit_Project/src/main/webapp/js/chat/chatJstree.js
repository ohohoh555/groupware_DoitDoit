var jsTreeData;
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
	console.log("makeJsTree");
	$("#makeJsTree").jstree({
		'core' : {
			//treedata의 값을 tree 구조로 만들어 줌
			'data' : treedata
		},
		"plugins" : [ "wholerow", "changed", "checkbox" ]
	});
}

function createJson(treedata) {
		
	createTree(treedata);

	$("#makeJsTree").on("changed.jstree",function(e, data) {
		jsTreeData = (data.selected);
	});
}

function btnInvite(){
	
}
