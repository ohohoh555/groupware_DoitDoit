package com.doit.gw.service.entr;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.entr.EntrBoardVo;

public interface IEntrService {
	
	public List<EntrBoardVo> selEboardAllUser();
	public List<EntrBoardVo> selEboardCgoryUser(Map<String, Object> map);
	public List<EntrBoardVo> selEboardFildocThree();
	public List<EntrBoardVo> selEboardFildocAll();
	public EntrBoardVo selEboardDetail(String eboard_no);
	public int updEboardDelflagUser(String eboard_no);
	public int insEboardRoot(EntrBoardVo eVo);
	public int insEboardAttach(Map<String, Object>map);
	
	public int insEboardCald(EntrBoardVo eVo);
	
	public int updEboardCald(Map<String, Object> map);
	public int updEboardRoot(Map<String, Object> map);
	
	public List<EntrBoardVo> selEboardAllAdmin();
	public List<EntrBoardVo> selEboardCgoryAdmin(Map<String, Object> map);
	public int updEboardDelfAdmin(List<String> eboard_nos);
	public int delEboard(String eboard_no);
	
	
	
}