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
	
	// 회원 상세 조회
	@Override
	public List<EmpVo> selEmpDetail(String emp_id) {
		return sqlSession.selectList(NS+"selEmpDetail", emp_id);
	}
	
	// 비밀번호 초기화
	@Override
	public int resetPassword(String emp_id) {
		return sqlSession.update(NS+"resetPassword",emp_id);
	}
	
	@Override
	public String resetPwdCheck(String emp_id) {
		return sqlSession.selectOne(NS+"resetPwdCheck", emp_id);
	}
	
	@Override
	public int upEmp(Map<String, Object> map) {
		return sqlSession.update(NS+"upEmp", map);
	}

	//nfc 중복확인
	@Override
	public int selEmpNfcCheck(String emp_nfc) {
		return sqlSession.selectOne(NS+"selEmpNfcCheck", emp_nfc);
	}
	
	//서명등록을 위한 id 불러오기
	@Override
	public int getMaxId() {
		return sqlSession.selectOne(NS+"getMaxId");
	}
	
	@Override
	public int updPassPhone(Map<String, Object> map) {
		return sqlSession.update(NS+"updPassPhone", map);
	}
}
