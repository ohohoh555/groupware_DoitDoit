package com.doit.gw.mapper.cald;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.entr.EntrBoardVo;

@Repository
public class CalendarMapperImpl implements ICalendarMapper {
	
	private static final String NS = "com.doit.gw.mapper.cald.CalendarMapperImpl.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int insGongji_Entr(Map<String, Object> map) {
		return sqlSession.insert(NS+"insGongji_Entr",map);
	}

	@Override
	public int insGongji_Cald(Map<String, Object> map) {
		return sqlSession.insert(NS+"insGongji_Cald",map);
	}

	@Override
	public int insCald(Map<String, Object> map) {
		return sqlSession.insert(NS+"insCald",map);
	}

	@Override
	public List<EntrBoardVo> selCaldAll(String emp_id) {
		return sqlSession.selectList(NS+"selCaldAll", emp_id);
	}

	@Override
	public boolean updCaldDate(Map<String, Object> map) {
		return sqlSession.update(NS+"updCaldDate", map)>0?true:false;
	}

	@Override
	public boolean updCaldContent(Map<String, Object> map) {
		return sqlSession.update(NS+"updCaldContent", map)>0?true:false;
	}

	@Override
	public boolean delCaldDate(String cald_id) {
		return sqlSession.delete(NS+"delCaldDate", cald_id)>0?true:false;
	}

	@Override
	public boolean delCaldContent(String cald_id) {
		return sqlSession.delete(NS+"delCaldDate", cald_id)>0?true:false;
	}
	
	
	
}
