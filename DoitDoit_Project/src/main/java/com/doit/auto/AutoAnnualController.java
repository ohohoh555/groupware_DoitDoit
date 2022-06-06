package com.doit.auto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.doit.gw.service.ann.IAnnService;
import com.doit.gw.util.WorkingDays;
import com.doit.gw.vo.emp.EmpVo;

@Controller
@EnableScheduling
public class AutoAnnualController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAnnService service;
	
	/**
	 * 한국 시간으로 연차 기준일 계산
	 * @return baseDate = 연차 계산을 위해 기준이 되는 날짜
	 */
	public static String baseDate() {
		//새해(한국 시간 설정)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		String baseDate = sdf.format(date).substring(0,4)+"-01-01";		
		return baseDate;
	}	
	
	@Scheduled(cron = "0 0/1 0 1 1 *", zone = "Asia/Seoul")//매년 1월 1일 0시 1분
	public void generateAnnYear() {
		logger.info("AutoAnnualController generateAnnYear 새해 연차");
		//생성내역 초기화
		service.delAnnAddReset();
		//사용내역 초기화
		service.delAnnUseReset();
		//연차 초기화
		service.updAnnualReset();
		
		String baseDate = AutoAnnualController.baseDate();//연차 기준일
		
		//올해 이전 입사한 사원 조회
		List<EmpVo> emps = service.selEmpYear(baseDate);
		
		//올해 이전 입사한 사원이 있으면 연차(15개) 부여
		List<String> ids = new ArrayList<String>();
		if(!emps.isEmpty()) {
			for (EmpVo empVo : emps) {
				String emp_id = String.valueOf(empVo.getEmp_id());
				service.insAnnAddYear(emp_id);
				ids.add(emp_id);
			}
			String[] emp_ids = ids.toArray(new String[ids.size()]);
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("emp_ids", emp_ids);
			int n = service.updAnnualYear(map);
			logger.info("AutoAnnualController generateAnnYear "+(n>0?"새해 연차 부여":""));
		}
	}
	
	@Scheduled(cron = "0 0/1 0 1 * *", zone = "Asia/Seoul")//매월 1일 0시 1분
	public void generateAnnMonth() throws Exception {
		logger.info("AutoAnnualController generateAnnMonth 만근 연차");
		
		String baseDate = AutoAnnualController.baseDate();//연차 기준일
		int workingDays = WorkingDays.workingDays();//지난달 근무일 수
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", baseDate);
		map.put("days", workingDays);
		
		//올해 입사한 사원들 중 만근인 사원 조회
		List<EmpVo> emp = service.selEmpMonth(map);
		
		//올해 입사한 사원중 만근시 연차(1개) 부여
		List<String> ids = new ArrayList<String>();
		if (!emp.isEmpty()) {
			for (EmpVo empVo : emp) {
				String emp_id = String.valueOf(empVo.getEmp_id());
				service.insAnnAddMonth(emp_id);
				ids.add(emp_id);
			}
			String[] emp_ids = ids.toArray(new String[ids.size()]);
			Map<String, String[]> empMap = new HashMap<String, String[]>();
			empMap.put("emp_ids", emp_ids);
			int n = service.updAnnualMonth(empMap);
			logger.info("AutoAnnualController generateAnnNewMonth "+(n>0?"만근 연차 부여":""));
		}
	}
	
	@Scheduled(cron = "0 0/1 0 * * *", zone = "Asia/Seoul")//매일 0시 1분
	public void everyDayReset() {
		logger.info("AutoAnnualController everyDayReset 출퇴근 초기화");
		int n = service.updAnnualEveryDay();
		logger.info("AutoAnnualController everyDayReset "+(n>0?"출퇴근 시간 초기화":""));
	}
}
