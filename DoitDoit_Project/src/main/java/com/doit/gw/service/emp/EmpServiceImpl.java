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
	public EmpVo login(String id) {
		logger.info("MemberServiceImpl login 전달 받은 ID : " + id);
		return mapper.login(id);
	}
	
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
}
