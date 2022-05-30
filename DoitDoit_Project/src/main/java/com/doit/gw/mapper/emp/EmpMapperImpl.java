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
	
	// 회원전체조회
	@Override
	public List<EmpVo> selEmpAll() {
		return sqlSession.selectList(NS+"selEmpAll");
	}
	
	// 회원등록
	@Override
	public int insEmp(Map<String, Object> map) {
		System.out.println("전달 받은 map : " + map);
		System.out.println("비밀번호 : " + map.get("emp_password"));
		
		return sqlSession.insert(NS+"insEmp", map);
	}
	
	// 비밀번호 확인 (입력된 비밀번호를 암호화 시키기 위함)
	@Override
	public EmpVo pwdCheck() {
		return sqlSession.selectOne(NS+"pwdCheck");
	}
	
	// 비밀번호 업데이트 (암호화된 비밀번호로 수정을 위함)
	@Override
	public int pwdUpdate(EmpVo vo) {
		return sqlSession.update(NS+"pwdUpdate",vo);
	}
	
}
