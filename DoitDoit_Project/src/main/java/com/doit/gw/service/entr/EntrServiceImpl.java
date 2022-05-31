package com.doit.gw.service.entr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.entr.IEntrMapper;
import com.doit.gw.vo.entr.EntrBoardVo;

@Service
public class EntrServiceImpl implements IEntrService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEntrMapper mapper;

	@Override
	public List<EntrBoardVo> selEboardAllUser() {
		logger.info("EntrServiceImpl selEboardAllUser 사원 게시글 전체조회");
		return mapper.selEboardAllUser();
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryUser(Map<String, Object> map) {
		logger.info("EntrServiceImpl selEboardCgoryUser 사원 게시글 카테고리별 조회 : {}", map);
		return mapper.selEboardCgoryUser(map);
	}

	@Override
	public List<EntrBoardVo> selEboardFildocThree() {
		logger.info("EntrServiceImpl selEboardFildocThree 필독 게시글 3개 조회 ");
		return mapper.selEboardFildocThree();
	}

	@Override
	public List<EntrBoardVo> selEboardFildocAll() {
		logger.info("EntrServiceImpl selEboardFildocAll 필독 게시글 전체조회 ");
		return mapper.selEboardFildocAll();
	}

	@Override
	public EntrBoardVo selEboardDetail(String eboard_no) {
		logger.info("EntrServiceImpl selEboardDetail 공지게시글 상세조회 : 글번호 {}", eboard_no);
		int cnt=mapper.updEboardReadCnt(eboard_no);
		logger.info("EntrServiceImpl updEboardReadCnt 상세조회시 조회수 증가 : 성공 {}",cnt);
		return mapper.selEboardDetail(eboard_no);
	}

	@Override
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("EntrServiceImpl selEboardAllAdmin 관리자 공지게시판 전체조회");
		return mapper.selEboardAllAdmin();
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryAdmin(Map<String, Object> map) {
		logger.info("EntrServiceImpl selEboardCgoryAdmin 관리자 카테고리별 조회 : {}", map);
		return mapper.selEboardCgoryAdmin(map);
	}

	@Override
	public int updEboardDelflagUser(String eboard_no) {
		logger.info("EntrServiceImpl updEboardDelflagUser 사용자 게시글 삭제처리 : {}", eboard_no);
		return mapper.updEboardDelflagUser(eboard_no);
	}

	

}
