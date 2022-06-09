package com.doit.gw.mapper.entr;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.entr.FileListVo;

public interface IJaryoMapper {
	
	public List<FileListVo> selJaryoAllUser();
	public int insJaryoRoot(FileListVo fVo);
	public int insJaryoAttach(FileListVo fVo);
	
	public int updJaryoDelflagUser(Map<String, Object>map);
	
	public List<FileListVo> selJaryoAllAdmin();
	public int updJaryoDelflagAdmin(List<String> eboard_nos);
	public int delJaryoAttach(List<String> eboard_nos);
	public int delJaryoRoot(List<String> eboard_nos);

}
