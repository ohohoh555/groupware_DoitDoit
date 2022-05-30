package com.doit.gw.mapper.emp;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.emp.EmpVo;


 
@Repository
public class EmpMapperImpl implements IEmpMapper {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "com.doit.gw.mapper.emp.EmpMapperImpl.";
	
	@Override
	public EmpVo login(String id) {
		return sqlSession.selectOne(NS+"login", id);
	}
	
	@Override
	public List<EmpVo> selEmpAll() {
		return sqlSession.selectList(NS+"selEmpAll");
	}
	
	@Override
	public int insEmp(Map<String, Object> map) {
		System.out.println("전달 받은 map : " + map);
		System.out.println("비밀번호 : " + map.get("emp_password"));
		
		return sqlSession.insert(NS+"insEmp", map);
//		return 0;
	}
	
	@Override
	public EmpVo pwdCheck() {
		return sqlSession.selectOne(NS+"pwdCheck");
	}
	
	@Override
	public int pwdUpdate(EmpVo vo) {
		return sqlSession.update(NS+"pwdUpdate",vo);
	}
	
}
