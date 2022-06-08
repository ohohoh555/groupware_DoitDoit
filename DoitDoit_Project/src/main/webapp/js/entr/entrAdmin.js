//공지게시판의 카테고리별 조회
function cgoryBoard(val){
	console.log("cgoryBoard 작동", val);
	
	$("#entrTable").DataTable().destroy();

	
	$.ajax({
		url:"./selEboardCgoryAdmin.do?cgory_no="+val,
		type:"get",
		success:function(data){
			console.log("cgoryBoard 통신성공",data);
			$("#entrTable").DataTable({
				data:data,
				columns:[
					{data:"eboard_no", render:function(data){return '<input type="checkbox" name="chk" value='+data+'>'}},
		        	{data:"cgory_no"},
		        	{data:"eboard_title", 
		        	render: function (data,type,row,meta) {
			        	      return '<a href="./OneBoardAdmin.do?eboard_no='+row.eboard_no+'">'+data+'</a>';
			        	    }	
		        	},
		        	{data:"emp_name"},
		        	{data:"eboard_regdate"},
		        	{data:"eboard_delflag"},
		        	{data:"eboard_readcount"}
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
		        lengthMenu: [ 5, 10, 15],
		        displayLength: 5
				
			});
		},
		error:function(){
			console.log("cgoryBoard 통신실패");
		}
		
	});
}


//전체체크 allChk 클릭시 모든 체크박스 선택 또는 해제
function allSelect(bool){
	console.log("checkAll 작동", bool);
	var chks = document.getElementsByName("chk");
	for(let i in chks){
		chks[i].checked=bool;
	}
}


function ChangEntrDel(){
	//선택한 체크박스의 갯수 가져오기
	var chks = $("input:checkbox[name='chk']:checked");
	console.log(chks.length);
	
	//선택한 체크박스의 값 가져오기
	var chksVal = '';
    $('input[type="checkbox"]:checked').each(function (index) {
        if (index != 0) {
        	chksVal += ', ';
        }
        chksVal += $(this).val();
    });
    
    
	
	if(chks.length > 0){
		document.getElementById("entrFrm").action = "./changeEntrDel.do";
		var con = confirm("선택한 글의 숨김여부를 변경하시겠습니까? 선택한 글 번호: " +chksVal);
		if(con){
			alert("선택한 글의 숨김여부가 변경되었습니다.");
			return true;
		}
		return false;
	}else{
		alert("하나이상의 글을 선택해주세요");
		return false;
	}

}

