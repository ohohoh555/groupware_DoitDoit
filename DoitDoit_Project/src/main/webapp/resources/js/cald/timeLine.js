//fullcalendar
document.addEventListener('DOMContentLoaded', selectAjax());
var aa;
function selectAjax() {
	$.ajax({
		type: "get",
		url: "/DoitDoit_Project/cald/calendarAjax.do",
		dataType: "json",
		success: function(data) {
			aa = data[0].events;
			console.log(data)
			console.log(data[0].events)
			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				slotMinTime: "09:00:00",
				slotMaxTime: "18:00:00",
				initialView: 'resourceTimelineDay', // 처음 로드될때 보이는 캘린더 화면(default : 달)
				locale: 'ko', // 한국어 설정
				selectable: true, // 달력 드래그 가능
				dayMaxEventRows: true, // 하루에 일정이 여러개여서 화면을 넘어가는 경우 + more 로 표시하게 됨
				scrollTime: '09:00',
				aspectRatio: 1.5,
				headerToolbar: { //헤더에 표시될 툴 바
					start: 'prev today next', // 1달 전, 1달 후 , 오늘 버튼을 캘린더 상단 왼쪽에 생성
					center: 'title', // 캘린더의 상단 중앙에 title을 찍음
					end:'' // 1달 기준으로 보기, 일주일 기준으로 보기, 4일 기준으로 보기
				},
				//			        editable: true, //이벤트들을 드래그 가능하게 만들어 줌
				// 상단의 년 월 을 어떻게 찍을지 만들어줌 ex) 2022년 5월
				titleFormat: function(date) {
					return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월' + date.date.day + '일';
				},
				// 캘린더에 띄울 화면을 직접 생성
				views: {
					resourceTimelineDay:
					{
						buttonText: ':15 slots',
						slotDuration: '00:15'
					},
					timeGridFourDay: {
						type: 'timeGrid', //타임 그리드 형식으로
						duration: { days: 4 }, // 4일씩
						buttonText: '4Day', // 버튼에 나타낼 텍스트
						allDayText: '오늘', // 버그인지 all-day 라는 부분은 국제화 코드로 바뀌지 않아 직접 지정
					}
				},

				// 캘린더에 입력될 값
				events: data[0].events,
				resources: data[1].resources,

				// 이벤트 클릭 시 동작
				eventClick: function(info) {
					$("#datetimepicker1_2").val("");
					$("#datetimepicker2_2").val("");
					var isc = $("#modifyButton").is(":checked");
					console.log(isc);
					console.log(info.event)
					var modal = $("#schedule-edit");
					var detailModal = $("#schedule-detail");
					// 이벤트 클릭시 모달 오픈
					if (isc) {
						console.log(data[0].events)
						modal.modal({ backdrop: 'static', keyboard: false });
						$("#id").val(info.event.id);
						$("#modalTitle").val(info.event.title);
						$("#modalWriter").val(info.event.extendedProps.writer);
						$("#datetimepicker1_2").val(change(info.event._instance.range.start.toISOString()))
						$("#datetimepicker2_2").val(change(info.event._instance.range.end.toISOString()))
					} else {
						console.log(info.event._instance.range.start.toLocaleString())
						detailModal.modal({ backdrop: 'static', keyboard: false });
						console.log(info.event._def.resourceIds[0])
						$("#id2").val(info.event.id);
						$("#modalTitle2").val(info.event.title);
						$("#modalWriter2").val(info.event.extendedProps.writer);
						$("#start").val(change(info.event._instance.range.start.toISOString()))
						$("#end").val(change(info.event._instance.range.end.toISOString()))
						$("#resId").val(info.event._def.resourceIds[0])
					}

				},
				eventDidMount: function(info) {
					tippy(info.el, {
						content: info.event.title
					});
				},
			});
			// 달력 초기화시 필수임
			calendar.render();
		},
		error: function(error) {
			alert("통신 실패 : " + error.status)
		}
	});
}

// UTC 날짜 형식을 바꾸어줌
function change(date){
	console.log(date)
	var sp = date.split("T");
	console.log(sp);
	var sp2 = sp[1].split(":");
	var sum = sp[0]+" "+sp2[0]+":"+sp2[1]
	console.log(sum);
	
	return sum;
}

