package com.doit.gw.mapper.entr;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.entr.EntrBoardVo;

@Repository
public class EntrMapperImpl implements IEntrMapper {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "com.doit.gw.mapper.board.EntrMapperImpl.";
	
	@Override
	public List<EntrBoardVo> selEboardResent() {
		return sqlSession.selectList(NS+"selEboardResent");
	}

	@Override
	public List<EntrBoardVo> selEboardAllUser() {
		return sqlSession.selectList(NS+"selEboardAllUser");
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryUser(Map<String, Object> map) {
		return sqlSession.selectList(NS+"selEboardCgoryUser", map);
	}

	@Override
	public List<EntrBoardVo> selEboardFildocThree() {
		return sqlSession.selectList(NS+"selEboardFildocThree");
	}

	@Override
	public List<EntrBoardVo> selEboardFildocAll() {
		return sqlSession.selectList(NS+"selEboardFildocAll");
	}

	@Override
	public EntrBoardVo selEboardDetail(String eboard_no) {
		return sqlSession.selectOne(NS+"selEboardDetail", eboard_no);
	}

	@Override
	public int updEboardReadCnt(String eboard_no) {
		return sqlSession.update(NS+"updEboardReadCnt", eboard_no);
	}

	@Override
	public List<EntrBoardVo> selEboardAllAdmin() {
		return sqlSession.selectList(NS+"selEboardAllAdmin");
	}

	@Override
	public List<EntrBoardVo> selEboardCgoryAdmin(Map<String, Object> map) {
		return sqlSession.selectList(NS+"selEboardCgoryAdmin", map);
	}

	@Override
	public int updEboardDelflagUser(String eboard_no) {
		return sqlSession.update(NS+"updEboardDelflagUser", eboard_no);
	}

	@Override
	public int insEboardRoot(Map<String, Object>map) {
		return sqlSession.insert(NS+"insEboardRoot", map);
	}

	@Override
	public int updEboardDelfAdmin(List<String> eboard_nos) {
		return sqlSession.update(NS+"updEboardDelfAdmin", eboard_nos);
	}

	@Override
	public int delEboardRoot(String eboard_no) {
		return sqlSession.delete(NS+"delEboardRoot", eboard_no);
	}

	@Override
	public int insEboardAttach(Map<String, Object> map) {
		return sqlSession.insert(NS+"insEboardAttach", map);
	}

	@Override
	public int insEboardCald(Map<String, Object>map) {
		return sqlSession.insert(NS+"insEboardCald",  map);
	}

	@Override
	public int insCaldRoot(Map<String, Object>map) {
		return sqlSession.insert(NS+"insCaldRoot", map);
	}

	@Override
	public int delEboardAttach(String eboard_no) {
		return sqlSession.delete(NS+"delEboardAttach", eboard_no);
	}

	@Override
	public int updEboardCald(Map<String, Object> map) {
		return sqlSession.update(NS+"updEboardCald", map);
	}

	@Override
	public int updEboardRoot(Map<String, Object> map) {
		return sqlSession.update(NS+"updEboardRoot", map);
	}



}
