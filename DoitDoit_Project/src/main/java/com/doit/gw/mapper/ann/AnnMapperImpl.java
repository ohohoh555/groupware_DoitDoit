package com.doit.gw.mapper.ann;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.ann.AnnAddVo;
import com.doit.gw.vo.ann.AnnUseVo;
import com.doit.gw.vo.ann.AnnualVo;
import com.doit.gw.vo.emp.EmpVo;

@Repository
public class AnnMapperImpl implements IAnnMapper {

	@Autowired 
	private SqlSessionTemplate sqlSession;

	private final String NS = "com.doit.gw.mapper.ann.AnnMapperImpl.";
	
	@Override
	public int insAnnual() {
		return sqlSession.insert(NS+"insAnnual");
	}

	@Override
	public int delAnnAddReset() {
		return sqlSession.delete(NS+"delAnnAddReset");
	}

	@Override
	public int delAnnUseReset() {
		return sqlSession.delete(NS+"delAnnUseReset");
	}

	@Override
	public int updAnnualReset() {
		return sqlSession.update(NS+"updAnnualReset");
	}

	@Override
	public List<EmpVo> selEmpYear(String date) {
		return sqlSession.selectList(NS+"selEmpYear", date);
	}

	@Override
	public int insAnnAddYear(String emp_id) {
		return sqlSession.insert(NS+"insAnnAddYear", emp_id);
	}

	@Override
	public int updAnnualYear(Map<String, String[]> emp_ids) {
		return sqlSession.update(NS+"updAnnualYear", emp_ids);
	}

	@Override
	public List<EmpVo> selEmpMonth(Map<String, Object> map) {
		return sqlSession.selectList(NS+"selEmpMonth", map);
	}

	@Override
	public int insAnnAddMonth(String emp_id) {
		return sqlSession.insert(NS+"insAnnAddMonth", emp_id);
	}

	@Override
	public int updAnnualMonth(Map<String, String[]> emp_ids) {
		return sqlSession.update(NS+"updAnnualMonth", emp_ids);
	}

	@Override
	public int updAnnualEveryDay() {
		return sqlSession.update(NS+"updAnnualEveryDay");
	}
	
	@Override
	public List<AnnualVo> selAnnualAdmin(String dept_no) {
		return sqlSession.selectList(NS+"selAnnualAdmin", dept_no);
	}

	@Override
	public int insAnnAdd(AnnAddVo annAddVo) {
		return sqlSession.insert(NS+"insAnnAdd", annAddVo);
	}

	@Override
	public int updAnnualAdd(Map<String, Object> map) {
		return sqlSession.update(NS+"updAnnualAdd", map);
	}

	@Override
	public AnnualVo selAnnualEmp(String emp_id) {
		return sqlSession.selectOne(NS+"selAnnualEmp", emp_id);
	}

	@Override
	public List<AnnAddVo> selAnnAddEmp(String emp_id) {
		return sqlSession.selectList(NS+"selAnnAddEmp", emp_id);
	}

	@Override
	public List<AnnUseVo> selAnnUseEmp(String emp_id) {
		return sqlSession.selectList(NS+"selAnnUseEmp", emp_id);
	}

	@Override
	public int updAnnualWorkIn(String emp_nfc) {
		return sqlSession.update(NS+"updAnnualWorkIn", emp_nfc);
	}

	@Override
	public int updAnnualWorkOut(String emp_nfc) {
		return sqlSession.update(NS+"updAnnualWorkOut", emp_nfc);
	}

	@Override
	public int updAnnualWorkDays(String emp_nfc) {
		return sqlSession.update(NS+"updAnnualWorkDays", emp_nfc);
	}

}
