<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>==문자발송==</title>
</head>
<%@include file="../comm/setting.jsp" %>
<body>
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
	<input type="text" id="inputPhoneNumber" placeholder='수신번호를 입력하세요' onKeyup="inputPhoneNumber(this);" maxlength="13"/>
	<input type="button" id="sendPhoneNumber" value='전송'/><br>
	<input type="text" id="inputCertifiedNumber" placeholder='인증번호를 입력하세요'>
	<input type="button" id="checkBtn" value="확인">
	
	<script>
        $('#sendPhoneNumber').click(function(){
            var phoneNumber = $('#inputPhoneNumber').val();
            var contextPath = $("#contextPath").val();
            alert('인증번호 발송 완료!');

        	var docum = document.getElementById("checkBtn").disabled;
            $.ajax({
                type: "POST",
                url: contextPath + "/comm/sendSMS.do",
                data: {"phoneNumber" : phoneNumber},
                success: function(res){
                    $('#checkBtn').click(function(){
                    	if($('#inputCertifiedNumber').val()=='') {
                    		alert('값을 입력하세요.');
                    	}else if($.trim(res) == $('#inputCertifiedNumber').val()){
                    		alert('인증성공!'+'휴대폰 인증이 정상적으로 완료되었습니다 비밀번호/휴대폰 번호 설정 페이지로 이동합니다');
							location.href = contextPath + "/comm/updPassPhoneGo.do";
                        }else if($.trim(res) != $('#inputCertifiedNumber').val()){
                    		alert('올바른 인증번호를 입력해주세요!!');
                        }
                    })
                    }
        	});
        });
        
        function inputPhoneNumber(obj) {
        	
    	    var number = obj.value.replace(/[^0-9]/g, "");
    	    var phone = "";
    	
    	    if(number.length < 4) {
    	        return number;
    	    } else if(number.length < 7) {
    	        phone += number.substr(0, 3);
    	        phone += "-";
    	        phone += number.substr(3);
    	    } else if(number.length < 11) {
    	        phone += number.substr(0, 3);
    	        phone += "-";
    	        phone += number.substr(3, 3);
    	        phone += "-";
    	        phone += number.substr(6);
    	    } else {
    	        phone += number.substr(0, 3);
    	        phone += "-";
    	        phone += number.substr(3, 4);
    	        phone += "-";
    	        phone += number.substr(7);
    	    }
    	    obj.value = phone;
    	}
	</script>
	
</body>
</html>