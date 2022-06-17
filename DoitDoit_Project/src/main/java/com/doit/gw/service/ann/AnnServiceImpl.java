package com.doit.gw.service.ann;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.ann.IAnnMapper;
import com.doit.gw.vo.ann.AnnAddVo;
import com.doit.gw.vo.ann.AnnUseVo;
import com.doit.gw.vo.ann.AnnualVo;
import com.doit.gw.vo.appro.ApproVo;
import com.doit.gw.vo.emp.EmpVo;

@Service
public class AnnServiceImpl implements IAnnService {

	@Autowired
	private IAnnMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int insAnnual() {
		logger.info("AnnServiceImpl insAnnual 연차 등록");
		return mapper.insAnnual();
	}

	@Override
	public List<EmpVo> selEmpYear() {
		logger.info("AnnServiceImpl selEmpYear 입사 1년차 이상 사원 조회 : {}");
		return mapper.selEmpYear();
	}
	
	@Override
	public int updAnnualReset(Map<String, String[]> emp_ids) {
		logger.info("AnnServiceImpl updAnnualReset 입사 1년차 이상 사원 연차 초기화 : {}", emp_ids);
		return mapper.updAnnualReset(emp_ids);
	}

	@Override
	public int insAnnAddYear(String emp_id) {
		logger.info("AnnServiceImpl insAnnAddYear 입사 1년차 이상 사원 연차(15개) 부여(insert) : {}", emp_id);
		return mapper.insAnnAddYear(emp_id);
	}

	@Override
	public int updAnnualYear(Map<String, String[]> map) {
		logger.info("AnnServiceImpl updAnnualYear 입사 1년차 이상 사원 연차(15개) 부여(update) : {}", map);
		return mapper.updAnnualYear(map);
	}

	@Override
	public List<EmpVo> selEmpMonth(int days) {
		logger.info("AnnServiceImpl selEmpMonth 입사 1년차 안되며 만근인 사원 조회 : {}", days);
		return mapper.selEmpMonth(days);
	}

	@Override
	public int insAnnAddMonth(String emp_id) {
		logger.info("AnnServiceImpl insAnnAddMonth 입사 1년차 안된 사원 만근시 연차(1개) 부여(insert) : {}", emp_id);
		return mapper.insAnnAddMonth(emp_id);
	}

	@Override
	public int updAnnualMonth(Map<String, String[]> emp_ids) {
		logger.info("AnnServiceImpl updAnnualMonth 입사 1년차 안된 사원 만근시 연차(1개) 부여(update) : {}", emp_ids);
		return mapper.updAnnualMonth(emp_ids);
	}
	
	@Override
	public int updAnnualMonthWork() {
		logger.info("AnnServiceImpl updAnnualMonthWork 근무일수 초기화");
		return mapper.updAnnualMonthWork();
	}
	
	@Override
	public List<EmpVo> selEmpEveryDay() {
		logger.info("AnnServiceImpl updAnnualEveryDay 매일 입사 1년차된 사원 조회");
		return mapper.selEmpEveryDay();
	}
	
	@Override
	public int updAnnualEveryDay() {
		logger.info("AnnServiceImpl updAnnualEveryDay 출퇴근 시간 초기화");
		return mapper.updAnnualEveryDay();
	}

	@Override
	public List<AnnualVo> selAnnualAdmin(String dept_no) {
		logger.info("AnnServiceImpl selAnnualAdmin 관리자 연차 조회 : {}", dept_no);
		return mapper.selAnnualAdmin(dept_no);
	}

	@Override
	public int insAnnAdd(AnnAddVo annAddVo) {
		logger.info("AnnServiceImpl insAnnAdd 관리자 연차 부여(insert) : {}", annAddVo);
		return mapper.insAnnAdd(annAddVo);
	}

	@Override
	public int updAnnualAdd(Map<String, Object> map) {
		logger.info("AnnServiceImpl updAnnualAdd 관리자 연차 부여(update) : {}", map);
		return mapper.updAnnualAdd(map);
	}

	@Override
	public AnnualVo selAnnualEmp(String emp_id) {
		logger.info("AnnServiceImpl selAnnualEmp 사원 연차 조회(ANNUAL) : {}", emp_id);
		return mapper.selAnnualEmp(emp_id);
	}

	@Override
	public List<AnnAddVo> selAnnAddEmp(String emp_id) {
		logger.info("AnnServiceImpl selAnnualEmp 사원 연차 조회(ANN_ADD) : {}", emp_id);
		return mapper.selAnnAddEmp(emp_id);
	}

	@Override
	public List<AnnUseVo> selAnnUseEmp(String emp_id) {
		logger.info("AnnServiceImpl selAnnualEmp 사원 연차 조회(ANN_USE) : {}", emp_id);
		return mapper.selAnnUseEmp(emp_id);
	}

	@Override
	public AnnualVo selAnuualWorkIn(String emp_nfc) {
		logger.info("AnnServiceImpl updAnnualWorkIn 사원 출근 확인 : {}", emp_nfc);
		return mapper.selAnuualWorkIn(emp_nfc);
	}
	
	@Override
	public int updAnnualWorkIn(String emp_nfc) {
		logger.info("AnnServiceImpl updAnnualWorkIn 사원 출근 등록 : {}", emp_nfc);
		return mapper.updAnnualWorkIn(emp_nfc);
	}

	@Override
	public int updAnnualWorkOut(String emp_nfc) {
		logger.info("AnnServiceImpl updAnnualWorkOut 사원 퇴근 등록 : {}", emp_nfc);
		return mapper.updAnnualWorkOut(emp_nfc);
	}

	   @Override
	   public void anuualUse(int appro_line_no) {
	      logger.info("AnnServiceImpl anuualUse 연차 결재시 사용 : {}", appro_line_no);
	      ApproVo vo = mapper.searchAppro(appro_line_no);
	      
	      String content = vo.getAppro_content();
	      String emp_id = String.valueOf(vo.getEmp_id());
	      Document doc = Jsoup.parse(content);
	      Elements td = doc.getElementsByTag("td");
	      
	      String iscAnn = td.get(1).text();
	      logger.info("연차 vo : {}", vo);
	      if(iscAnn.equals("연차")) {
	    	  logger.info("연차 if vo : {}", vo);
	         String use_period = td.get(3).text();
	         String use_days = td.get(7).text();
	         String use_date = use_period.substring(0, 10);
	         String use_content = "";
	         String use_work_days = "";      
	         if(use_days.equals("0.5")) {
	        	 logger.info("연차 if의 if vo : {}", vo);
	            use_content = "반차";
	            use_work_days = "1";
	         }else {
	        	 logger.info("연차 if의 if의 else vo : {}", vo);
	            use_content = "연차";
	            use_work_days = use_days;
	         }
	         Map<String, Object> map = new HashMap<String, Object>();
	         map.put("use_content", use_content);
	         map.put("use_days", use_days);
	         map.put("use_period", use_period);
	         map.put("use_date", use_date);
	         map.put("use_work_days", use_work_days);
	         map.put("emp_id", emp_id);
	         
	         int n = mapper.insAnnUse(map);
	         int m = mapper.updAnnualUse(map);
	         logger.info((n+m==2?"연차 결재 승인":""));      
	      }
	   }
}
