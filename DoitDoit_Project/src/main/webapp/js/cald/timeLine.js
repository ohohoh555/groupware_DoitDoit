//fullcalendar
	 document.addEventListener('DOMContentLoaded', selectAjax());

function selectAjax(){
	$.ajax({
				type:"get",
				url:"./calendarAjax.do",
				dataType:"json",
				success:function(data){
					console.log(data)
					console.log(data[0].events)
					var calendarEl = document.getElementById('calendar');
			        var calendar = new FullCalendar.Calendar(calendarEl, {
					slotMinTime:"09:00:00",
					slotMaxTime:"18:00:00",
			        initialView: 'resourceTimelineDay', // 처음 로드될때 보이는 캘린더 화면(default : 달)
			        locale: 'ko', // 한국어 설정
					selectable : true, // 달력 드래그 가능
					dayMaxEventRows: true, // 하루에 일정이 여러개여서 화면을 넘어가는 경우 + more 로 표시하게 됨
					scrollTime: '08:00',
					aspectRatio: 1.5,
			        headerToolbar: { //헤더에 표시될 툴 바
						start : 'prev today next', // 1달 전, 1달 후 , 오늘 버튼을 캘린더 상단 왼쪽에 생성
						center : 'title', // 캘린더의 상단 중앙에 title을 찍음
						end : '' // 1달 기준으로 보기, 일주일 기준으로 보기, 4일 기준으로 보기
			        },
			        editable: true, //이벤트들을 드래그 가능하게 만들어 줌
					// 상단의 년 월 을 어떻게 찍을지 만들어줌 ex) 2022년 5월
					titleFormat : function(date){
						return date.date.year + '년 ' + (parseInt(date.date.month)+1) + '월'+date.date.day+'일';
					},
					// 캘린더에 띄울 화면을 직접 생성
					views:{
						  resourceTimelineDay: 
						{
				          buttonText: ':15 slots',
				          slotDuration: '00:15'
				        },
						timeGridFourDay:{
							type:'timeGrid', //타임 그리드 형식으로
							duration:{days:4}, // 4일씩
							buttonText:'4Day', // 버튼에 나타낼 텍스트
							allDayText: '오늘', // 버그인지 all-day 라는 부분은 국제화 코드로 바뀌지 않아 직접 지정
						}
					},
					
					// 캘린더에 입력될 값
			        events: data[0].events,
					resources:data[1].resources,
					
					// 이벤트 클릭 시 동작
					eventClick: function(info) {
							$("#modalContent").val("");
							$("#datetimepicker1_2").val("");
							$("#datetimepicker2_2").val("");
							var isc = $("#modifyButton").is(":checked");
							console.log(isc);
							console.log(info.event)
							var modal = $("#schedule-edit");
							var detailModal = $("#schedule-detail");
							// 이벤트 클릭시 모달 오픈
							if(isc){
								console.log(data[0].events)
								modal.modal({backdrop: 'static', keyboard: false});
								$("#id").val(info.event.id);
								$("#modalTitle").val(info.event.title);
								$("#modalWriter").val(info.event.extendedProps.writer);
								$("#modalContent").attr("placeholder", info.event.extendedProps.description);	
							}else{
								detailModal.modal({backdrop: 'static', keyboard: false});
								$("#id2").val(info.event.id);
								$("#modalTitle2").val(info.event.title);
								$("#modalContent2").val(info.event.extendedProps.description);
								$("#modalWriter2").val(info.event.extendedProps.writer);
								$("#start").val(info.event._instance.range.start)
								$("#end").val(info.event._instance.range.end)
							}
							
						},
						eventDidMount : function(info){
							tippy(info.el,{
								content : info.event.title
							});
						},
						//드래그 후 이벤트(일정게시글)를 떨어트렸을때 동작되는 콜백함수
				eventDrop: function(info) {
					console.log(dateFormat(info.event.start));
					console.log(dateFormat(info.event.end));
					console.log(info.event.id);
					// info.event.start(Thu May 12 2022 09:30:00 GMT+0900 (한국 표준시)) 를 202205120903 형식으로 바꾸어 
					// updateDragAjax를 통해 일정 업데이트
					updateDragAjax(dateFormat(info.event.start), dateFormat(info.event.end), info.event.id);
				}
			        });
				// 달력 초기화시 필수임
			    calendar.render();
				},
				error:function(error){
					alert("통신 실패 : "+error.status)
				}
			});
}

