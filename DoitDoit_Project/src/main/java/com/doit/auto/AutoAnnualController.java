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
import com.doit.gw.vo.emp.EmpVo;

@Controller
@EnableScheduling
public class AutoAnnualController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAnnService service;
	
	@Scheduled(cron = "0 0/1 0 1 1 *", zone = "Asia/Seoul")//매년 1월1일 0시 1분
	public void generateAnnNewYear() {
		logger.info("AutoAnnualController generateAnnNewYear 새해 연차");
		//생성내역 초기화
		service.delAnnAddReset();
		//사용내역 초기화
		service.delAnnUseReset();
		//연차 초기화
		service.updAnnualReset();
		
		//한국 시간
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		
		//새해 1월1일
		String newYearDay = sdf.format(date).substring(0,4)+"-01-01";
		
		//새해 1월1일 이전 입사한 사원 조회
		List<EmpVo> emps = service.selEmpYear(newYearDay);
		
		//새해 1월1일 입사한 사원이 있으면 연차(15개) 부여
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
			logger.info("AutoAnnualController generateAnnNewYear "+(n>0?"새해 연차 부여":""));
		}
	}
	
}
