package com.doit.gw.service.entr;

import java.util.ArrayList;
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
		logger.info("@selEboardAllUser 사원 게시글 전체조회");
		return mapper.selEboardAllUser();
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryUser(Map<String, Object> map) {
		logger.info("@selEboardCgoryUser 사원 게시글 카테고리별 조회 : {}", map);
		return mapper.selEboardCgoryUser(map);
	}

	@Override
	public List<EntrBoardVo> selEboardFildocThree() {
		logger.info("@selEboardFildocThree 필독 게시글 3개 조회 ");
		return mapper.selEboardFildocThree();
	}

	@Override
	public List<EntrBoardVo> selEboardFildocAll() {
		logger.info("@selEboardFildocAll 필독 게시글 전체조회 ");
		return mapper.selEboardFildocAll();
	}

	@Override
	public EntrBoardVo selEboardDetail(String eboard_no) {
		logger.info("@selEboardDetail 공지게시글 상세조회 : 글번호 {}", eboard_no);
		int cnt=mapper.updEboardReadCnt(eboard_no);
		logger.info("@updEboardReadCnt 상세조회시 조회수 증가 : 성공 {}",cnt);
		return mapper.selEboardDetail(eboard_no);
	}

	@Override
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("@selEboardAllAdmin 관리자 공지게시판 전체조회");
		return mapper.selEboardAllAdmin();
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryAdmin(Map<String, Object> map) {
		logger.info("@selEboardCgoryAdmin 관리자 카테고리별 조회 : {}", map);
		return mapper.selEboardCgoryAdmin(map);
	}

	@Override
	public int updEboardDelflagUser(String eboard_no) {
		logger.info("@updEboardDelflagUser 사용자 게시글 삭제처리 : {}", eboard_no);
		return mapper.updEboardDelflagUser(eboard_no);
	}

	@Override
	public int insEboardRoot(EntrBoardVo eVo) {
		logger.info("@insEboardRoot 사용자 게시글 입력 : {}", eVo);
		return mapper.insEboardRoot(eVo);
	}

	@Override
	public int updEboardDelfAdmin(List<String> eboard_nos) {
		logger.info("@updEboardDelfAdmin 관리자 게시글 숨김/보임처리 : {}", eboard_nos.toString());
		return mapper.updEboardDelfAdmin(eboard_nos);
	}

	@Override
	public int delEboard(String eboard_no) {
		logger.info("@delEboardRoot 관리자 게시글 완전삭제 : {}", eboard_no);
		int cnt1 = mapper.delEboardAttach(eboard_no);
		return mapper.delEboardRoot(eboard_no);
	}

	@Override
	public int insEboardAttach(Map<String, Object> map) {
		logger.info("@insEboardAttach 게시글 입력시 첨부파일 저장: {}",map);
		return mapper.insEboardAttach(map);
	}

	@Override
	public int insEboardCald(EntrBoardVo eVo) {
		logger.info("@insEboardCald 사용자 일정 게시글 입력 : {}",eVo);
		int cnt = mapper.insEboardCald(eVo);
		logger.info("@캘린더에 일정 먼저 등록 성공 : {}", cnt);
		return mapper.insCaldRoot(eVo);
	}

	@Override
	public int updEboardCald(Map<String, Object> map) {
		logger.info("@updEboardCald 일정게시글의 일시 수정:{}",map);
		return mapper.updEboardCald(map);
	}

	@Override
	public int updEboardRoot(Map<String, Object> map) {
		logger.info("@updEboardRoot 사용자 게시글 수정:{}",map);
		return mapper.updEboardRoot(map);
	}


	

}
