package com.doit.gw.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WorkingDays {

	public static int workingDays() throws Exception {
		
		String key = "fET3a8HPdgurj21NIvYv%2BpPB3hJ5cgyRtZj4oV%2FU36V7d80cPhkpJ4O5SUR4FHhdenvXtgrCH9Upl6VO8YfFBQ%3D%3D";
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		
		String year = dateFormat.format(date).substring(0,4);//올해
		int iMonth = Integer.parseInt(dateFormat.format(date).substring(4,6))-1;//지난달(int)
		String sMonth = iMonth<10?"0"+iMonth:String.valueOf(iMonth);//지난달(String)
		
		calendar.set(Integer.parseInt(year), iMonth-1, 1);
		int firstDay = calendar.getActualMinimum(calendar.DAY_OF_MONTH);//지난달 첫째날
		int lastDay = calendar.getActualMaximum(calendar.DAY_OF_MONTH);//지난달 마지막날
		
		List<String> holidays = new ArrayList<String>();
		for (int i = firstDay; i <= lastDay; i++) {//주말 날짜(년월일) 구하기
			String weekDay = i<10?"0"+i:String.valueOf(i);
			calendar.set(Integer.parseInt(year), iMonth-1, i);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//요일 1,7 = 주말
			if(dayOfWeek == 1 || dayOfWeek == 7) {
				holidays.add(year+sMonth+weekDay);
			}
		}
		
		//근로자의 날
		if(sMonth.equals("05")) {
			holidays.add(year+"0501");
		}
		
		urlBuilder.append("?"+URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
		urlBuilder.append("&"+URLEncoder.encode("pageNo", "UTF-8")+"="+URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("numOfRows", "UTF-8")+"="+URLEncoder.encode("30", "UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("solYear", "UTF-8")+"="+URLEncoder.encode(year, "UTF-8"));
		urlBuilder.append("&"+URLEncoder.encode("solMonth", "UTF-8")+"="+URLEncoder.encode(sMonth, "UTF-8"));		
		
		String URL = urlBuilder.toString();
		Document getHoliDayPage = Jsoup.connect(URL).get();
		String result = getHoliDayPage.html();
		JSONObject jsonObject = XML.toJSONObject(result);
		Object oTotalCount = jsonObject.getJSONObject("response").getJSONObject("body").get("totalCount");
		int iTotalCount = (int) oTotalCount;
		
		if(iTotalCount != 0) {//공휴일 날짜 구해옴
			JSONArray holiday = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
			for (int i = 0; i < holiday.length(); i++) {
				JSONObject holidayObj = holiday.getJSONObject(i);
				String locdate = holidayObj.optString("locdate");
				holidays.add(locdate);
			}
		}
		
		HashSet<String> distinct = new HashSet<String>(holidays);//공휴일 주말 날짜 중복제거
		List<String> dholidays = new ArrayList<String>(distinct);// 중복 제거된 휴일
		
		int holiDays = dholidays.size();//공휴일 수
		int workingDays = lastDay-holiDays;//근무일 수
		return workingDays;
	}
	
}
