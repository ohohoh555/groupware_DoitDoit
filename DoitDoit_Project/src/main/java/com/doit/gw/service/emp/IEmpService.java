package com.doit.gw.service.emp;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.emp.EmpVo;

public interface IEmpService {

	public List<EmpVo> selEmpAll();
	public int insEmp(Map<String, Object> map);
	public List<EmpVo> selEmpDetail(String emp_id);
	public int resetPassword(String emp_id);
	public int upEmp(Map<String, Object> map);
	public int selEmpNfcCheck(String emp_nfc);
}
