document.addEventListener('DOMContentLoaded', adminJaryo());

function adminJaryo(){
	console.log("자료게시판 이동");
//	$("#navGongji").removeClass("active");
//	$("#navJaryo").addClass("active");
//	$("#boardResult").html("");
	
	$.ajax({
		url:"./selJaryoAllAdmin.do",
		method:"get",
		dataType:"json",
		success:function(msg){
			console.log("자료게시판 아작스 성공",msg);
			
			var html = "";
			html +="<form action='#' method='post' id='jaryoFrm'>";
			html += "<table id='jaryoTable' class='stripe'>";
			html += "<thead>";
			html += "<tr>";	
			html += "<th><input type='checkbox' name='allChk' id='allChk' onclick='allSelect(this.checked)'></th>";		
			html += "<th>파일명</th>";		
			html += "<th>크기</th>";		
			html += "<th>등록자</th>";		
			html += "<th>숨김여부</th>";		
			html += "<th>등록일자</th>";		
			html += "</tr>";	
			html += "</thead>";
			html += "<tbody>";
			$.each(msg, function(key, value){
				html += "<tr>";	
				html += "<td><input type='checkbox' name='chk' value='"+value.eboard_no+"'></td>";		
				html += "<td>"+value.flist_originname+"</td>";		
				html += "<td>"+value.flist_size+"</td>";		
				html += "<td>"+value.emp_name+"</td>";		
				html += "<td>"+value.eboard_delflag+"</td>";		
				html += "<td>"+value.eboard_regdate+"</td>";		
				html += "</tr>";	
			});
			html += "</tbody>";
			html += "</table>";
			html +="</form>";
			
			$("#jaryoResult").html(html);
			
			$("#jaryoTable").DataTable({
				aoColumnDefs: [
		          { 'bSortable': false, 'aTargets': [0,1] }
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
		        }
				
			});
			
		},
		error:function(){
			alert("자료게시판 통신실패");
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

function ChangJaryoDel(){
	console.log("자료게시글 숨김/보임 작동");
	
	var chks = $("input:checkbox[name='chk']:checked");
	console.log(chks.length);
	
	if(chks.length >0){
		$("#jaryoFrm").attr("action", "./changeJaryoDel.do");
		$("#jaryoFrm").attr("method", "post");
		$("#jaryoFrm").submit();
	}else{
		
		alert("선택된 자료가 없습니다! (한 개 이상의 자료를 선택해주세요)");
		return false;
	}
	
}


function DeleteJaryo(){
	console.log("자료게시글 삭제");
	
	var chks = $("input:checkbox[name='chk']:checked");
	console.log(chks.length);
	
	if(chks.length >0){
		var con = confirm("선택한 자료들을 정말 삭제하시겠습니까?(완전히 삭제되어 복구가 불가능합니다.)");
		if(con){
		$("#jaryoFrm").attr("action", "./deleteJaryo.do");
		$("#jaryoFrm").attr("method", "post");
		$("#jaryoFrm").submit();
		}else{
			return false;
		}
	}else{
		alert("삭제할 자료를 선택해주세요");
		return false;
	}
	
}