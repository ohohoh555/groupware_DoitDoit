package com.doit.gw.mapper.appro;

import java.util.List;

import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;

public interface IApproLineMapper {
	
	public List<ApproEmpVo> selEmp();
	public List<ApproDeptVo> selDept();

}
