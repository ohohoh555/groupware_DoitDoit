package com.doit.gw.service.appro;

import java.util.List;

import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;

public interface IApproLineService {
	
	public List<ApproEmpVo> selEmp();
	public List<ApproDeptVo> selDept();
	public ApproEmpVo selEmpInfo(int emp_id);
}
