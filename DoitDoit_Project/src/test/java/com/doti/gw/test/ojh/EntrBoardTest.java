package com.doti.gw.test.ojh;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

//	@Test
	public void selEboardAllUserTest() {
		List<EntrBoardVo> lists = service.selEboardAllUser();
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void selEboardCgoryUserTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cgory_no", 101);
		
		List<EntrBoardVo> lists =service.selEboardCgoryUser(map);
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void selEboardFildocThreeTest() {
		List<EntrBoardVo> lists = service.selEboardFildocThree();
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void selEboardFildocAllTest() {
		List<EntrBoardVo> lists = service.selEboardFildocAll();
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void selEboardDetailTest() {
		String eboard_no = "1";
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		System.out.println(entrOne);
		assertNotNull(entrOne );
	}
	

//	@Test
	public void selEboardAllAdminTest() {
		List<EntrBoardVo> lists = service.selEboardAllAdmin();
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void selEboardCgoryAdminTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cgory_no", 102);
		List<EntrBoardVo> lists = service.selEboardCgoryAdmin(map);
		System.out.println(lists);
		assertNotNull(lists);
	}
	

//	@Test
	public void updEboardDelflagUserTest() {
		String seq = "66";
		int cnt = service.updEboardDelflagUser(seq);
		System.out.println(cnt>0?"삭제성공":"삭제실패");
	}
	
//	@Test
	public void insEboardRootTest() {
//		EntrBoardVo eVo = new EntrBoardVo();
//		eVo.setCgory_no("101");
//		eVo.setEmp_id("2022053000");
//		eVo.setEmp_name("오지혜");
//		eVo.setEboard_title("입력테스트");
//		eVo.setEboard_content("<p>일반 게시글 입력 테스트 중</p>");
//		
//		int cnt = service.insEboardRoot(eVo);
//		System.out.println(cnt>0?"입력성공":"입력실패");
	}
	
//	@Test
	public void updEboardDelfAdminTest() {
		List<String> eboard_nos = new ArrayList<String>();
		eboard_nos.add("61");
		eboard_nos.add("18");
		eboard_nos.add("3");
		
		int cnt = service.updEboardDelfAdmin(eboard_nos);
		System.out.println(cnt>0?"성공":"실패");
	}
	
	@Test
	public void insEboardAttachTest() {
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("cgory_no", 101);
		map.put("flist_uuid", "283cbf48-7ab0-4d71-8c8d-d66076bfb038");
		map.put("originname", "image-20220602152921-1.jpeg");
		map.put("flist_size", "102713");
		map.put("flist_uploadpath", "C:\\Users\\user\\git\\groupware_doit\\DoitDoit_Project\\src\\main\\resources\\back\\2022\\06\\");
		
		int cnt = service.insEboardAttach(map);
		System.out.println(cnt>0?"성공":"실패");
	}
}
