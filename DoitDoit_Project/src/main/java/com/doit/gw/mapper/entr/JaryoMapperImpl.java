package com.doit.gw.mapper.entr;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.entr.FileListVo;

@Repository
public class JaryoMapperImpl implements IJaryoMapper {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "com.doit.gw.mapper.board.JaryoMapperImpl.";

	@Override
	public List<FileListVo> selJaryoAllUser() {
		return sqlSession.selectList(NS+"selJaryoAllUser");
	}

	@Override
	public int insJaryoRoot(FileListVo fVo) {
		return sqlSession.insert(NS+"insJaryoRoot", fVo);
	}

	@Override
	public int insJaryoAttach(FileListVo fVo) {
		return sqlSession.insert(NS+"insJaryoAttach", fVo);
	}

	@Override
	public int updJaryoDelflagUser(String eboard_no) {
		return sqlSession.update(NS+"updJaryoDelflagUser", eboard_no);
	}

	@Override
	public List<FileListVo> selJaryoAllAdmin() {
		return sqlSession.selectList(NS+"selJaryoAllAdmin");
	}

	@Override
	public int updJaryoDelflagAdmin(List<String> eboard_nos) {
		return sqlSession.update(NS+"updJaryoDelflagAdmin", eboard_nos);
	}

	@Override
	public int delJaryoAttach(List<String> eboard_nos) {
		return sqlSession.delete(NS+"delJaryoAttach", eboard_nos);
	}

	@Override
	public int delJaryoRoot(List<String> eboard_nos) {
		return sqlSession.delete(NS+"delJaryoRoot", eboard_nos);
	}

}
