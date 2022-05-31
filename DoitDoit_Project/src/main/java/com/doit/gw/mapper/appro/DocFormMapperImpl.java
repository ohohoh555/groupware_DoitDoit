package com.doit.gw.mapper.appro;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.appro.DocFormVo;
@Repository
public class DocFormMapperImpl implements IDocFormMapper {
	@Autowired
	private SqlSessionTemplate sqlSession;

	private final String NS = "com.doit.gw.mapper.appro.DocFormMapperImpl.";

	@Override
	public DocFormVo selDocForm(String doc_form_no) {
		return sqlSession.selectOne(NS+"selDocForm",doc_form_no);
	}
}
