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


	@Override
	public int insJaryo(FileListVo fVo) {
		logger.info("@insJaryoRoot 사용자 자료글 입력");
		int cnt = mapper.insJaryoRoot(fVo);
		logger.info("@insJaryoRoot 사용자 자료글 Root 입력성공:{}",cnt);
		return mapper.insJaryoAttach(fVo);
	}


	@Override
	public int updJaryoDelflagUser(String eboard_no) {
		logger.info("@updJaryoDelflagUser 사용자 자료글 삭제 : {}", eboard_no);
		return mapper.updJaryoDelflagUser(eboard_no);
	}

}
