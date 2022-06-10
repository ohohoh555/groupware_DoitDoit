<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통합검색</title>
<%@include file="../comm/setting.jsp" %>
</head>
<body>
	<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
					<div class="rContent-full">
					<h3>통합검색</h3>
					<button onclick="searchCount()">갯수</button>
						<div style="float: left;">	
								<select class="form-control" style="width: 100px;" name="searchOp" >
									<option value="uniTitle">문서명</option>
									<option value="uniContent">내용</option>
								</select>
						</div>
						<div style="float: left;"><input type="text" class="form-control" width="70%" id="searchWord"></div>	
						<div style="float: left;"><button class="btn btn-success" onclick="searchWord()">검색</button></div>	
					<div><h3>총 <span class="label label-default" id="boardCnt"></span> 건의 게시글이 있습니다</h3></div>
					
					<div>
						<table id="searchResult" class="striped">
							<thead>
								<tr>
									<th >제목</th>
									<th >미리보기</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					</div><!--rContent-full 끝 -->
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
            </div>
        </main>
    </div>
</body>
<script type="text/javascript">
$(document).ready( function () {
    $('#searchResult').DataTable({
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
		scrollY:"200px"
    });
} );


function searchCount(){
	console.log("searchCount 개수확인");
	
	var jsonData = { "query":{"match_all":{ }}};
	
	$.ajax({
		url:"http://localhost:9200/entrboard/_count",
		method:"POST",
		headers:{
			"Authorization" : "Basic " + btoa("GDread"+":"+"gd1234")
		},
		data:JSON.stringify(jsonData), // 전송데이터
		timeout: 5000, //타임아웃 설정
		dataType: "JSON", //응답받을 데이터타입
		contentType: "application/json; charset=UTF-8",
		success:function(json){
			console.log(json);
			var data = json.count;
			$("#boardCnt").text(data);
		}
	});

}


function searchWord(){
	var searchWord = $("#searchWord").val();
	console.log("searchWord 검색버튼 작동",searchWord );
	
	var op =  $("select[name='searchOp']").val();
	console.log(op);
	
	
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
	
	
	
	$.ajax({
		url:"http://localhost:9200/entrboard/_search",
		method:"POST",
		headers:{"Authorization" : "Basic " + btoa("GDread"+":"+"gd1234")},
		data:JSON.stringify(jsonData), // 전송데이터
		timeout: 5000, //타임아웃 설정
		dataType: "JSON", //응답받을 데이터타입
		contentType: "application/json; charset=utf-8",
		success:function(json){
			console.log(json);
			var data = json.hits.hits;
			var html = "";
			for(var i=0; i<data.length; i++){
				html += "<tr>";
				html += "<td>"+data[i]._source.eboard_title+"</td>";
				html += "<td>등록일&nbsp;:&nbsp;"+data[i]._source.eboard_regdate.substring(0,10);
				html += "&nbsp;&nbsp;작성자&nbsp;:&nbsp;"+data[i]._source.emp_name
				html += "<br>내용&nbsp;:&nbsp;"+data[i]._source.eboard_content.replace(/(<([^>]+)>)/ig,"").substring(0,30)+"...</td>";
				html += "</tr>";
			}
			$("#searchResult  tbody").html(html);

		}
		
	});
	
}

</script>
</html>