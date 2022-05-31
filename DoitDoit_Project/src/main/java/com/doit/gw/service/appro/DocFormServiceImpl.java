package com.doit.gw.service.appro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.appro.IDocFormMapper;
import com.doit.gw.vo.appro.DocFormVo;

@Service
public class DocFormServiceImpl implements IDocFormService {
	
	@Autowired
	private IDocFormMapper mapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public DocFormVo selDocForm(String doc_form_no) {
		logger.info("===== DocFormServiceImpl selDocForm 실행 : doc_form_no 값 : {} =====",doc_form_no);
		return mapper.selDocForm(doc_form_no);
	}


}
