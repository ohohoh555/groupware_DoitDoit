package com.doti.gw.test.ojh;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doit.gw.service.entr.IEntrService;
import com.doit.gw.vo.entr.EntrBoardVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class EntrBoardTest {
	
	@Autowired
	private IEntrService service;

	
	/*
	 * 사원 게시글 전체조회 테스트
	 */
//	@Test
	public void selEboardAllUserTest() {
		List<EntrBoardVo> lists = service.selEboardAllUser();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
	/*
	 * 사원 게시글 카테고리별 조회
	 */
//	@Test
	public void selEboardCgoryUserTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cgory_no", 101);
		
		List<EntrBoardVo> lists =service.selEboardCgoryUser(map);
		System.out.println(lists);
		assertNotNull(lists);
	}
	
	/*
	 * 필독 게시글 3개 조회
	 */
//	@Test
	public void selEboardFildocThreeTest() {
		List<EntrBoardVo> lists = service.selEboardFildocThree();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
	/*
	 * 필독 게시글 전체조회
	 */
//	@Test
	public void selEboardFildocAllTest() {
		List<EntrBoardVo> lists = service.selEboardFildocAll();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
	/*
	 * 공지게시글 상세조회
	 */
//	@Test
	public void selEboardDetailTest() {
		String eboard_no = "1";
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		System.out.println(entrOne);
		assertNotNull(entrOne );
	}
	
	/*
	 * 관리자 공지게시판 전체조회
	 */
//	@Test
	public void selEboardAllAdminTest() {
		List<EntrBoardVo> lists = service.selEboardAllAdmin();
		System.out.println(lists);
		assertNotNull(lists);
	}
	
	/*
	 * 관리자 공지게시판 카테고리별 조회
	 */
	@Test
	public void selEboardCgoryAdminTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cgory_no", 102);
		List<EntrBoardVo> lists = service.selEboardCgoryAdmin(map);
		System.out.println(lists);
		assertNotNull(lists);
	}
	
}