// 예약 등록
function insertAjax() {
	var cnt = 0;
	console.log(aa);
	var st = dateFormat($("#datetimepicker1_2").datetimepicker('getValue'))
	var start = (new Date($("#datetimepicker1_2").val())).getTime();
	var end = (new Date($("#datetimepicker2_2").val())).getTime();
	var cnt = 0;
	
	// 같은 회의실 내에서 예약이 겹치는지 유효성 검사
	$.each(aa, function(index, val) {
		console.log(val)
		var rgxStart = new Date(val.start).getTime();
		var rgxEnd = new Date(val.end).getTime();
		
		// 인당 하나의 예약만 가능
		if(val.writer == $("#name").val()){
			cnt++;
			alert("한사람당 하나의 예약만 가능합니다 .")
			return;
		}
		
		// 자기 예약을 제외한 예약과 같은 회의실에서 하는 예약인지 검사
		if(val.writer != $("#name").val() && val.resourceId == $("#resId").val()){
			if(start>=rgxStart && start<=rgxEnd){
				cnt++;
				alert("예약일이 중복되었습니다.")
				return false;
			}
			if(end>=rgxStart && end<=rgxEnd){
				cnt++;
				alert("예약일이 중복되었습니다.")
				return false
			}
		}
	})

	if (cnt != 0) {
		return false;
	}
	
	// 작성자 필수값 입력 검사
	console.log($("#datetimepicker1").val())
	if ($("#writer").val() == "") {
		alert("작성자를 입력해주세요")
		$("#writer").focus();
		return false;
	}
	
	// 예약 명 필수 값
	if ($("#title").val() == "") {
		alert("예약명을 입력해주세요")
		$("#title").focus();
		return false;
	}
	
	// 예약 시간 필수 값 검사
	if ($("#datetimepicker1").val() == "" || $("#datetimepicker2").val() == "") {
		alert("시간을 입력해 주세요")
		return false;
	}

	// 시작일이 종료일보다 클 시 아작스 강제 종료
	if (dateVal('datetimepicker1', 'datetimepicker2') == false) {
		return false;
	}
	
	// 예약 시작일이 현재 날짜보다 이전인지 체크
	if (dateFormat(new Date) > st) {
		alert("현재 날짜 이전의 예약입니다")
		return false;
	}

	// serialize() 값 : 작성자(writer), 일정명(title), 내용(content), 일정 시작일(start), 일정 종료일(end)
	var str = $("#frm").serialize()
	var res = $("#res option:selected").val();
	console.log(res)
	console.log(str)
	console.log(start)
	console.log(end)


	// 인서트 성공 시 결과값을 boolean으로 반환
	$.ajax({
		url: "./calendarInsert.do",
		data: str,
		type: "post",
		dataType: "json",
		success: function(msg) {
			console.log(msg);
			if (msg != true) {
				alert("일정 등록에 실패하였습니다.")
				return false;
			} else {
				$("#title").val("");
				$("#datetimepicker1").val("");
				$("#datetimepicker2").val("");
				$("#res option:eq(0)").attr("selected", "selected")
				alert("예약이 등록되었습니다.")
				selectAjax();
				console.log("ㅎㅇㅎㅇ")
			}
		},
		error: function() {
			alert("예약 등록 실패");
		}
	})
}

// 예약 수정
function updateContent() {
	var frm = $("#modalFrm").serialize();
	var content = $("#modalContent");
	var id = $("#id").val();
	
	console.log(id)
	
	var st = dateFormat($("#datetimepicker1_2").datetimepicker('getValue'));
	var en = dateFormat($("#datetimepicker2_2").datetimepicker('getValue'));
	
	var start = (new Date($("#datetimepicker1_2").val())).getTime();
	var end = (new Date($("#datetimepicker2_2").val())).getTime();
	var cnt = 0;
	
	$.each(aa, function(index, val) {
		console.log(val)
		var rgxStart = new Date(val.start).getTime();
		var rgxEnd = new Date(val.end).getTime();
		
		// 자기 예약을 제외한 예약과 같은 회의실에서 하는 예약인지 검사
		if(val.writer != $("#modalWriter2").val() && val.resourceId == $("#resId").val()){
			if(start>=rgxStart && start<=rgxEnd){
				cnt++;
				alert("예약일이 중복되었습니다.")
				return false;
			}
			if(end>=rgxStart && end<=rgxEnd){
				cnt++;
				alert("예약일이 중복되었습니다.")
				return false
			}
		}
	})

	if (cnt != 0) {
		return false;
	}
//	console.log(start)
//	console.log(end)

	if ($("#datetimepicker1_2").val() == "" || $("#datetimepicker2_2").val() == "") {
		alert("시간을 입력해 주세요")
		return false;
	}

	if (dateVal('datetimepicker1_2', 'datetimepicker2_2') == false) {
		return false;
	}

	if (dateFormat(new Date) > dateFormat($("#datetimepicker1_2").datetimepicker('getValue'))) {
		alert("현재 날짜 이전의 예약입니다")
		return false;
	}

	console.log(frm);
	$.ajax({
		url: "./uadateAjax.do",
		type: "post",
		data:
		{
			"resv_id": id,
			"resv_start": st,
			"resv_end": en
		},
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data != true) {
				content.val("");
				$("#datetimepicker1_2").val("");
				$("#datetimepicker2_2").val("");
				console.log("실패");
				alert("예약 수정에 실패하였습니다. 본인의 예약인지 확인하여 주세요.")
				$("#schedule-edit").modal("hide");
				return false;
			} else {
				$("#schedule-edit").modal("hide");
				console.log("성공")
				alert("예약이 수정되었습니다.")
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
		data: { "resv_id": id, "resv_end": end },
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data != true) {
				$("#schedule-detail").modal("hide");
				console.log("실패");
				alert("본인의 예약만 삭제 가능합니다.")
				return false;
			} else {
				$("#schedule-detail").modal("hide");
				console.log("성공")
				alert("예약이 삭제되었습니다.")
				selectAjax();
			}
		},
		error: function() {
			alert("에러")
		}
	});
}

function dateVal(dtp, dtp2) {
	// start 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
	let date1 = $("#" + dtp + "")
	let i = date1.datetimepicker('getValue');
	// end 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
	let date2 = $("#" + dtp2 + "")
	let i2 = date2.datetimepicker('getValue');
	console.log(i, i2)
	// test.valueOf() 밀리세컨트로 반환
	// 밀리세컨트로 반환한 i 와 i2 의 시간을 비교하여 시작일보다 종료일이 작은 경우를 방지
	if (i.valueOf() >= i2.valueOf()) {
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
function zeroPlus(time) {
	return time < 10 ? "0" + time : time;
}
