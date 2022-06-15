var stomp;
var room_id;
var emp_id;
var user_name;

//우연
var susin;
var barsin;
var empArr;

// 파일 중복 업로드 방지용 맵을 선언한다.
//var map = new Map(); // Map.prototype(); 객체는 언제나 함수형태로 생성
//파일 업로드
var chatFd = new FormData();
// form 에 데이터 추가 시 동적으로 name 을 주기 위해 사용
var k = 0;

$(document).ready(function() {
	
	$(".invite-modal-body").load("./inviteJstree.do");
	
	//우연
	var empN = $("#empN").val();

	console.log("js실행");
	var sock = new SockJS("/DoitDoit_Project/stompSocket");
	console.log(sock);
	
	//방 id
	room_id = $("#room_id").val();
	// emp_id
	emp_id = $("#pr_emp_id").val();
	// emp 성함
	user_name = $("#pr_user_name").val();

	stomp = Stomp.over(sock);
	stomp.connect({}, function() {
		console.log("STOMP Connection");
		
		//4. subscribe(path, callback)으로 메세지를 받을 수 있음
		// 메세지 받기
		stomp.subscribe("/sub/chat/room/" + room_id, function(chat) {
			console.log("메시지 받음",chat);
			var content = JSON.parse(chat.body);
			var clas;
			
			//누구 메세지 인지 판단
			if(content.emp_id == 0){
				clas = "issue";
			}else if(content.emp_id == emp_id){
				clas = "myMsg";
				$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
			}else{
				clas = "anotherMsg";
			}
			console.log("받은 메시지 클래스",clas);
			//append 새로운 메시지
			var html = '';
			html += "<div class=\""+clas+"\">";
			html += 	content.chat_con;
			html += "</div>"; 
			$("#chatLog:last-child").append(html);
			
			html = "";
			if(content.chat_type == "F"){
				html += "<div class=\"files\">"
				html += content.chat_con;
				html += "</div>";
				$("#fileArea:last-child").append(html);
				if($('#fileArea').scrollTop()){
					$('#fileArea').scrollTop($('#chatLog')[0].scrollHeight);
				}
			}
			
			//scrollbar가 내려 갔을시 추가 되는 스크롤 자동으로 내려줌
			if($('#chatLog').scrollTop()){
				$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
			};
			
			chatList(content.room_id,content.chat_con,content.chat_type);
			var child = ($("#chatRoom").find("#"+content.room_id));
			$(child).find(".read").css("color","#FCFCFC");
		});
		
		//들어 왔을떄
		stomp.subscribe("/sub/chatMem/room/" + room_id, function(member){
			console.log("chatMem Enter");
			mems = member.body;
			mems = mems.replaceAll('"', '');
			mems = mems.replace('[', '');
			mems = mems.replace(']', '');
			mems = mems.split(",");
			console.log(mems);
			
			aboutChatRoom(mems);
			
			$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
		});
		
		roomEnter(room_id,emp_id);
		
		stomp.subscribe("/sub/chat/getOut/" + room_id, function(out){			
			var mem = JSON.parse(out.body);

			var emps = $("#members").children();
			for(var i = 0; i < emps.length; i++){
				if(emps.eq(i).attr("id") == mem.emp_id){
					$("#members").children().eq(i).remove();
				}
			}
		});
		
		stomp.subscribe("/sub/invite/room/" + room_id, function(invite){
			var mem = JSON.parse(invite.body);
			$("#members").html(mem.html);
			aboutChatRoom(mem.memList);
		});
		
		//우연
		stomp.subscribe('/sub/alarm/'+empN, function(text){
        	var hel = JSON.parse(text.body);
         
         	if(hel.type == "appr"){
	            $("#textApp").text(hel.barsin+" 님이 결재를 요청하였습니다.")
	            $("#textApp").slideDown();
	            $("#textApp").delay(3000).slideUp();
         	}else if(hel.type == "chat"){
				console.log("알람 울림");
	
				chatList(hel.room_id,hel.chat_con,hel.chat_type,hel.roomName);
            	var child = ($("#chatRoom").find("#"+hel.room_id));
            	$(child).find(".read").css("color","red");
            	
            	$("#textApp").html("<div>"+hel.roomName+"</div>" + hel.chat_con);
            	$("#textApp").slideDown();
            	$("#textApp").delay(6000).slideUp();
			}
      	});
      
		//우연
		stomp.subscribe('/sub/login',{});
			
		stomp.send('/pub/login', {}, JSON.stringify({ emp_id: empN }));
		
		stomp.subscribe('/sub/logout',function(){
			console.log("로그아웃")
		});
		
		//우연    
      	stomp.subscribe('/sub/approval/complet/'+empN,function(text){
         	var hel = JSON.parse(text.body);
				console.log("DAFSAFAS"+hel);
			if(hel.type == "comp"){
				console.log(hel);
	         	$("#textApp").text("결재가 완료되었습니다.")
	         	$("#textApp").slideDown();
	         	$("#textApp").delay(3000).slideUp();
			}else if(hel.type == "banryu"){
				console.log(hel);
				$("#textApp").text(hel.susin+" 님이 기안을 반려하였습니다.")
            	$("#textApp").slideDown();
            	$("#textApp").delay(3000).slideUp();
			}
    	});
	});
	
	// 채팅입력
	$("#btnSend").on("click",function(){
		chatSend();
	});
	
	//엔터키
	$("#chatCon").keydown(function(key){
		if(key.keyCode == 13){
      		if(!key.shiftKey){
				key.preventDefault();
             	chatSend();
             	$("#chatCon").empty();
          	}
   		}
	});
	
	//나갈때 이벤트 발생
	$(window).on("beforeunload",function(){
		alert('닫으시겠습니까?');
		roomOut(room_id, empId);
		stomp.disconnect();
	});
	
//	$(window).on("unload",function(){
//		alert('닫으시겠습니까?');
//	});
	
	//drag & drop
	// dragenter : 마우스가 대상 객체의 위로 처음 진입할 때 발생함.
	$(document).on("dragenter", ".rContent-full", function(e) {
		//브라우저에서 기본으로 제공하는 드래그앤드롭 이벤트를 막아줘야 정상작동
		e.stopPropagation(); // 브라우저가 해당 이벤트에 대해 수행하는 기본적인 작업을 방지. 예를 들어 파일을 내려놓을 때 새탭으로 파일정보를 보여주는 이벤트를 방지 
		e.preventDefault(); // 나를 둘러싸고 있는 이벤트의 추가전파 방지
		$(this).css('border', '1px solid red');
	});
	// dragover : 드래그하면서 마우스가 대상 객체의 위에 자리 잡고 있을 때 발생함.
	$(document).on("dragover", ".rContent-full",function(e) {
		e.stopPropagation();
		e.preventDefault();
	});
	// drop : 	드래그가 끝나서 드래그하던 객체를 놓는 장소에 위치한 객체에서 발생함.
	$(document).on("drop", ".rContent-full", function(e) {
		console.log("drop");
		// 브라우저로 이동되는 이벤트를 방지하고 드랍 이벤트를 우선시 한다.
		e.preventDefault();
		// DROP 되는 위치에 들어온 파일 정보를 배열 형태로 받아온다.
		// drag&drop 한 모든 파일들의 정보를 가진 FileList 객체
		var files = e.originalEvent.dataTransfer.files;
		console.log("files",files);
		$(this).css('border', '1px solid black');
		// DIV에 DROP 이벤트가 발생 했을 때 다음을 호출한다.
		chatHandlerFileUpload(files);
	});
	
	//파일  크기 판단
	function chatHandlerFileUpload(files) {
		chatFd = new FormData();
		console.log("handle에 ",files);
		// 파일의 길이만큼 반복하며 formData에 셋팅해준다.
		var megaByte = 1024*1024;
		console.log(files.length);
		for (var i = 0; i < files.length; i++) {
			// containsValue : map에 value가 있는지 확인
			if((files[i].size/megaByte)>10){
				// 중복되는 정보 확인 위해 콘솔에 찍음
				alert(files[i].name+"은(는) \n 10메가 보다 커서 업로드가 할 수 없습니다.");
				files[i].remove;
			}else{
				console.log("append", files[i]);
				chatFd.append('file', files[i]);
			}
		}
		sendFileToServer(chatFd);
	}
	
	$("#chatFile").change(function (){
		var files = e.originalEvent.dataTransfer.files;
		console.log(files);
	});
	
	// 알림을 뿌려주는 아작스
	// 우연
	$.ajax({
		url:"/DoitDoit_Project/comm/selAlaram.do",
		data:{emp_id:$("#empN").val()},
		type:"post",
		success:function(tag){
			console.log("성공")
			console.log(tag)            
			let html = "";
			if(tag.length>0){
				$("#bell").attr("src","./img/bellon32.png");
			}else{
				$("#bell").attr("src","./img/bell32.png");
				$("#approWindow").append("<div style='width:100%; font-size:25px; top:40%; position:absolute; text-align:center;'>알림이 없습니다.</div>")
			}
			$.each(tag,function(index,val){
				html +="<div id='alaDiv' style='border:1px solid #000; width: 100%; height:70px;' onclick='hello()'>";
				html +="	<input type='hidden' id='calId' value='"+val.cald_id+"'> ";
				html +="	<span style='font-size:13px; text-align:left;'>"+val.eboard_regdate+"</span><br> ";
				html +="	<span style='font-size:13px; text-align:center;'>"+val.eboard_title+"</span> ";
				html +="</div>";
			})
			$("#approWindow").append(html);
		},
		error:function(request, status, error){
			alert("code:"+request.status+"\n"+"\n"+"error:"+error);
		}
	});
});

