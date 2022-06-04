package com.doit.gw.service.cald;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doit.gw.mapper.cald.ICalendarMapper;
import com.doit.gw.vo.entr.EntrBoardVo;

@Service
public class CalendarServiceImpl implements ICalendarService {
	
	@Autowired
	private ICalendarMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean insCald(Map<String, Object> map) {
		logger.info("CalendarServiceImpl insCald 파라미터 : {}", map);
		int m = mapper.insCald(map);
		int n = mapper.insGongji_Cald(map);
		return (n>0||m>0)?true:false;
	}

	@Override
	public List<EntrBoardVo> selCaldAll(String emp_id) {
		logger.info("CalendarServiceImpl selCaldAll 파라미터 : {}", emp_id);
		return mapper.selCaldAll(emp_id);
	}

	@Override
	public boolean updCaldDate(Map<String, Object> map) {
		logger.info("CalendarServiceImpl updCaldDate 파라미터 : {}", map);
		return mapper.updCaldDate(map);
	}

	@Override
	public boolean updCaldContent(Map<String, Object> map) {
		logger.info("CalendarServiceImpl updCaldContent 파라미터 : {}", map);
		return mapper.updCaldContent(map);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delCaldDate(String cald_id) {
		logger.info("CalendarServiceImpl delCaldDate 파라미터 : {}", cald_id);
		boolean isc = mapper.delCaldDate(cald_id);
		boolean isc2 = mapper.delCaldContent(cald_id);
		return (isc||isc2)?true:false;
	}

	@Override
	public boolean delCaldContent(String cald_id) {
		logger.info("CalendarServiceImpl delCaldContent 파라미터 : {}", cald_id);
		return mapper.delCaldContent(cald_id);
	}

}
