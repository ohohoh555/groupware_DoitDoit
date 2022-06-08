package com.doit.gw.service.resv;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.resv.IReservationMapper;
import com.doit.gw.vo.resv.ReservationVo;

@Service
public class ReservationServiceImpl implements IReservationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IReservationMapper mapper;
	
	@Override
	public boolean insResvRoom(Map<String, Object> map) {
		logger.info("ReservationServiceImpl insResvRoom 받아온 값 : {}",map);
		return mapper.insResvRoom(map);
	}

	@Override
	public List<ReservationVo> selResvRoom() {
		logger.info("ReservationServiceImpl selResvRoom");
		return mapper.selResvRoom();
	}

	@Override
	public List<ReservationVo> selResvAll() {
		logger.info("ReservationServiceImpl selResvAll");
		return mapper.selResvAll();
	}

	@Override
	public boolean updResv(Map<String, Object> map) {
		logger.info("ReservationServiceImpl updResv 받아온 값 : {}",map);
		return mapper.updResv(map);
	}

	@Override
	public boolean delResv(Map<String, Object> map) {
		logger.info("ReservationServiceImpl insResvRoom 받아온 값 : {}",map);
		return mapper.delResv(map);
	}

	@Override
	public boolean updResvDate(Map<String, Object> map) {
		return mapper.updResvDate(map);
	}
	
	@Override
	public List<ReservationVo> selResvInsDate() {
		return mapper.selResvInsDate();
	}

}
