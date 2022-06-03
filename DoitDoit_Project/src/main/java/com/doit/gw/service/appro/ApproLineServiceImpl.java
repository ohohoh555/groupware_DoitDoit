package com.doit.gw.service.appro;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.appro.IApproLineMapper;
import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;

@Service
public class ApproLineServiceImpl implements IApproLineService {
	
	@Autowired
	private IApproLineMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ApproEmpVo> selEmp() {
		logger.info("===== ApproLineServiceImpl selectEmp 실행 =====");
		return mapper.selEmp();
	}

	@Override
	public List<ApproDeptVo> selDept() {
		logger.info("===== ApproLineServiceImpl selectDept 실행 =====");
		return mapper.selDept();
	}

	@Override
	public ApproEmpVo selEmpInfo(int emp_id) {
		logger.info("===== ApproLineServiceImpl selEmpInfo 실행 =====");
		return mapper.selEmpInfo(emp_id);
	}

}
