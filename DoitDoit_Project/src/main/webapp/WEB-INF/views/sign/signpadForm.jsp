<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>    
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>::: 사인패드 :::</title>
	<script src="./js/sign/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="./js/sign/signature_pad.min.js" type="text/javascript"></script>
	
	<link rel="stylesheet" href="./css/sign/sign.css">
</head>
<%@include file="../comm/setting.jsp" %>

<body>
<div id="container">
        <%@include file="../comm/nav.jsp" %>
        <main>
            <%@include file="../comm/header.jsp" %>
            <div id="content">
            <sec:authorize access="hasRole('ROLE_USER')">
                <div id="rContent">
                    <div id="resent" class="rContent-full">
                <div >
                <img onclick="javascript:history.back(-1);" alt="돌아가기" style="cursor: pointer; margin: 10px;" src="./img/sign/back.png">
                
                </div>
                  <div id="signature-pad" class="m-signature-pad">
		<div class="m-signature-pad--body">
			<canvas></canvas>
		</div>
		<div class="m-signature-pad--footer">
			<div class="description">사인해주세요~</div>
			<button type="button" class="button clear" data-action="clear">지우기</button>
			<button type="button" class="button save" data-action="save">저장</button>
		</div>
	</div>
     
                    </div>
                </div>
            <%@include file="../comm/aside.jsp" %>    
            </sec:authorize>
        <%--    <sec:authorize access="isAuthenticated()">
            	아이디 : <sec:authentication property="principal"/> ${principal} <br>
            	직급 : <sec:authentication property="Details" var="info"/>${info.rank_no}<br>
            	부서 : <sec:authentication property="Details" var="info"/>${info.dept_no}<br>
            	이름 : <sec:authentication property="Details" var="info"/>${info.emp_name}<br>
            	권한 : <sec:authentication property="Authorities"/> ${Authorities} <br>
            </sec:authorize>	 --%>
            </div>
        </main>
    </div>
	
    <script>
		var canvas = $("#signature-pad canvas")[0];
		var sign = new SignaturePad(canvas, {
			minWidth: 5,
			maxWidth: 10,
			penColor: "rgb(66, 133, 244)"
		});
		
		$("[data-action]").on("click", function(){
			if ( $(this).data("action")=="clear" ){
				sign.clear();
			}else if ( $(this).data("action")=="save" ){
				if (sign.isEmpty()) {
					alert("사인을 해주세요!!");
				} else {//사인을 했다면 이미지를 저장 할 수 있게 해줌
					//서명(캔버스)이미지를 base64 문자열 형태로 변환하기
					var dataURL = canvas.toDataURL();
				
					var blob = dataURLToBlob(dataURL);
					var url = window.URL.createObjectURL(blob); //Blob 객체를 생성
					
					//다운로드를 위한 a태그 생성하기
					var a = document.createElement("a");
					a.style = "display: none";
					a.href = url;
					
					//파일명생성을 위한 난수(data 객체)생성
					const d = new Date();
					filename = "signature"+d.getMilliseconds()+".png";
					
					a.download = filename;
					
					document.body.appendChild(a);
					a.click();
					
					//생성된 Blob 객체를 지워줌(지우지 않으면 js엔진에 변수가 계속 남아있어서 메모리 누수가 됨)
					window.URL.revokeObjectURL(url);
				
					alert("서명 생성 성공!!");
					location.href='./test.do';
				}
			}
		});
		
		function resizeCanvas(){
			var canvas = $("#signature-pad canvas")[0];
	
			var ratio =  Math.max(window.devicePixelRatio || 1, 1);
			canvas.width = canvas.offsetWidth * ratio;
			canvas.height = canvas.offsetHeight * ratio;
			canvas.getContext("2d").scale(ratio, ratio);
		}
	    
	    $(window).on("resize", function(){
			resizeCanvas();
		});

		resizeCanvas();
	
		
		//문자열 이미지를 Byte객체로 변환(Blob객체 생성)
		function dataURLToBlob(dataURL) {
			//Data:Base64,ABSFE92AA...으로 되어 있는 값을 DB에 쉽게 넣기위해 정제하기
			var parts = dataURL.split(';base64,');
			var contentType = parts[0].split(":")[1];
			var raw = window.atob(parts[1]);
			var rawLength = raw.length;
			var uInt8Array = new Uint8Array(rawLength);
			//문자열 값을 char로 변환하여 0101 Byte로 변환해줌
			for (var i = 0; i < rawLength; ++i) {
				uInt8Array[i] = raw.charCodeAt(i);
			}
			//변환된 Byte를 new해서 카피해줌
			return new Blob([ uInt8Array ], {
				type : contentType
			});
		} 
	
    </script>
</body>
</html>