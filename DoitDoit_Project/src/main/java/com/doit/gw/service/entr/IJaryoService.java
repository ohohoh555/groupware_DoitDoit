package com.doit.gw.service.entr;

import java.util.List;

import com.doit.gw.vo.entr.FileListVo;

public interface IJaryoService {
	
	public List<FileListVo> selJaryoAllUser();
	public int insJaryo(FileListVo fVo);
	public int updJaryoDelflagUser(String eboard_no);
	
	public List<FileListVo> selJaryoAllAdmin();
	public int updJaryoDelflagAdmin(List<String> eboard_nos);
	public int delJaryo(List<String> eboard_nos);
}
