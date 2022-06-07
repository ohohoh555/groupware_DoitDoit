package com.doit.gw.mapper.resv;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.resv.ReservationVo;

@Repository
public class ReservationMapperImpl implements IReservationMapper {
	
	private final String NS = "com.doit.gw.mapper.resv.ReservationMapperImpl.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public boolean insResvRoom(Map<String, Object> map) {
		return sqlSession.insert(NS+"insResvRoom",map)>0?true:false;
	}

	@Override
	public List<ReservationVo> selResvRoom() {
		return sqlSession.selectList(NS+"selResvRoom");
	}

	@Override
	public List<ReservationVo> selResvAll() {
		return sqlSession.selectList(NS+"selResvAll");
	}

	@Override
	public boolean updResv(Map<String, Object> map) {
		return sqlSession.update(NS+"updResv", map)>0?true:false;
	}

	@Override
	public boolean delResv(Map<String, Object> map) {
		return sqlSession.delete(NS+"delResv", map)>0?true:false;
	}

	@Override
	public boolean updResvDate(Map<String, Object> map) {
		return sqlSession.update(NS+"updResvDate", map)>0?true:false;
	}
	
	@Override
	public List<ReservationVo> selResvInsDate() {
		return sqlSession.selectList(NS+"selResvInsDate");
	}

}
