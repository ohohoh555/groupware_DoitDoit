package com.doti.gw.test.ojh;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doit.gw.service.entr.IJaryoService;
import com.doit.gw.vo.entr.FileListVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class JaryoBoardTest {
	
	@Autowired
	private IJaryoService service;

//	@Test
	public void selJaryoAllUserTest() {
		List<FileListVo> list = service.selJaryoAllUser();
		assertNotNull(list);
	}
	
//	@Test
	public void selJaryoAllAdminTest() {
		List<FileListVo> list = service.selJaryoAllAdmin();
		assertNotNull(list);
	}
	
	@Test
	public void delJaryoTest() {
		List<String> lists = new ArrayList<String>();
		lists.add("478");
		int cnt = service.delJaryo(lists);
		System.out.println("자료글 삭제 성공횟수 :"+cnt);
	}

}
