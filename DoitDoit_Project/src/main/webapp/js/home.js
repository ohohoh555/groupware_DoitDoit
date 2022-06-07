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

	$(".mod-body").load("./chatJstree.do");
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