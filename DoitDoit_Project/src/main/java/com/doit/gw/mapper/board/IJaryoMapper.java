package com.doit.gw.mapper.board;

import java.util.List;

import com.doit.gw.vo.entr.FileListVo;

public interface IJaryoMapper {
	
	public List<FileListVo> selJaryoAllUser();
	public int insJaryoRoot(FileListVo fVo);
	public int insJaryoAttach(FileListVo fVo);
	
	public int updJaryoDelflagUser(String eboard_no);
	
	public List<FileListVo> selJaryoAllAdmin();

}
