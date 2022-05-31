package com.doit.gw.mapper.ann;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public int updAnnualYear(Map<String, String[]> map) {
		return sqlSession.update(NS+"updAnnualYear", map);
	}

}
