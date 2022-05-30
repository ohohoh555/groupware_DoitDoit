package com.doit.gw.mapper.emp;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.emp.EmpVo;


public interface IEmpMapper {

	public EmpVo login(String id);
	public List<EmpVo> selEmpAll();
	public int insEmp(Map<String, Object> map);
	public EmpVo pwdCheck();
	public int pwdUpdate(EmpVo vo);
}
