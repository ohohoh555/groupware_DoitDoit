var stomp;
var room_id;
var emp_id;
var user_name;
var chatCon;

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
		console.log("STOMP Connection")
	
		//4. subscribe(path, callback)으로 메세지를 받을 수 있음
		// 채팅방
		stomp.subscribe("/sub/chat/room/" + room_id, function(chat) {
			console.log(chat);
			var content = JSON.parse(chat.body);
			
			var message = content.chat_con;
			
			
			$("table>tbody").append(html);
		});
		
		roomEnter(room_id,emp_id);
		
		stomp.subscribe("/sub/chatMem/room/" + room_id, function(member){
			var mems = member.body;
			mems = mems.replaceAll('"', '');
			mems = mems.replace('[', '');
			mems = mems.replace(']', '');
			mems = mems.split(",");
			console.log(mems);
			console.log(mems.length);
			
			html = '';
			
			for(var i = 0; i < mems.length; i++){
				html += "<div>";
				html += mems[i];
				html += "</div>";
			}
			
			$("#chat_memList").html(html);
		});
		
	});
	// 채팅입력
	$("#btnSend").on("click",function(){
		chatCon = $("#chatCon");
		console.log("chatCon", chatCon);
		console.log("send",chatCon.val());
		
		chatSend(room_id,chatCon,emp_id,user_name);
	});
	
	$(window).on("beforeunload",function(){
		roomOut(room_id, empId);
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
			}else{
				console.log("append", files[i]);
				fd.append('file', files[i]);
			}
		}
		sendFileToServer(fd);
	}
});

function enterkey(){
	if(window.event.keyCode == 13){
		chatCon = document.getElementById("chatCon");
		chatSend(room_id,chatCon,empId);
	}
}

function chatSend(room_id,chatCon,empId){
	if(chatCon.val != ""){
		stomp.send('/pub/chat/message', {}, JSON.stringify({ room_id: room_id, chat_con: chatCon.val(), emp_id: emp_id, user_name: user_name}));
		chatCon.value = '';
	}else{
		alert('채팅을 입력해 주세요');
	}
}

//채팅방 입장 시 
function roomEnter(room_id,username){
	stomp.send('/pub/chat/enter', {}, JSON.stringify({ room_id: room_id, emp_id: emp_id }));
}

//채팅방 닫기 실행 시
function roomOut(room_id, username){
	stomp.send('/pub/chat/out', {}, JSON.stringify({ room_id: room_id, emp_id: emp_id }));
}

function sendFileToServer(fd) {
	//FormData()에 room_id와 empId 추가
	fd.append('room_id',room_id);
	fd.append('emp_id',emp_id);
	
	var uploadURL = "./saveFile.do"; 
	$.ajax({
		type : "POST",
		data : fd,
		url : uploadURL,
		enctype: "multipart/form-data",
		contentType : false, // default 값은 "application/x-www-form-urlencoded; charset=UTF-8","multipart/form-data"로 전송되도록 false 설정
		processData : false, // 일반적으로 서버에 전달되는 데이터는 query string 형태임
		cache : false, //ajax 로 통신 중 cache 가 남아서 갱신된 데이터를 받아오지 못할 경우를 대비
		dataType:"String",
		success : function(data) {
			console.log("파일 업로드 성공");		
		},
		error:function(data){
			alert("파일업로드 실패");
		}
	});
}