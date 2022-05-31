package com.doit.gw.service.sign;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.sign.ISignMapper;
import com.doit.gw.vo.sign.SignVo;

@Service
public class SignServiceImpl implements ISignService {

	@Autowired
	private ISignMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public int insSign(SignVo signVo) {
		logger.info("===== SignServiceImpl insSign 실행 : vo 값 : {} =====",signVo);
		return mapper.insSign(signVo);
	}

	@Override
	public List<Map<String, Object>> selSign(int emp_id) {
		logger.info("===== SignServiceImpl selSign 실행 =====");
		return mapper.selSign(emp_id);
	}

	@Override
	public int updSign(SignVo signVo) {
		logger.info("===== SignServiceImpl updSign 실행 : vo 값 : {} =====",signVo);
		return mapper.updSign(signVo);
	}
	
}
