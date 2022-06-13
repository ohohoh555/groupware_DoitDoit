const reg = /<[^>]*>?/g; //정규식

function searchAction(){
	var searchWord = $("#searchWord").val(); //검색어
	var op =  $("select[name='searchOp']").val(); //검색옵션
	var owner = $("#owner").val();
	console.log("검색버튼 작동",op, searchWord, owner);
	
	if(searchWord.length<2){
		alert("검색어는 2글자 이상 입력해주세요");
		return false;
	}
	
	searchEntr(searchWord, op);
	searchAppro(searchWord, op, owner);


}


function searchEntr(searchWord, op){
	
	var jsonData = "";
	if(op=='uniTitle'){
		jsonData = {
	 			  "query": { "match": { "eboard_title": searchWord } }
	 	};
	}else if(op=='uniContent'){
		jsonData = {
	 			  "query": { "match": { "eboard_content": searchWord } }
	 	};
	}
	
	$("#entrResult").DataTable().destroy();
	var html = "";
	
	$.ajax({
		url:"http://localhost:9200/entrboard/_search",
		method:"POST",
		headers:{"Authorization" : "Basic " + btoa("GDread"+":"+"gd1234")},
		data:JSON.stringify(jsonData), // 전송데이터
		timeout: 5000, //타임아웃 설정
		dataType: "JSON", //응답받을 데이터타입
		contentType: "application/json; charset=utf-8",
		success:function(json){
			var data = json.hits.hits;
			console.log(data);
			$("#entrCnt").text(json.hits.total.value); //검색된 갯수 표기

			$("#entrResult>tbody").empty();

			for(var i=0; i<data.length; i++){
				html += "<tr>";
				html += "<td><a href='./OneBoard.do?eboard_no="+data[i]._source.eboard_no+"'>"+data[i]._source.eboard_title+"</a></td>";
				html += "<td>등록일&nbsp;:&nbsp;"+data[i]._source.eboard_regdate.substring(0,10);
				html +="<br> 내용&nbsp;:&nbsp;"+data[i]._source.eboard_content.replace(reg, "").substring(0, 50) + "...";
				html +="</td>";
				html += "</tr>";
			}
			$("#entrResult>tbody").append(html);
		    $('#entrResult').DataTable({
		        lengthChange: false, // 표시 건수기능 숨기기
		        searching:false, // 검색 기능 숨기기
		        ordering: false, // 정렬 기능 숨기기
		        info: false, // 정보 표시 숨기기
		        paging:true,// 페이징 기능 숨기기
				"language": {
		            "emptyTable": "검색된 결과가 없어요.",
		            "loadingRecords": "로딩중...",
		            "processing":     "잠시만 기다려 주세요...",
		            "paginate": {
		                "next": "다음",
		                "previous": "이전"
		            }
		        },
		        displayLength: 5,
				scrollCollapse:true,
				scrollY:"200px",
				columnDefs: [
		            { width: 150, targets: 0 }
		        ]
		    });
			

		},
		error:function(){
			alert("게시판 통신오류");
		}
		
	});
	
}

function searchAppro(searchWord, op, owner){
	var jsonData1 = "";
	
	if(op=='uniTitle'){
			jsonData1 = {"query": { 
							"bool": { 
							 "must": [{ "match": { "emp_id": owner }},
								 	   {"wildcard":{"appro_title":"*"+searchWord+"*"}}]
								}
							}
						};
	}else if(op=='uniContent'){
			jsonData1 = {"query": { 
							"bool": { 
							 "must": [{ "match": { "emp_id": owner }},
								 	   {"wildcard":{"appro_content":"*"+searchWord+"*"}}]
								}
							}
						};
	}

	
	$("#approResult").DataTable().destroy();
	var html = "";
	
	$.ajax({
		url:"http://localhost:9200/approval/_search",
		method:"POST",
		headers:{"Authorization" : "Basic " + btoa("GD"+":"+"gd1234")},
		data:JSON.stringify(jsonData1), // 전송데이터
		timeout: 4000, //타임아웃 설정
		dataType: "JSON", //응답받을 데이터타입
		contentType: "application/json; charset=UTF-8",
		success:function(json){
			console.log(json);
			var data = json.hits.hits;
			$("#approCnt").text(json.hits.total.value); //검색된 갯수 표기
			
			
			$("#approResult>tbody").empty();
			for(var i=0; i<data.length; i++){
				html += "<tr>";
				html += "<td><a href='/DoitDoit_Project/appro/selDocDetail.do?emp_id="+data[i]._source.emp_id+"&appro_no="+data[i]._source.appro_no+"'>"+data[i]._source.appro_title+"</a></td>";
				html += "<td> 등록일&nbsp;:&nbsp;"+data[i]._source.appro_regdate.substring(0,10);
				html +="<br> 내용&nbsp;:&nbsp;"+data[i]._source.appro_content.replace(reg, "").substring(0, 50) + "...";
				html+="</td>";
				html += "</tr>";
			}
			$("#approResult>tbody").append(html);
		    $('#approResult').DataTable({
		        lengthChange: false, // 표시 건수기능 숨기기
		        searching:false, // 검색 기능 숨기기
		        ordering: false, // 정렬 기능 숨기기
		        info: false, // 정보 표시 숨기기
		        paging:true,// 페이징 기능 숨기기
				"language": {
		            "emptyTable": "검색된 결과가 없어요.",
		            "loadingRecords": "로딩중...",
		            "processing":     "잠시만 기다려 주세요...",
		            "paginate": {
		                "next": "다음",
		                "previous": "이전"
		            }
		        },
		        displayLength: 5,
				scrollCollapse:true,
				scrollY:"200px",
				columnDefs: [
		            { width: 150, targets: 0 }
		        ]
		    });

		},
		error:function(){
			alert("전자결재 통신오류");
		}
		
	});

}