function hello(){

		$.ajax({
			url:"/DoitDoit_Project/comm/delAlarm.do",
			data:{cald_id:$("#calId").val()},
			type:"post",
			success:function(text){
				console.log("성공여부",text)
				if(text==true){
					location.href='/DoitDoit_Project/appro/approMain.do';
				}
		}
	});
}

function chatList(roomId,chatCon,type,roomName){
	console.log("chatList 실행");
	var child = ($("#chatRoom").find("#"+roomId));
	console.log("child",$(child).text());
	var date = new Date();
	if($(child).text() == ""){
		console.log
		var html; 
		html = "<div id=\""+roomId+"\">";
		html += 	"<a href=/DoitDoit_Project/comm/chatRoom.do?room_id="+roomId+">";
		html += 		"<div class=\"roomName\">";
		html += 			"<div>";
		html +=					"<span class=\"read\" style=\"color:\"red\"> ● </span>";
		html += 				"<span style=\"font-size: 10px; color: white;\">"+roomName+"</span>";
		html += 			"</div>";
		html += 			"<div>"
		if(type == "F"){
			html +=				"<span style=\"color: #FCFCFC;\">파일이 전송 되었습니다.</span>";
		}else{
			html +=				"<span style=\"color: #FCFCFC;\">"+chatCon+"</span>";
		}
		html +=					"<p><span style=\"color: #EAEAEA;\">"+date.format('yyyy-MM-dd HH:mm:ss')+"</span><p>";
		html +=				"</div>";
		html += 		"</div>";	
		html += 	"</a>";
		html += 	"<hr>";		
		html += "</div>";
		$("#chatRoom").prepend(html);
	}else{
		$(child).find(".roomName > div:eq(1) > span:eq(0)").html(chatCon);
		$(child).find(".roomName > div:eq(1) > p > span").text(date.format('yyyy-MM-dd HH:mm:ss'));
		$(child).find(".roomName > div:eq(0) > .read").css("color","#FCFCFC");
		if(type == "F"){
			$(".roomName > div:eq(1) > span:eq(0)>.imageMsg").remove();
			$(".roomName > div:eq(1) > span:eq(0)>.saveFile").remove();	
			var msg = "<span class=\"msg\">파일이 전송 되었습니다.</span>";
			$(child).find(".roomName > div:eq(1) > span:eq(0)").append(msg);
		}
		
		var prependHtml = "<div id=\""+roomId+"\">" + $(child).html() + "</div>";
		$(child).remove();
		$("#chatRoom").prepend(prependHtml);
	}
}

