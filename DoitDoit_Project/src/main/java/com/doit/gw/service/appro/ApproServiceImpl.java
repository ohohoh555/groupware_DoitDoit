package com.doit.gw.service.appro;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.appro.IApproMapper;
import com.doit.gw.vo.appro.ApproVo;

@Service
public class ApproServiceImpl implements IApproService {
	
	@Autowired
	private IApproMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ApproVo insApproval(ApproVo approVo) {
		logger.info("===== ApproServiceImpl insApproval 실행 : vo 값 : {} =====",approVo);
		return mapper.insApproval(approVo);
	}

	@Override
	public int insApproLine(ApproVo approVo) {
		logger.info("===== ApproServiceImpl insApproLine 실행 : vo 값 : {} =====",approVo);
		return mapper.insApproLine(approVo);
	}

	@Override
	public int insApproDraft(ApproVo approVo) {
		logger.info("===== ApproServiceImpl insApproDraft 실행 : vo 값 : {} =====",approVo);
		return mapper.insApproDraft(approVo);
	}
	
	@Override
	public List<ApproVo> selMyDocument(int emp_id) {
		logger.info("===== ApproServiceImpl selMyDocument 실행 =====");
		return mapper.selMyDocument(emp_id);
	}

	@Override
	public List<Map<String, Object>> selAllDocument(int emp_id) {
		logger.info("===== ApproServiceImpl selAllDocument 실행 =====");
		return mapper.selAllDocument(emp_id);
	}
	
	@Override
	public List<ApproVo> selStatusDocument(ApproVo approVo) {
		logger.info("===== ApproServiceImpl selStatusDocument 실행 =====");
		return mapper.selStatusDocument(approVo);
	}

	@Override
	public ApproVo selOneDocument(String appro_no) {
		logger.info("===== ApproServiceImpl selOneDocument 실행 =====");
		return mapper.selOneDocument(appro_no);
	}

	@Override
	public List<Map<String, Object>> selLineList(String appro_no) {
		logger.info("===== ApproServiceImpl selLineList 실행 =====");
		return mapper.selLineList(appro_no);
	}

	@Override
	public int updApprovedApproLine(ApproVo approVo) {
		logger.info("===== ApproServiceImpl updApprovedApproLine 실행 =====");
		return mapper.updApprovedApproLine(approVo);
	}

	@Override
	public int updApprovedAppro(int appro_line_no) {
		logger.info("===== ApproServiceImpl updApprovedAppro 실행 =====");
		return mapper.updApprovedAppro(appro_line_no);
	}

	@Override
	public List<ApproVo> selFinalDoc(int emp_id) {
		logger.info("===== ApproServiceImpl selFinalDoc 실행 =====");
		return mapper.selFinalDoc(emp_id);
	}


}
