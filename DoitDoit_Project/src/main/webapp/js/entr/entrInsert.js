function resetContent(){
	var con = confirm("모든 내용을 정말 삭제하시겠습니까? (작성중인 내용가 모두 사라집니다.)");
	if(con){
		CKEDITOR.instances.eboard_content.setData("");
	}

}

function insertAction(){
	var insertFrm = document.getElementById("insertFrm");
	insertFrm.action ="./insertFrm.do";
	
	var title = document.getElementById("title").value;
	var content = CKEDITOR.instances.eboard_content.getData();
	var cald= document.getElementById("cald_content").value;

	var cgory_no = $("select[name='cgory_no']").val()
	var cald_start = $("#cald_start").val();
	var cald_end = $("#cald_end").val();
	console.log(cald_start,cald_end);
	
	if(title==""){
		alert("제목은 필수로 입력해주세요");
		return false;
	}else if(title.length < 2){
		alert("제목은 2글자 이상 입력해주세요");
		return false;
	}

	
	if(cgory_no=='302'){
		if(cald_start==""){
			alert("일정시작을 입력해주세요");
			return false;
		}else if(cald_end==""){
			alert("일정종료를 입력해주세요");
			return false;
		}else if(cald==""){
			alert("일정 내용을 입력해주세요");
			return false;
		}else{
			insertFrm.submit();
		}

	}else{
		
			if(content =="" ){
				alert("내용을 필수로 입력해주세요");
				return false;
			}else{
				insertFrm.submit();
			}
	}

}

function selectCgory(val){
	console.log("selectCgory 작동", val);
	
	if(val == 101){
		$("#cgoryEtc").empty();
		$("#trDate").hide();
		$("#trCald").hide();
		$("#trEboard").show();

	}else if(val == 102){
		$("#cgoryEtc").empty();
		$("#trDate").hide();
		$("#trCald").hide();
		$("#trEboard").show();
		$("#cgoryEtc").text("필독게시물은 게시판 상단에 별도 표기됩니다.");
	}else if(val == 103){
		$("#cgoryEtc").empty();
		$("#trDate").hide();
		$("#trCald").hide();
		$("#trEboard").show();
	}else{
		$("#cgoryEtc").empty();
		$("#trDate").show();
		$("#trCald").show();
		$("#trEboard").hide();

	}
}

