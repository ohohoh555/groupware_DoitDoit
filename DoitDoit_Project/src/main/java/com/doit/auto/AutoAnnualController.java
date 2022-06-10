package com.doit.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/*
	 * 매년 1월 1일 입사 1년차 이상 사원 연차(15개) 부여
	 * 연차 초기화
	 */
	@Scheduled(cron = "0 0/1 0 1 1 *", zone = "Asia/Seoul")//매년 1월 1일 0시 1분
	public void generateAnnYear() {
		logger.info("AutoAnnualController generateAnnYear 새해 연차");
		
		//입사 1년차 이상 사원 조회
		List<EmpVo> emps = service.selEmpYear();
		
		//입사 1년차 이상 사원 있으면 연차(15개) 부여
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
			//연차 초기화 후 부여
			service.updAnnualReset(map);
			int n = service.updAnnualYear(map);
			logger.info("AutoAnnualController generateAnnYear "+(n>0?"새해 연차 부여":""));
		}
	}
	
	/*
	 * 매월 1일 입사 1년미만 사원 만근시 연차(1개) 부여
	 * 근무일수 초기화
	 */
	@Scheduled(cron = "0 0/1 0 1 * *", zone = "Asia/Seoul")//매월 1일 0시 1분
	public void generateAnnMonth() throws Exception {
		logger.info("AutoAnnualController generateAnnMonth 만근 연차");
		
		int workingDays = WorkingDays.workingDays();//지난달 근무일 수
		
		//입사 1년차 안되며 만근인 사원 조회
		List<EmpVo> emps = service.selEmpMonth(workingDays);
		
		//입사 1년차 안된 사원 만근시 연차(1개) 부여
		List<String> ids = new ArrayList<String>();
		if (!emps.isEmpty()) {
			for (EmpVo empVo : emps) {
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
		//근무일수 초기화
		service.updAnnualMonthWork();
	}
	
	/*
	 * 매일 입사 1년차된 사원 연차(15개) 부여
	 * 연차 초기화
	 * 출퇴근 초기화
	 */
	@Scheduled(cron = "0 0/1 0 * * *", zone = "Asia/Seoul")//매일 0시 1분
	public void everyDay() {
		logger.info("AutoAnnualController everyDay 매일 입사 1년차된 사원 조회");
		//매일 입사 1년차된 사원 조회
		List<EmpVo> emps = service.selEmpEveryDay();
		
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
			//연차 초기화 후 부여
			service.updAnnualReset(map);
			int n = service.updAnnualYear(map);
			logger.info("AutoAnnualController generateAnnYear "+(n>0?"입사 1년차 연차 부여":""));
		}
		
		logger.info("AutoAnnualController everyDay 출퇴근 초기화");
		int n = service.updAnnualEveryDay();
		logger.info("AutoAnnualController everyDay "+(n>0?"출퇴근 시간 초기화":""));
	}
}
