package com.doit.gw.service.sms;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class SmsService {
	
	public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
		String api_key = "NCSZADE5ZEC1DZR3"; // coolSMS 사이트에서 받은 인증키
        String api_secret = "HQMRDEF5F5WUBE15UHVGMYSOY4PVFBJS"; // coolSMS 자체에 발급된 비밀키
        Message coolsms = new Message(api_key, api_secret);

        // 4개의 입력값 필요
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "010-7378-0203");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨 -> 발신전화 번호는 coolSMS에 등록해줘야됨
        params.put("type", "SMS");// type 방식
        params.put("text", "휴대폰인증 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
      	params.put("app_version", "test app 1.2"); // application name and version
      	try {
        	JSONObject obj = (JSONObject)coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
	}
}


