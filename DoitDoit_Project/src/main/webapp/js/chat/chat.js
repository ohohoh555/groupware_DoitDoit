var stomp;
var room_id;
var emp_id;
var user_name;

// 파일 중복 업로드 방지용 맵을 선언한다.
//var map = new Map(); // Map.prototype(); 객체는 언제나 함수형태로 생성
//파일 업로드
var fd = new FormData();
// form 에 데이터 추가 시 동적으로 name 을 주기 위해 사용
var k = 0;

$(document).ready(function() {

	console.log("js실행");
	var sock = new SockJS("/DoitDoit_Project/stompSocket");
	console.log(sock);
	
	//방 id
	room_id = $("#room_id").val();
	console.log("room_id", room_id);
	// emp_id
	emp_id = $("#pr_emp_id").val();
	console.log("emp_id", emp_id);
	// emp 성함
	user_name = $("#pr_user_name").val();
	console.log("user_name",user_name);
	
	stomp = Stomp.over(sock);
	stomp.connect({}, function() {
		console.log("STOMP Connection");
		
		//들어 갔을 때 스크롤 밑으로 내리기
		$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
		
		//4. subscribe(path, callback)으로 메세지를 받을 수 있음
		// 메세지 받기
		stomp.subscribe("/sub/chat/room/" + room_id, function(chat) {
			console.log("메시지 받음",chat);
			var content = JSON.parse(chat.body);
			var clas;
			
			//누구 메세지 인지 판단
			if(content.emp_id == 0){
				clas = "msg";
			}else if(content.emp_id == emp_id){
				clas = "myMsg";
				$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
			}else{
				clas = "anotherMsg";
			}
						
			//append 새로운 메시지
			var html = '';
			html += "<div class=\""+clas+"\">";
			html += 	content.chat_con;
			html += "</div>"; 
			$("#chatLog:last-child").append(html);
			
			//scrollbar가 내려 갔을시 추가 되는 스크롤 자동으로 내려줌
			if($('#chatLog').scrollTop()){
				$('#chatLog').scrollTop($('#chatLog')[0].scrollHeight);
			};
			
			if(content.type == "O"){
				chatMemRemove(content.emp_id);
				if(content.emp_id == emp_id){
					location.href="./gohome.do";
				}
			}
		});
		
		//들어 왔을떄
		stomp.subscribe("/sub/chatMem/room/" + room_id, function(member){
			console.log("chatMem Enter");
			var mems = member.body;
			mems = mems.replaceAll('"', '');
			mems = mems.replace('[', '');
			mems = mems.replace(']', '');
			mems = mems.split(",");
			console.log(mems);
			
			html = '';
			
			for(var i = 0; i < mems.length; i++){
				html += "<div>";
				html += mems[i];
				html += "</div>";
			}
			
			aboutChatRoom(mems);
		});
		
		roomEnter(room_id,emp_id);
		
		stomp.subscribe("/sub/chat/roomOut/" + room_id, function(out){
			console.log("나간거 이벤트 발생", out);
		});
	});
	// 채팅입력
	$("#btnSend").on("click",function(){
		chatSend();
	});
	
	//엔터키
	$("#chatCon").keydown(function(key){
		if(key.keyCode == 13){
      		chatSend();
   		}
	});
	
	//나갈때 이벤트 발생
	$(window).on("beforeunload",function(){
		roomOut(room_id, empId);
		stomp.disconnect();
	});
	
	//drag & drop
	var objDragAndDrop = $("#dragdrop");
	// dragenter : 마우스가 대상 객체의 위로 처음 진입할 때 발생함.
	$(document).on("dragenter", "#dragdrop",
			function(e) {
				//브라우저에서 기본으로 제공하는 드래그앤드롭 이벤트를 막아줘야 정상작동
				e.stopPropagation(); // 브라우저가 해당 이벤트에 대해 수행하는 기본적인 작업을 방지. 예를 들어 파일을 내려놓을 때 새탭으로 파일정보를 보여주는 이벤트를 방지 
				e.preventDefault(); // 나를 둘러싸고 있는 이벤트의 추가전파 방지
				$(this).css('border', '1px solid red');
			});
	// dragover : 드래그하면서 마우스가 대상 객체의 위에 자리 잡고 있을 때 발생함.
	$(document).on("dragover", "#dragdrop",
			function(e) {
				e.stopPropagation();
				e.preventDefault();
			});
	// drop : 	드래그가 끝나서 드래그하던 객체를 놓는 장소에 위치한 객체에서 발생함.
	$(document).on("drop", "#dragdrop", function(e) {
		console.log("drop");
		// 브라우저로 이동되는 이벤트를 방지하고 드랍 이벤트를 우선시 한다.
		e.preventDefault();
		// DROP 되는 위치에 들어온 파일 정보를 배열 형태로 받아온다.
		// drag&drop 한 모든 파일들의 정보를 가진 FileList 객체
		var files = e.originalEvent.dataTransfer.files;
		console.log("files",files);
		$(this).css('border', '1px solid black');
		// DIV에 DROP 이벤트가 발생 했을 때 다음을 호출한다.
		handleFileUpload(files);
	});
	
	//파일  크기 판단
	function handleFileUpload(files) {
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
				fd.append('file', files[i]);
			}
		}
		sendFileToServer(fd);
	}
});

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

function chatMemRemove(emp_id){
	var emps = $("#members").children();
	for(var i = 0; i < emps.length; i++){
		if($.inArray(emps.eq(i).attr("id"), emp_id)){
			$("#members > div").eq(i).remove();
		}
	}
}

//ajax로 파일 입력
function sendFileToServer(fd) {	
	$.ajax({
		type : "POST",
		data : fd,
		url : "./saveFile.do",
		enctype: "multipart/form-data",
		contentType : false, // default 값은 "application/x-www-form-urlencoded; charset=UTF-8","multipart/form-data"로 전송되도록 false 설정
		processData : false, // 일반적으로 서버에 전달되는 데이터는 query string 형태임
		cache : false, //ajax 로 통신 중 cache 가 남아서 갱신된 데이터를 받아오지 못할 경우를 대비
		success : function(map){
			stomp.send('/pub/chat/message', {}, JSON.stringify({ room_id: room_id, html: map.html ,emp_id: emp_id, user_name: user_name, type: "F"}));
		},
		error:function(){
			alert("파일업로드 실패");
		}
	});
}	

function getOut(){
	console.log("getOut() 실행")
	stomp.send('/pub/chat/message', {},JSON.stringify({room_id: room_id, emp_id: emp_id, user_name: user_name, type: "O"}));
}