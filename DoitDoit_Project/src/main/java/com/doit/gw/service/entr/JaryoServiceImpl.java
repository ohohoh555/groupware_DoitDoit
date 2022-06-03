package com.doit.gw.service.entr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.entr.IJaryoMapper;
import com.doit.gw.vo.entr.FileListVo;

@Service
public class JaryoServiceImpl implements IJaryoService {

	@Autowired
	private IJaryoMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public List<FileListVo> selJaryoAllUser() {
		logger.info("@selJaryoAllUser 사용자 자료게시판 전체조회");
		return mapper.selJaryoAllUser();
	}

}