function insertAjax(){
	console.log($("#datetimepicker1").val())
		if($("#writer").val()==""){
			alert("작성자를 입력해주세요")
			$("#writer").focus();
			return false;
		}
		if($("#title").val()==""){
			alert("예약 제목을 입력해주세요")
			$("#title").focus();
			return false;
		}
		if($("#content2").val()==""){
			alert("내용을 입력해주세요")
			$("#content2").focus();
			return false;
		}
		if($("#datetimepicker1").val() == ""|| $("#datetimepicker2").val() == ""){
			alert("시간을 입력해 주세요")
			return false;
		}
		
		// 시작일이 종료일보다 클 시 아작스 강제 종료
		if(dateVal('datetimepicker1','datetimepicker2') == false){
			return false;
		}
		
		if(dateFormat(new Date)>dateFormat($("#datetimepicker1").datetimepicker('getValue'))){
			alert("현재 날짜 이전의 예약입니다")
			return false;
		}
		
		// serialize() 값 : 작성자(writer), 일정명(title), 내용(content), 그룹(group), 일정 시작일(start), 일정 종료일(end)
		var str = $("#frm").serialize()
		var res = $("#res option:selected").val();
//		str += "resourceId="+res;
		console.log(res)
		console.log(str)

		// 인서트 성공 시 결과값을 boolean으로 반환
		$.ajax({
			url:"./calendarInsert.do",
			data:str,
			type:"post",
			dataType:"json",
			success:function(msg){
				console.log(msg);
				if(msg != true){
					alert("일정 등록에 실패하였습니다.")
					return false;
				}else{
//					$("#frm input[type=text]").attr("value","");
//					console.log($("#frm input[type=text]"))
					$("#frm input[type=text]").each(function(index, item){
						console.log($(item).val(""));
					})
					
//					for(let i=0; i<$("#frm input").length-2; i++){
//						$("#frm input")[i].val(0)
//					}
					$("#res option:eq(0)").attr("selected","selected")
					$("#grId option:eq(0)").attr("selected","selected")
					selectAjax();
					console.log("ㅎㅇㅎㅇ")
				}
			},
			error:function(){
				alert("예약 등록 실패");
			}
		})
	}
	
//// 드래그를 통한 시간 업데이트 ajax
//// 파라미터 값 : 시작일(start), 종료일(end), 아이디(id)
//// 돌아오는 반환값(data)값 : boolean
//// true : 성공 / false : 실패
function updateDragAjax(start, end, id) {
	console.log(start);
	console.log(end);
	console.log(id);
	$.ajax({
		url: "./uadateDragAjax.do",
		type: "post",
		data: { "resv_start": start, "resv_end": end, "resv_id": id },
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data != true) {
				selectAjax();
				alert("잘못된 드래그 수정입니다.")
				return false;
			} else {
				console.log("성공")
			}
		},
		error: function() {
			alert("에러")
		}
	});
}

function updateContent() {
	var frm = $("#modalFrm").serialize();
	var content = $("#modalContent");
	var id = $("#id").val();
	console.log(id)
	var start = dateFormat($("#datetimepicker1_2").datetimepicker('getValue'));
	var end = dateFormat($("#datetimepicker2_2").datetimepicker('getValue'));
	
	console.log(start)
	console.log(end)
	if (content.val() == "") {
		alert("내용을 입력해주세요")
		content.focus();
		return false;
	}

	if ($("#datetimepicker1_2").val() == "" || $("#datetimepicker2_2").val() == "") {
		alert("시간을 입력해 주세요")
		return false;
	}

	if (dateVal('datetimepicker1_2', 'datetimepicker2_2') == false) {
		return false;
	}
	
	if(dateFormat(new Date)>dateFormat($("#datetimepicker1_2").datetimepicker('getValue'))){
		alert("현재 날짜 이전의 예약입니다")
		return false;
	}
		
	console.log(frm);
	$.ajax({
		url: "./uadateAjax.do",
		type: "post",
		data: 
		{
			"resv_id":id,
			"resv_description":content.val(),
			"resv_start":start,
			"resv_end":end
		},
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data != true) {
				content.val("");
				$("#datetimepicker1_2").val("");
				$("#datetimepicker2_2").val("");
				console.log("실패");
				$("#schedule-edit").modal("hide");
				return false;
			} else {
				$("#schedule-edit").modal("hide");
				console.log("성공")
				selectAjax();
			}
		},
		error: function() {
			alert("에러")
		}
	});
}

function deleteContent() {
	var id = $("#id2").val();
	var end = $("#end").val();
	$.ajax({
		url: "./deleteAjax.do",
		type: "post",
		data: { "resv_id": id, "resv_end":end },
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data != true) {
				$("#schedule-detail").modal("hide");
				console.log("실패");
				return false;
			} else {
				$("#schedule-detail").modal("hide");
				console.log("성공")
				selectAjax();
			}
		},
		error: function() {
			alert("에러")
		}
	});
}

function dateVal(dtp, dtp2){
	// start 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
	let date1 = $("#"+dtp+"")
	let i = date1.datetimepicker('getValue');
	// end 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
	let date2 = $("#"+dtp2+"")
	let i2 = date2.datetimepicker('getValue');
	console.log(i, i2)
	// test.valueOf() 밀리세컨트로 반환
	// 밀리세컨트로 반환한 i 와 i2 의 시간을 비교하여 시작일보다 종료일이 작은 경우를 방지
	if(i.valueOf()>=i2.valueOf()){
		date1.val("")
		date2.val("")
		alert("일정 종료일이 시작일보다 과거또는 같은 시간입니다.")
		return false;
	}
}

// date( Thu May 12 2022 09:30:00 GMT+0900 (한국 표준시) ) 를 202205120903 형식으로 바꾸어줌
function dateFormat(date) {
	let year = date.getFullYear();
	let month = zeroPlus(date.getMonth() + 1);
	let day = zeroPlus(date.getDate());
	let hour = zeroPlus(date.getHours());
	let min = zeroPlus(date.getMinutes());
	let startVal = year + "" + month + "" + day + "" + hour + "" + min
	return startVal;
}

// 만약 1~9 월일 경우 뒤에 0을 붙여주어 01, 02, 03으로 바꾸어줌
// 월, 시간, 분 을 바꿀때 사용
function zeroPlus(time){
	return time<10?"0"+time:time;
}
