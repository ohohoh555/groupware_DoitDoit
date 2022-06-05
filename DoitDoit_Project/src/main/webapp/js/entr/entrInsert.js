document.addEventListener('DOMContentLoaded', readyFn());
function readyFn(){
	console.log("dom 완성");
}


function resetContent(){
	CKEDITOR.instances.content.setData("");
}

function insertAction(){
	var insertFrm = document.getElementById("insertFrm");
	insertFrm.action ="./insertFrm.do";
	
	
	var title = document.getElementById("title").value;
	var content = CKEDITOR.instances.content.getData();
	console.log(title, content, title.length);
	
	if(title==""){
		alert("제목은 필수로 입력해주세요");
		title.focus();
		return false;
	}else if(title.length < 2){
		alert("제목은 2글자 이상 입력해주세요");
		return false;
	}else if(content ==""){
		alert("내용을 필수로 입력해주세요");
		return false;
	}else{
		insertFrm.submit();
	}
}


function selectCgory(val){
	console.log("selectCgory 작동", val);
	
// 	var html = "";
// 	html +="<tr id='trDate'><td>일시</td>";
// 	html +="<td>시작:<input type='date' class='form-control' style='width: 150px;'>";
// 	html +="<input type='time' class='form-control' style='width: 150px;'> ~ ";
// 	html +="종료:<input type='date' class='form-control' style='width: 150px;'>";
// 	html +="<input type='time' class='form-control' style='width: 150px;'>";
// 	html +="</td></tr>";

	var cald_start = document.getElementById("cald_start");
	var cald_end = document.getElementById("cald_end");

	cald_start.disabled = true;
	cald_end.disabled = true;

	
	if(val == 101){
		$("#cgoryEtc").empty();
//		$("#trDate").remove();
		cald_start.disabled = true;
		cald_end.disabled = true;

	}else if(val == 102){
		$("#cgoryEtc").empty();
//		$("#trDate").remove();
		cald_start.disabled = true;
		cald_end.disabled = true;

		$("#cgoryEtc").text("필독게시물은 게시판 상단에 별도 표기됩니다.");
	}else if(val == 103){
		$("#cgoryEtc").empty();
//		$("#trDate").remove();
		cald_start.disabled = true;
		cald_end.disabled = true;

	}else{
		$("#cgoryEtc").empty();
		$("#trDate").remove();
// 		$("#insertTbl >tbody > tr").eq(0).after(html);
		cald_start.disabled = false;
		cald_end.disabled = false;

	}
}