//우연 시작
function sendEmp_No(empNo){
   	$("#frClick").click(function(){
 		empArr = empNo;
 		onSendApprMessage();
   });
}


function onSendApprMessage(){
  	console.log(empArr[0])
	susin = empArr[0];
	barsin = $("#imagename").val();
 	stomp.send("/pub/alarm/"+susin,{},JSON.stringify({type:"appr",barsin:barsin,susin:susin}));
}

function gyuljaeClick2(empList){
   	console.log("기안자 아이디 "+$("#gianja").val());
   	console.log(empList);
   	var json = JSON.parse(empList);
   	console.log("JSON 파싱 후 ",json.approval)
   	for(let i=0; i<json.approval.length; i++){
      	if(json.approval[i].EMP_ID==$("#empN").val()){
         	if(json.approval[i+1] !== undefined){
            	susin = json.approval[i+1].EMP_ID;
            	stomp.send("/pub/alarm/"+susin,{},JSON.stringify({type:"appr",barsin:$("#gianja").val(),susin:susin}));
     		}else{
            	susin = json.approval[i].EMP_ID;
        		stomp.send("/pub/apprMem/complet/"+$("#gianja").val(),{},JSON.stringify({type:"comp",susin:susin,barsin:$("#gianja").val()}));
     		} 
      	}
	}
}

