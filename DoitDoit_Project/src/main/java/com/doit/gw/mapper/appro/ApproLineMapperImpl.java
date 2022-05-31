package com.doit.gw.mapper.appro;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;

@Repository
public class ApproLineMapperImpl implements IApproLineMapper {
	@Autowired
	private SqlSessionTemplate sqlSession;

	private final String NS = "com.doit.gw.mapper.appro.ApproLineMapperImpl.";

	@Override
	public List<ApproEmpVo> selEmp() {
		return sqlSession.selectList(NS+"selEmp");
	}

	@Override
	public List<ApproDeptVo> selDept() {
		return sqlSession.selectList(NS+"selDept");
	}


}
