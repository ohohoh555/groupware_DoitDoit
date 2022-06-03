package com.doit.gw.mapper.cald;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.resv.ReservationVo;


public interface ICalendarMapper {
	
	public int insGongji_Entr(Map<String, Object> map);
	
	public int insGongji_Cald(Map<String, Object> map);
	
	public int insCald(Map<String, Object> map);
	
	public List<EntrBoardVo> selCaldAll(String emp_id);
	
	public boolean updCaldDate(Map<String, Object> map);
	
	public boolean updCaldContent(Map<String, Object> map);
	
	public boolean delCaldDate(String cald_id);
	
	public boolean delCaldContent(String cald_id);
	
}
