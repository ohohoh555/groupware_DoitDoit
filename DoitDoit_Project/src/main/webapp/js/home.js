var cnt = 0;
	    
$(document).ready(function(){
	$("#mainMenu>li").mouseover(function(){
		$(this).find(".subMenu").css({display:"block"});
	}).mouseout(function(){
        $(".subMenu").css({display:"none"});
    });

	$.ajax({
		type : "GET",
		url:"./roomList.do",
		success:function(rooms){
			$("#chatRoom").html("");
			for(var i = 0; i < rooms.length; i++){
				var html; 
				html = "<div onclick=\"toChatRoom('"+rooms[i].room_id+"')\">";
				html += 	"<a href=./chatRoom.do?room_id="+rooms[i].room_id+">";
				html += 		"<div class=\"roomName\">";
				html += 			"<div>";
				html += 				"<span style=\"font-size: 10px; color: white;\">"+rooms[i].room_name+"</span>";
				html += 			"</div>";
				html += 			"<div>"
				if(rooms[i].chat_type == "T"){
					html +=				"<span style=\"color: #FCFCFC;\">"+rooms[i].chat_con+"</span>";
				}else{
					html +=				"<span style=\"color: #FCFCFC;\">파일이 전송 되었습니다.</span>";
				}
				html +=					"<span style=\"color: #EAEAEA;\">"+rooms[i].chat_time+"</span>";
				html +=				"</div>";
				html += 		"</div>";	
				html += 	"</a>";
				html += "</div>";	
				html += "<hr>";		
				$("#chatRoom").append(html);
			}
		},
		error:function(){
			alert("에러 발생");
		}
	});		

	$(".create-modal-body").load("./createJstree.do");
	
	eboardResent();
});

function menuOn(){
	cnt ++;
	if(cnt % 2 == 1){
		$("nav").css({left:"0",transition:"all 0.5s"})
	}else{
		$("nav").css({left:"-150px",transition:"all 0.5s"})
	}
}

function chatOn(){
	cnt ++;
	if(cnt % 2 == 1){
		$("#chat").css({top:"375px",transition:"all 0.5s"});	
	}else{
		$("#chat").css({top:"25px",transition:"all 0.5s"})
	}
}




function eboardResent(){
	console.log(" eboardResent 최근글 작동");
	
	var html ="";
	var now = new Date(); //현재 날짜 및 시간 받아보기
	console.log(now);
	var year = now.getFullYear();
	var month = ('0' + (now.getMonth() + 1)).slice(-2);
	var day = ('0' + now.getDate()).slice(-2);
	
	var dateString = year + '-' + month  + '-' + day; // string 포맷처리
	console.log(dateString);

	
	
	$.ajax({
		url:"./resentBoard.do",
		type:"get",
		dataType:"json",
		success:function(msg){
			console.log(msg);
			html +='<h3>최근글</h3>';
			$.each(msg, function(key, value){
				html +='<div class="panel panel-default" style="margin-bottom:5px;">';
				html +='<div class="panel-heading">'+value.cgory_no+'&nbsp;'
				html += '<a href="./OneBoard.do?eboard_no='+value.eboard_no+'">'+value.eboard_title+'</a>&nbsp;&nbsp';
				if(dateString==value.eboard_regdate.toString()){ //오늘 올린 글은 new 표시
					html +='<span class="badge">New</span>';
					}
				html +='</div>';
				html +='<div class="panel-body" style="opacity:0.5;">작성일:'+value.eboard_regdate+'&nbsp;&nbsp;작성자:'+value.emp_name+'&nbsp;&nbsp;';
				html +='내용:'+value.eboard_content.replace(/(<([^>]+)>)/ig,"");
				html +='</div>'
				html +='</div>';
			});
			$("#resentBoard").html(html);
			
		},
		error:function(){
			alert("최근글 통신오류");
		}
	});
	
	
}