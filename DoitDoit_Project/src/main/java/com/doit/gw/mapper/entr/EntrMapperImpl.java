package com.doit.gw.mapper.entr;

import java.util.ArrayList;
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
	
	private final String NS = "com.doit.gw.mapper.entr.EntrMapperImpl.";

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
	public int insEboardRoot(EntrBoardVo eVo) {
		return sqlSession.insert(NS+"insEboardRoot", eVo);
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
	public int insEboardCald(EntrBoardVo eVo) {
		return sqlSession.insert(NS+"insEboardCald",  eVo);
	}

	@Override
	public int insCaldRoot(EntrBoardVo eVo) {
		return sqlSession.insert(NS+"insCaldRoot", eVo);
	}

}
