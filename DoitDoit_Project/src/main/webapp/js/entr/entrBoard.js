$(document).ready(function(){
	$('#EntrTable').DataTable({
   		"language": {
            "emptyTable": "데이터가 없어요.",  //게시글이 없을 경우
            "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
            "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
            "infoEmpty": "데이터 없음",   // info부분이 비었을 경우 
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전" 
            }
        },
		scrollCollapse:true,
		scrollY:"200px",
        lengthMenu: [ 5, 10, 15],
        displayLength: 5,
        order:[ [ 4, "desc"]]

	});
	
	
	$("#FildokTable").DataTable({
		scrollCollapse:true,
		scrollY:"200px",
		lengthChange: false,
		searching: false,
		ordering: false,
		info: false,
		paging:false,
		order:[ [ 4, "desc"]]
	});
		
});

function cgoryAction(val){
	console.log("cgoryAction 작동", val);
	
	$("#EntrTable").DataTable().destroy();
	$("#EntrDiv").hide();
	
		$.ajax({
			url:"./cgoryBoard.do?cgory_no="+val,
			type:"get",
			success:function(data){
			//	console.log(data);
			$("#EntrDiv").show();
				$('#EntrTable').DataTable({
					data:data,
				    columns: [
				        { data: 'eboard_no', render:function(data,type,row,meta){return meta.row+1 } },
				        { data: 'cgory_no' },
				        { data: 'eboard_title',
				        	render: function (data,type,row,meta) {
				        	      return '<a href="./OneBoard.do?eboard_no='+row.eboard_no+'">'+data+'</a>';
				        	    }
				        },
				        { data: 'emp_name' },
				        { data: 'eboard_regdate' },
				        { data: 'eboard_readcount'}
				    	],
				   	"language": {
				            "emptyTable": "게시글이 없어요.",  //게시글이 없을 경우
				            "lengthMenu": "페이지당 _MENU_ 개씩 보기",  // 페이징 개수
				            "info": "현재 _START_ - _END_ / _TOTAL_건",  // 시작 - 끝 / 전체페이지 
				            "infoEmpty": "게시글 없음",   // info부분이 비었을 경우 
				            "infoFiltered": "( _MAX_건의 게시글에서 필터링됨 )",
				            "search": "검색: ",
				            "zeroRecords": "일치하는 게시글이 없어요.",  //검색시 필터링된 게시글이 없을 경우
				            "loadingRecords": "로딩중...",
				            "processing":     "잠시만 기다려 주세요...",
				            "paginate": {
				                "next": "다음",
				                "previous": "이전"
				            }
				        },
					scrollCollapse:true,
					scrollY:"200px",
					scrollX:true,
					columnDefs: [
						            { width: 22, targets: 0 }
						        ],
				     lengthMenu: [ 5, 10, 15],
				     displayLength: 5,
				     order:[ [ 4, "desc"]]
				});
				
			},
			error:function(){
				alert("EboardCgory 통신실패");
			}
	});
}


function insertBoard(){
	console.log("insertBoard 글쓰기 작동");
	location.href ="./insertBoard.do"
}

function fildokAll(){
	console.log("필독 전체보기 작동");
	$("#FildokTable").DataTable().destroy();
	
	$.ajax({
		url:"./FildocAll.do",
		type:"get",
		success:function(data){
			console.log(data);
			$("#FildokTable").DataTable({
				data:data,
				columns: [
					{data:"eboard_no", render: function (data,type,row,meta) {return "★"}},
					{data:"cgory_no", render: function (data,type,row,meta) {return "[필독]"}},
					{data:"eboard_title", render:function(data,type,row,meta){return '<a href="./OneBoard.do?eboard_no='+row.eboard_no+'">'+data+'</a>'}},
					{data:"emp_name"},
					{data:"eboard_regdate"},
					{data:"eboard_readcount"}
				],
				scrollCollapse:true,
				scrollY:"200px",
				lengthChange: false,
				searching: false,
				ordering: false,
				info: false,
				paging:false,
				order:[ [ 4, "desc"]]
			});
		},
		error:function(){
			alert("필독전체보기 통신에러");
		}
		
		
	});
	
	$("#FildokTable tfoot>tr").remove();
	$("#FildokTable>tfoot").append('<tr><td colspan="6" style="text-align: center;" onclick="fildokClose()">필독게시판 닫기</td></tr>');
//	$("#EntrDiv").remove();

}

function fildokClose(){
	console.log("필독게시판 닫기");
	
	$("#FildokTable").DataTable().destroy();

	
	$.ajax({
		url:"./FildocThree.do",
		data:"get",
		success:function(data){
//			console.log(data);
			$("#FildokTable").DataTable({
				
				data:data,
				columns: [
					{data:"eboard_no", render: function (data,type,row,meta) {return "★"}},
					{data:"cgory_no", render: function (data,type,row,meta) {return "[필독]"}},
					{data:"eboard_title", render:function(data,type,row,meta){return '<a href="./OneBoardAdmin.do?eboard_no='+row.eboard_no+'">'+data+'</a>'}},
					{data:"emp_name"},
					{data:"eboard_regdate"},
					{data:"eboard_readcount"}
				],
				lengthChange: false,
				searching: false,
				ordering: false,
				info: false,
				paging:false,
				order:[ [ 4, "desc"]]
			});
	$("#FildokTable tfoot>tr").remove();
	$("#FildokTable>tfoot").append(
		'<tr><td colspan="6" style="text-align: center;"><ul><li onclick="fildokAll()">&lt;&lt;[필독]게시글 전체보기&gt;&gt;</li></ul></td></tr>'					
	);
	
		},
		error:function(){
			alert("필독게시판 닫기 통신오류");
		}

	});
}
