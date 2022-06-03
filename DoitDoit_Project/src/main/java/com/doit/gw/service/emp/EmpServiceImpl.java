package com.doit.gw.service.emp;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.emp.IEmpMapper;
import com.doit.gw.vo.emp.EmpVo;

@Service
public class EmpServiceImpl implements IEmpService{

	@Autowired
	private IEmpMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder password;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<EmpVo> selEmpAll() {
		logger.info("MemberServiceImpl selEmpAll 회원전체조회");
		return mapper.selEmpAll();
	}
	
	@Override
	public int insEmp(Map<String, Object> map) {
		logger.info("MemberServiceImpl insEmp 회원 가입 전달 받은 값 : " + map);
		int n = mapper.insEmp(map);
		EmpVo vo = mapper.pwdCheck();
		System.out.println("ID/PW : " + vo.getEmp_id() + "/" + vo.getEmp_password());
		String rPassword = password.encode(vo.getPassword());
		vo.setEmp_password(rPassword);
		mapper.pwdUpdate(vo);
		return n;
	}
	
	@Override
	public List<EmpVo> selEmpDetail(String emp_id) {
		logger.info("MemberServiceImpl selEmpDetail 회원 상세 조회");
		logger.info("전달 받은 emp_id : " + emp_id);
		
		return mapper.selEmpDetail(emp_id);
	}
	
	// 비밀번호 초기화
	@Override
	public int resetPassword(String emp_id) {
		logger.info("MemberServiceImpl resetPassword 비밀번호 초기화");
		mapper.resetPassword(emp_id);
		String pwd = mapper.resetPwdCheck(emp_id);
		logger.info("초기화된 비밀번호 : " + pwd);
		String rPwd = password.encode(pwd);
		logger.info("초기화 후 암호화 된 비밀번호 : " + rPwd);
		EmpVo eVo = new EmpVo();
		eVo.setEmp_id(emp_id);
		eVo.setEmp_password(rPwd);
		
		return mapper.pwdUpdate(eVo);
	}
}
