document.addEventListener('DOMContentLoaded', selectAjax());
var contextPath = $("#contextPath").val();

console.log("컨텍스트 : " + contextPath);

function selectAjax(){
	$.ajax({
				type:"get",
				url:"/DoitDoit_Project/cald/calendarAjaxMain.do",
				dataType:"json",
				success:function(data){
					console.log(data)
//					console.log(data[0].events)
					var calendarEl = document.getElementById('calendar');
			        var calendar = new FullCalendar.Calendar(calendarEl, {
					initialView: 'listWeek',
			        locale: 'ko', // 한국어 설정
					dayMaxEventRows: true, // 하루에 일정이 여러개여서 화면을 넘어가는 경우 + more 로 표시하게 됨
					aspectRatio: 1.5,
			        headerToolbar: { //헤더에 표시될 툴 바
						start : '', // 1달 전, 1달 후 , 오늘 버튼을 캘린더 상단 왼쪽에 생성
						center : '', // 캘린더의 상단 중앙에 title을 찍음
						end : '' // 1달 기준으로 보기, 일주일 기준으로 보기, 4일 기준으로 보기
			        },
					// 캘린더에 입력될 값
			        events: data,
					eventClick: function(){
						location.href="/DoitDoit_Project/cald/moveCalendar.do";
					},
			     });
				// 달력 초기화시 필수임
			    calendar.render();
				$(".fc-header-toolbar").css("display","none");
				$(".fc-list-empty-cushion").text("이번 주 일정이 없습니다.");
				
				},
				error:function(error){
					alert("통신 실패 : "+error.status)
				}
			});
}