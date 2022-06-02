//fullcalendar
document.addEventListener('DOMContentLoaded', selectAjax());

function selectAjax() {
	console.log("안녕 친구")
	$.ajax({
		type: "get",
		url: "./calendarAjax.do",
		dataType: "json",
		success: function(data) {
			console.log(data)
			var calendarEl = document.getElementById('calendar');
			// 달력 초기화시 필수임
//			calendarList.render();
			
			var calendar = new FullCalendar.Calendar(calendarEl, {
				googleCalendarApiKey: 'AIzaSyDXoZq9SLsEWRNRIpAZO25rN82M23hQZwI', // 구글캘린더 키
				initialView: 'dayGridMonth', // 처음 로드될때 보이는 캘린더 화면(default : 달)
				locale: 'ko', // 한국어 설정
				selectable: true, // 달력 드래그 가능
				dayMaxEventRows: true, // 하루에 일정이 여러개여서 화면을 넘어가는 경우 + more 로 표시하게 됨
				headerToolbar: { //헤더에 표시될 툴 바
					start: 'prev today next', // 1달 전, 1달 후 , 오늘 버튼을 캘린더 상단 왼쪽에 생성
					center: 'title', // 캘린더의 상단 중앙에 title을 찍음
					end: ''
				},
				editable: true, //이벤트들을 드래그 가능하게 만들어 줌
				// 상단의 년 월 을 어떻게 찍을지 만들어줌 ex) 2022년 5월
				titleFormat: function(date) {
					return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
				},

				// 캘린더에 입력될 값
				// JSONArray 형식임 ex) [{},{}]
				events: data,
				// 이벤트 클릭 시 동작
				eventClick: function(info) {
					var modal = $("#schedule-edit");
					// 이벤트 클릭시 모달 오픈
					modal.modal();
					console.log(info.event)
					$("#id").val(info.event.id);
					$("#modalTitle").val(info.event.title);
					$("#modalContent").attr("placeholder", info.event.extendedProps.description);
				},
				
				//여러 이벤트를 가져옴
				eventSources: [
					{
						// 구글 캘린더에서 가져올 이벤트의 ID
						googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com"
						// 클릭 이벤트를 제거하기 위해 넣은 클래스
						, className: "koHolidays"
						, editable: false
						//이벤트의 색
						, color: "#ff0000"
						//이벤트의 텍스트 컬러
						, textColor: "#FFFFFF"
						, overlap: false
					}
				],

				// 일정에 호버기능을 넣어줌
				// 일정의 제목을 띄움
				// 별개의 cdn 추가 필요
				// <script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.js"></script>
				// <script src="https://unpkg.com/tippy.js@6"></script>
				eventDidMount: function(info) {
					tippy(info.el, {
						content: info.event.title
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
			
			calendar.render();
		},
		error: function(error) {
			alert("통신 실패 : " + error.status)
		}
	});
}
// form을 통한 일정 등록
// 입력 값 : 작성자(writer), 일정명(title), 내용(content), 그룹(group), 일정 시작일(start), 일정 종료일(end)
// 돌아오는 반환값(data)값 : boolean
// true : 성공 / false : 실패
//function insertAjax() {
//	console.log($("#datetimepicker1").val())
//	
//	// 작성자칸이 비었을 경우
//	if ($("#writer").val() == "") {
//		alert("작성자를 입력해주세요")
//		$("#writer").focus();
//		return false;
//	}
//	
//	// 일정명 칸이 비었을 경우
//	if ($("#title").val() == "") {
//		alert("일정명을 입력해주세요")
//		$("#title").focus();
//		return false;
//	}
//	
//	// 내용칸이 비었을 경우
//	if ($("#content").val() == "") {
//		alert("내용을 입력해주세요")
//		$("#content").focus();
//		return false;
//	}
//	
//	// 시작일, 종료일이 비엇을경우
//	if ($("#datetimepicker1").val() == "" || $("#datetimepicker2").val() == "") {
//		alert("시간을 입력해 주세요")
//		return false;
//	}
//
//	// 시작일이 종료일보다 클 시 아작스 강제 종료
//	// dateVal() 시작일 값과 종료일 값을 밀리세컨드로 바꿔 크기를 비교해주는 function
//	if (dateVal('datetimepicker1', 'datetimepicker2') == false) {
//		return false;
//	}
//
//	// serialize() 값 : 작성자(writer), 일정명(title), 내용(content), 그룹(group), 일정 시작일(start), 일정 종료일(end)
//	var str = $("#frm").serialize()
//	console.log(str)
//
//	// 인서트 성공 시 결과값을 boolean으로 반환
//	$.ajax({
//		url: "./calendarInsert.do",
//		data: str,
//		type: "post",
//		dataType: "json",
//		success: function(msg) {
//			console.log(msg);
//			if (msg != true) {
//				alert("일정 등록에 실패하였습니다.")
//				return false;
//			} else {
//				selectAjax();
//				console.log("ㅎㅇㅎㅇ")
//			}
//		},
//		error: function() {
//			alert("안녕");
//		}
//	})
//}
//
//// 드래그를 통한 시간 업데이트 ajax
//// 파라미터 값 : 시작일(start), 종료일(end), 아이디(id)
//// 돌아오는 반환값(data)값 : boolean
//// true : 성공 / false : 실패
//function updateDragAjax(start, end, id) {
//	console.log(start);
//	console.log(end);
//	console.log(id);
//	$.ajax({
//		url: "./uadateDragAjax.do",
//		type: "post",
//		data: { "start": start, "end": end, "id": id },
//		dataType: "json",
//		success: function(data) {
//			console.log(data);
//			if (data != true) {
//				alert("잘못된 드래그 수정입니다.")
//				return false;
//			} else {
//				console.log("성공")
//			}
//		},
//		error: function() {
//			alert("에러")
//		}
//	});
//}
//
//function updateContent() {
//	var frm = $("#modalFrm").serialize();
//	var content = $("#modalContent");
//	var start = $("#datetimepicker1_2");
//	var end = $("#datetimepicker2_2");
//
//	if (content.val() == "") {
//		alert("내용을 입력해주세요")
//		content.focus();
//		return false;
//	}
//
//	if ($("#datetimepicker1_2").val() == "" || $("#datetimepicker2_2").val() == "") {
//		alert("시간을 입력해 주세요")
//		return false;
//	}
//
//	if (dateVal('datetimepicker1_2', 'datetimepicker2_2') == false) {
//		return false;
//	}
//	console.log(frm);
//	$.ajax({
//		url: "./uadateAjax.do",
//		type: "post",
//		data: frm,
//		dataType: "json",
//		success: function(data) {
//			console.log(data);
//			if (data != true) {
//				content.val("");
//				start.val("");
//				end.val("");
//				console.log("실패");
//				$("#schedule-edit").modal("hide");
//				return false;
//			} else {
//				$("#schedule-edit").modal("hide");
//				console.log("성공")
//				selectAjax();
//			}
//		},
//		error: function() {
//			alert("에러")
//		}
//	});
//}
//
//function deleteContent() {
//	var id = $("#id").val();
//	$.ajax({
//		url: "./deleteAjax.do",
//		type: "post",
//		data: { "id": id },
//		dataType: "json",
//		success: function(data) {
//			console.log(data);
//			if (data != true) {
//				$("#schedule-edit").modal("hide");
//				console.log("실패");
//				return false;
//			} else {
//				$("#schedule-edit").modal("hide");
//				console.log("성공")
//				selectAjax();
//			}
//		},
//		error: function() {
//			alert("에러")
//		}
//	});
//}
//
//// DataTimePicaker
//function dateVal(dtp, dtp2) {
//	// start 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
//	let date1 = $("#" + dtp + "")
//	let i = date1.datetimepicker('getValue');
//	// end 의 date 값 ex) Wed May 11 2022 11:10:17 GMT+0900 (한국 표준시)
//	let date2 = $("#" + dtp2 + "")
//	let i2 = date2.datetimepicker('getValue');
//	console.log(i, i2)
//	// test.valueOf() 밀리세컨트로 반환
//	// 밀리세컨트로 반환한 i 와 i2 의 시간을 비교하여 시작일보다 종료일이 작은 경우를 방지
//	if (i.valueOf() >= i2.valueOf()) {
//		date1.val("")
//		date2.val("")
//		alert("일정 종료일이 시작일보다 과거또는 같은 시간입니다.")
//		return false;
//	}
//}
//
//// date( Thu May 12 2022 09:30:00 GMT+0900 (한국 표준시) ) 를 202205120903 형식으로 바꾸어줌
//function dateFormat(date) {
//	let year = date.getFullYear();
//	let month = zeroPlus(date.getMonth() + 1);
//	let day = zeroPlus(date.getDate());
//	let hour = zeroPlus(date.getHours());
//	let min = zeroPlus(date.getMinutes());
//	let startVal = year + "" + month + "" + day + "" + hour + "" + min
//	return startVal;
//}
//
//// 만약 1~9 월일 경우 뒤에 0을 붙여주어 01, 02, 03으로 바꾸어줌
//// 월, 일, 시간, 분 을 바꿀때 사용
//// ex) 1월 => 2월
//function zeroPlus(time) {
//	console.log("###################", time)
//	return time < 10 ? "0" + time : time;
//}
//
