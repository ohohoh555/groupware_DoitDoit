//폼에 데이터를 추가하기 위해 (파일을 전송하기 전 정보 유지)
// <form>이 없어서 빈객체로 생성해줌 
	var fd = new FormData();
	// 파일 중복 업로드 방지용 맵을 선언한다.
	var map = new Map(); // Map.prototype(); 객체는 언제나 함수형태로 생성
	// form 에 데이터 추가 시 동적으로 name 을 주기 위해 사용
	var k = 0;
	
	//파일 업로드창 열고 닫는 함수
	function slideDown(){
		var uploadForm = $('.hide10');
		console.log(uploadForm);
		if(uploadForm.is(":visible")){
			uploadForm.slideUp();
		}else{
			uploadForm.slideDown();
		}
	}

	
	// 업로드 할 파일을 제거할 때 수행되는 함수
	function delFile(e){
		// tr을 삭제하기 위해
		var tr = $(e).parent().parent();
		// 파일 이름을 받아오기 위해 string 변수 선언
		var formName = '';
		// 선택한 창의 아이디가 파일의 form name이므로 아이디를 받아온다.
		formName = e.id;
		// form에서 데이터를 삭제한다.
		fd.delete(formName);
		// 맵에서도 올린 파일의 정보를 삭제한다.
		map.remove(formName);
		tr.remove();
		alert('삭제 완료!');
	}
	// submit 버튼을 눌렀을 때
	function submitFile(){
		// 추가적으로 보낼 파라미터가 있으면 formData에 넣어준다.
		// 예를들어 , 게시판의 경우 게시글 제목 , 게시글 내용 등등
		fd.append('title',$('#title').val());
		fd.append('content',$('#content').val());
		// ajax로 이루어진 파일 전송 함수를 수행시킨다.
		sendFileToServer(fd);
	}
	
	// 파일 전송 함수이다.
	function sendFileToServer(formData) {
		var uploadURL = "./saveFile.do"; 
		var extraData = {}; // Extra Data.
		jQuery.ajax({
			type : "POST",
			url : uploadURL,
			data : formData,
			contentType : false, // default 값은 "application/x-www-form-urlencoded; charset=UTF-8","multipart/form-data"로 전송되도록 false 설정
			processData : false, // 일반적으로 서버에 전달되는 데이터는 query string 형태임
			cache : false, //ajax 로 통신 중 cache 가 남아서 갱신된 데이터를 받아오지 못할 경우를 대비
			success : function(data) {
				alert(data);
				$('#fileTable tr:not(:first)').remove(); //첫번째 행 제외하고 모두 삭제 
				
			},
			error:function(data){
				alert("파일업로드 실패");
			}
		});
	}
	
	
	$(document).ready(function() {

		var objDragAndDrop = $(".dragAndDropDiv");
		// dragenter : 마우스가 대상 객체의 위로 처음 진입할 때 발생함.
		$(document).on("dragenter", ".dragAndDropDiv",
				function(e) {
					//브라우저에서 기본으로 제공하는 드래그앤드롭 이벤트를 막아줘야 정상작동
					e.stopPropagation(); // 브라우저가 해당 이벤트에 대해 수행하는 기본적인 작업을 방지. 예를 들어 파일을 내려놓을 때 새탭으로 파일정보를 보여주는 이벤트를 방지 
					e.preventDefault(); // 나를 둘러싸고 있는 이벤트의 추가전파 방지
					$(this).css('border', '2px solid red');
				});
		// dragover : 드래그하면서 마우스가 대상 객체의 위에 자리 잡고 있을 때 발생함.
		$(document).on("dragover", ".dragAndDropDiv",
				function(e) {
					e.stopPropagation();
					e.preventDefault();
				});
		// drop : 	드래그가 끝나서 드래그하던 객체를 놓는 장소에 위치한 객체에서 발생함.
		$(document).on("drop", ".dragAndDropDiv", function(e) {
	
			$(this).css('border', '2px solid #0B85A1');
			// 브라우저로 이동되는 이벤트를 방지하고 드랍 이벤트를 우선시 한다.
			e.preventDefault();
			// DROP 되는 위치에 들어온 파일 정보를 배열 형태로 받아온다.
			// drag&drop 한 모든 파일들의 정보를 가진 FileList 객체
			var files = e.originalEvent.dataTransfer.files;
			console.log(files);
			// DIV에 DROP 이벤트가 발생 했을 때 다음을 호출한다.
			handleFileUpload(files);
		});
	
		$(document).on('dragenter', function(e) {
			e.stopPropagation();
			e.preventDefault();
		});
		$(document).on('dragover', function(e) {
			e.stopPropagation();
			e.preventDefault();
			objDragAndDrop.css('border', '2px solid #0B85A1');
		});
		$(document).on('drop', function(e) {
			e.stopPropagation();
			e.preventDefault();
		});
		
		
		function handleFileUpload(files) {
			// 파일의 길이만큼 반복하며 formData에 셋팅해준다.
			var megaByte = 1024*1024;
			for (var i = 0; i < files.length; i++) {
				// containsValue : map에 value가 있는지 확인
				if(map.containsValue(files[i].name)== false && (files[i].size/megaByte)<=10){
					fd.append('file'+k, files[i]);
					// 파일 중복 업로드를 방지하기 위한 설정
					map.put('file'+k,files[i].name);
					// 파일 이름과 정보를 추가해준다.
					var fileList = new createFileList(); 
					fileList.setFileNameSize(files[i].name,files[i].size);
				}else{
					// 중복되는 정보 확인 위해 콘솔에 찍음
					if((files[i].size/megaByte) > 10){
					alert(files[i].name+"은(는) \n 10메가 보다 커서 업로드가 할 수 없습니다.");
					}
					else{
					console.log('파일 중복이다 파일명 = ' + files[i].name + ' / map에서 꺼냄 = '+ map.containsValue(files[i].name)  );
					}
				}
			}
		}
		
		function createFileList(obj) {
			
			var fileTable = $('#fileTable');
			this.inputFileInfo = $("<tr></tr>");
			this.filename = $("<td class='filename addTd filename'></td>")
					.appendTo(this.inputFileInfo);
			this.size = $("<td class='filesize addTd'></td>")
					.appendTo(this.inputFileInfo);
			this.fileCancelBtn = $("<td class='addTd'>"
					+"<button class='delBtn' id='file"+k+"' onclick='delFile(this)'>취소</button>"
					+"</td>").appendTo(this.inputFileInfo);
			k++;
			fileTable.append(this.inputFileInfo);
			this.setFileNameSize = function(name, size) {
				var sizeStr = "";
				var sizeKB = size / 1024;
				if (parseInt(sizeKB) > 1024) {
					var sizeMB = sizeKB / 1024;
					sizeStr = sizeMB.toFixed(2) + " MB";
				} else {
					sizeStr = sizeKB.toFixed(2) + " KB";
				}
				// 파일이름이 너무 길면 화면에 표시해주는 이름을 변경해준다.
				if(name.length > 70){
					name = name.substr(0,68)+"...";
				}
				this.filename.html(name);
				this.size.html(sizeStr);
			}
		}
		});
	
	
	function go_list() {
		location.href = "./boardList.do";
	}