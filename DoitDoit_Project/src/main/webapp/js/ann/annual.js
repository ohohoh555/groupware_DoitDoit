$(document).ready( function () {
    $('#annualTable').DataTable({
		//https://datatables.net/reference/option/language
		// DataTable은 기본적으로 영어로 표시되기 때문에 별도로 language를 통해서 변경해줘야 함
    	"language": { 
            "emptyTable": "선택된 부서의 사원이 없습니다.",
            "infoEmpty": "데이터 없음",
            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
            "search": "검색: ",
            "zeroRecords": "일치하는 데이터가 없습니다.",
            "loadingRecords": "로딩중...",
            "processing":     "잠시만 기다려 주세요...",
            "paginate": {
                "next": "다음",
                "previous": "이전"
            }
        },
        lengthChange: false, // 표시 건수기능 숨기기
        searching: true, // 검색 기능 숨기기
        ordering: true, // 정렬 기능 숨기기
        info: false, // 정보 표시 숨기기
        paging:true, // 페이징 기능 숨기기
        pageLength:5,
        order: [[1, "asc"]], //초기표기시 정렬, 만약 정렬을 안하겠다 => order: []
   		columnDefs: [{ "targets": "1", "width": "50px" }], //열의 넓이 조절 
        pagingType: "simple_numbers" // 페이징 타입 설정 : simple, simple_numbers, full_numbers 등
    
    });
    
//     $("#annualTable_paginate").css('align-content', 'center');
    
} );

function checkAll(bool){
	var chks = $("input[name='chk']");
	check();
	for(var i=0; i<chks.length; i++){
		chks[i].checked = bool;
	}
}


function checkEmp(){
	var chks = $("input[name='chk']:checked");
	var arrChk = [];
	chks.each(function(){
		arrChk.push($(this).val());
	});
	console.log(arrChk);	
	return arrChk;
}

function annAdd(){
	if(checkEmp()== ''){
		alert("사원을 선택해 주세요");
	}else{
		$("#annual").modal();
	}
}