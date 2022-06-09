package com.doit.gw.test.minjin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.doit.gw.service.chat.IChatService;
import com.doit.gw.vo.chat.ChatFileVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class ChatTest {
	@Autowired
	IChatService service;
	
	@Test
	public void chatFileTest() {
		ChatFileVo vo = new ChatFileVo(null, "uuid", "origin", "png", "C:", "6");
		
		int n = service.insFile(vo);
		System.out.print(n);
	}
}
