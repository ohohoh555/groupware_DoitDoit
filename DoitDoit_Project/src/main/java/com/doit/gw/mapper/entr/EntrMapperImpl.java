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

}
