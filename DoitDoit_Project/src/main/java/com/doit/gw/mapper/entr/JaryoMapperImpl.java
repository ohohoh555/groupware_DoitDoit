package com.doit.gw.mapper.entr;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.entr.FileListVo;

@Repository
public class JaryoMapperImpl implements IJaryoMapper {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "com.doit.gw.mapper.entr.JaryoMapperImpl.";

	@Override
	public List<FileListVo> selJaryoAllUser() {
		return sqlSession.selectList(NS+"selJaryoAllUser");
	}

}
