package com.doit.gw.service.emp;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.emp.EmpVo;

public interface IEmpService {

//	public EmpVo login(String id);
	public List<EmpVo> selEmpAll();
	public int insEmp(Map<String, Object> map);
}
