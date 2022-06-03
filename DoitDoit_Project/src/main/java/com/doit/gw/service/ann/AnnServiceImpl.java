package com.doit.gw.service.ann;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.ann.IAnnMapper;
import com.doit.gw.vo.ann.AnnualVo;
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
	public int delAnnAddReset() {
		logger.info("AnnServiceImpl delAnnAddReset 연차 생성내역 초기화");
		return mapper.delAnnAddReset();
	}

	@Override
	public int delAnnUseReset() {
		logger.info("AnnServiceImpl delAnnUseReset 연차 사용내역 초기화");
		return mapper.delAnnUseReset();
	}

	@Override
	public int updAnnualReset() {
		logger.info("AnnServiceImpl updAnnualReset 연차 초기화");
		return mapper.updAnnualReset();
	}

	@Override
	public List<EmpVo> selEmpYear(String date) {
		logger.info("AnnServiceImpl selEmpYear 올해 이전에 입사한 사원 조회 : {}", date);
		return mapper.selEmpYear(date);
	}

	@Override
	public int insAnnAddYear(String emp_id) {
		logger.info("AnnServiceImpl insAnnAddYear 올해 이전에 입사한 사원 연차(15개)부여(insert) : {}", emp_id);
		return mapper.insAnnAddYear(emp_id);
	}

	@Override
	public int updAnnualYear(Map<String, String[]> map) {
		logger.info("AnnServiceImpl updAnnualYear 올해 이전에 입사한 사원 연차(15개)부여(update) : {}", map);
		return mapper.updAnnualYear(map);
	}

	@Override
	public List<EmpVo> selEmpMonth(Map<String, Object> map) {
		logger.info("AnnServiceImpl selEmpMonth 올해 입사한 사원 조회 : {}", map);
		return mapper.selEmpMonth(map);
	}

	@Override
	public int insAnnAddMonth(String emp_id) {
		logger.info("AnnServiceImpl insAnnAddMonth 올해 입사한 사원 만근시 연차(1개) 부여(insert) : {}", emp_id);
		return mapper.insAnnAddMonth(emp_id);
	}

	@Override
	public int updAnnualMonth(Map<String, String[]> emp_ids) {
		logger.info("AnnServiceImpl insAnnAddMonth 올해 입사한 사원 만근시 연차(1개) 부여(update) : {}", emp_ids);
		return mapper.updAnnualMonth(emp_ids);
	}

	@Override
	public List<AnnualVo> selAnnualAdmin(String dept_no) {
		logger.info("AnnServiceImpl selAnnualAdmin 관리자 연차 조회 : {}", dept_no);
		return mapper.selAnnualAdmin(dept_no);
	}

}