function gyuljaeBanryu(empList){
   	console.log("기안자 아이디 "+$("#gianja").val());
   	var json = JSON.parse(empList);
   	console.log("JSON 파싱 후 ",json.approval)
   	for(let i=0; i<json.approval.length; i++){
      	if(json.approval[i].EMP_ID==$("#empN").val()){
				susin = json.approval[i].EMP_ID;
        		stomp.send("/pub/apprMem/complet/"+$("#gianja").val(),{},JSON.stringify({type:"banryu",susin:susin,barsin:$("#gianja").val()}));
      	}
	}
}


function disconn(){
	if(stomp.ws.readyState === WebSocket.OPEN){
		stomp.send("/pub/logout",{},JSON.stringify({emp_id:$("#empN").val()}))	
		stomp.disconnect(function(){
		});
	}
}
//우연 끝

//채팅 컨트롤러로 보내는 영역
function chatSend(){
	if($("#chatCon").val().trim() != ""){			
		stomp.send('/pub/chat/message', {}, JSON.stringify({ room_id: room_id, chat_con: $("#chatCon").val(), emp_id: emp_id, user_name: user_name, type: "T"}));
		$("#chatCon").val("");
	}else{
		alert('채팅을 입력해 주세요');
		$("#chatCon").val("");
	}
}

//채팅방 나가기(disconnect)
function getOut(){
	console.log("getOut() 실행");	
	stomp.send('/pub/chat/message', {}, JSON.stringify({room_id: room_id, emp_id: emp_id, user_name: user_name, type: "O"}));
	stomp.send('/sub/chat/getOut/' + room_id, {}, JSON.stringify({emp_id: emp_id}));
	
	location.href="./gohome.do";
}

//채팅방 입장 시 
function roomEnter(room_id,username){
	console.log("roomEnter");
	stomp.send('/pub/chat/enter', {}, JSON.stringify({ room_id: room_id, emp_id: emp_id }));
}

//채팅방 닫기 실행 시
function roomOut(room_id, username){
   stomp.send('/pub/chat/out', {}, JSON.stringify({ room_id: room_id, emp_id: emp_id, user_name: user_name }));
}

//채팅방에 있는지 없는지 판단
function aboutChatRoom(mems){
	var emps = $("#members").children();
	for(var i = 0; i < emps.length; i++){
		if($.inArray(emps.eq(i).attr("id"), mems) > -1){
			$("#members > div").eq(i).children().eq(0).text("O");
			console.log("있음");
		}else{
			$("#members > div").eq(i).children().eq(0).text("X");
			console.log("없음");
		}
	}
}

//ajax로 파일 입력
function sendFileToServer(chatFd) {	
	
	chatFd.append("user_name",user_name);
	chatFd.append("room_id",room_id);
	
	$.ajax({
		type : "POST",
		data : chatFd,
		url : "/DoitDoit_Project/comm/saveChatFile.do",
		enctype: "multipart/form-data",
		contentType : false, // default 값은 "application/x-www-form-urlencoded; charset=UTF-8","multipart/form-data"로 전송되도록 false 설정
		processData : false, // 일반적으로 서버에 전달되는 데이터는 query string 형태임
		cache : false, //ajax 로 통신 중 cache 가 남아서 갱신된 데이터를 받아오지 못할 경우를 대비
		success : function(map){
			stomp.send('/pub/chat/message', {}, JSON.stringify({ room_id: room_id, html: map.html ,emp_id: emp_id, user_name: user_name, type: map.type }));
		},
		error:function(){
			alert("파일업로드 실패");
		}
	});
}	

//날짜 형식
Date.prototype.format = function (f) {

    if (!this.valueOf()) return " ";

    var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];
    var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {

        switch ($1) {

            case "yyyy": return d.getFullYear(); // 년 (4자리)
            case "yy": return (d.getFullYear() % 1000).zf(2); // 년 (2자리)
            case "MM": return (d.getMonth() + 1).zf(2); // 월 (2자리)
            case "dd": return d.getDate().zf(2); // 일 (2자리)
            case "KS": return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)
            case "KL": return weekKorName[d.getDay()]; // 요일 (긴 한글)
            case "ES": return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)
            case "EL": return weekEngName[d.getDay()]; // 요일 (긴 영어)
            case "HH": return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2); // 시간 (12시간 기준, 2자리)
            case "mm": return d.getMinutes().zf(2); // 분 (2자리)
            case "ss": return d.getSeconds().zf(2); // 초 (2자리)
            case "a/p": return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분

            default: return $1;
        }
    });
};

String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };
String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };
Number.prototype.zf = function (len) { return this.toString().zf(len); };