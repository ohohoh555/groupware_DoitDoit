package com.doit.gw.service.cald;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.entr.EntrBoardVo;

public interface ICalendarService {
	
	public boolean insCald(Map<String, Object> map);
	
	public List<EntrBoardVo> selCaldAll(String emp_id);
	
	public boolean updCaldDate(Map<String, Object> map);
	
	public boolean updCaldContent(Map<String, Object> map);
	
	public boolean delCaldDate(String cald_id);
	
	public boolean delCaldContent(String cald_id);
	
}
