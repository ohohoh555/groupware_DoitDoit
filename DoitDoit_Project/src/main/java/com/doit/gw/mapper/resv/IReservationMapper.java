package com.doit.gw.mapper.resv;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.resv.ReservationVo;

public interface IReservationMapper {
	
	public boolean insResvRoom(Map<String, Object> map);
	
	public List<ReservationVo> selResvRoom();
	
	public List<ReservationVo> selResvAll();
	
	public boolean updResv(Map<String, Object> map);
	
	public boolean delResv(Map<String, Object> map);
	
	public boolean updResvDate(Map<String, Object> map);

	public List<ReservationVo> selResvInsDate();
	
}
