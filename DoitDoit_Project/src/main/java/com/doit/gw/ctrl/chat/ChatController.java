package com.doit.gw.ctrl.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.doit.gw.service.chat.IChatService;
import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatVo;

@Controller
public class ChatController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private IChatService service;

	private List<String> listMem;
	private List<ChatVo> listChat;

	private static Map<String, List<String>> mapMem;
	private static Map<String, List<ChatVo>> mapChat;
	
	private static int i;

	public ChatController() {
		listChat = new ArrayList<ChatVo>();
		listMem = new ArrayList<String>();

		mapMem = new HashMap<String, List<String>>();
		mapChat = new HashMap<String, List<ChatVo>>();
	}
	
	public static List<ChatVo> getListChat(String room_id) {
		List<ChatVo> sendChat = mapChat.get(room_id);
		return sendChat;
	}

	// 채팅방에 들어왔을때
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatVo cVo) {
		logger.info("d"+i++);
		logger.info("@ChatController enter() : {}", cVo);
		logger.info("enterMem {}", mapMem.get(cVo.getRoom_id()));

		logger.info("^^^^ 해당 방 멤버 {} ^^^^", mapMem.get(cVo.getRoom_id()));
		if (mapMem.get(cVo.getRoom_id()) != null) {
			listMem = mapMem.get(cVo.getRoom_id());
			logger.info("멤버 있음 {}", listMem);
		} else {
			listMem = new ArrayList<String>();
			logger.info("멤버 없음 {}", listMem);
		}

		if (!listMem.contains(cVo.getEmp_id())) {
			listMem.add(cVo.getEmp_id());
			logger.info("멤버 추가 {}", listMem);
			mapMem.put(cVo.getRoom_id(), listMem);
		}

		logger.info("^^^^^^ 멤버 확인 {} ^^^^^^", mapMem);
		template.convertAndSend("/sub/chatMem/room/" + cVo.getRoom_id(), mapMem.get(cVo.getRoom_id()));
	}

	// 채팅방 나갔을 때
	@MessageMapping(value = "/chat/out")
	public void out(ChatVo cVo) {
		logger.info("@ChatController out() : {}", cVo);

		listMem = mapMem.get(cVo.getRoom_id());
		listMem.remove(cVo.getEmp_id());

		mapMem.remove(cVo.getRoom_id());
		mapMem.put(cVo.getRoom_id(), listMem);

		logger.info("^^^^^ 남아 있는 멤버는 {} ^^^^^", listMem);
		
		if(mapMem.get(cVo.getRoom_id()).size() < 1) {
			chatSave(cVo.getRoom_id());
		}
		
		template.convertAndSend("/sub/chatMem/room/" + cVo.getRoom_id(), mapMem.get(cVo.getRoom_id()));
	}

	// 채팅 메시지
	@MessageMapping(value = "/chat/message")
	public void chatMessage(@RequestParam Map<String, String> map) {
		logger.info("@ChatController message() : {}", map);
		logger.info("전체방의 채팅 {}",mapChat);
		
		if (mapChat.get(map.get("room_id")) != null) {
			listChat = mapChat.get(map.get("room_id"));
			logger.info("해당 방의 채팅 있음 {}", listChat);
		} else {
			listChat = new ArrayList<ChatVo>();
			logger.info("해당 방의 채팅 없음 {}", listChat);
		}
		
		String html = "";
		html += "<span class=\"Name\">"+map.get("user_name")+"</span>";
		html += "<span class=\"msg\">"+ map.get("chat_con")+"</span>";

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatedNow = now.format(formatter);
				
		ChatVo vo = new ChatVo(null, map.get("room_id"), map.get("emp_id"), html, formatedNow.toString(), "T");
		listChat.add(vo);
		mapChat.put(map.get("room_id"), listChat);

		logger.info("저장된 채팅 {}", mapChat);

		logger.info("보낼 html {}", vo);
		
		template.convertAndSend("/sub/chat/room/" + map.get("room_id"), vo);
	}

	// 파일 메시지 받아서 저장
	@RequestMapping(value = "/saveFile.do", method = RequestMethod.POST)
	@ResponseBody
	public void saveFile(HttpServletRequest req, MultipartHttpServletRequest multipartRequest) throws FileNotFoundException {
		logger.info("@ChatController fileMessage {}", multipartRequest);

		Date date = new Date();

		List<MultipartFile> file = multipartRequest.getFiles("file");

		String room_id = multipartRequest.getParameter("room_id");
		String username = multipartRequest.getParameter("username");

		logger.info("방 번호는 {} / 유저 아이디는 {}", room_id, username);

		InputStream inputStream = null;
		OutputStream outputStream = null;

		for (int i = 0; i < file.size(); i++) {
			ChatFileVo cFv = new ChatFileVo();

			String fileName = file.get(i).getOriginalFilename();
			System.out.println("파일 네임 " + fileName);

			cFv.setFile_chat_originnm(fileName.substring(0, fileName.lastIndexOf(".")));
			cFv.setFile_chat_type(fileName.substring(fileName.lastIndexOf(".") + 1));
			cFv.setFile_chat_uuid(UUID.randomUUID().toString());
			cFv.setFile_uploadpath(WebUtils.getRealPath(req.getSession().getServletContext(),
					"/chatFile/" + (date.getMonth() + 1) + "/" + date.getDate()));

			try {
				inputStream = file.get(i).getInputStream();

				File storage = new File(cFv.getFile_uploadpath());
				if (!storage.exists()) {
					storage.mkdirs();
				}

				File newFile = new File(
						cFv.getFile_uploadpath() + "/" + cFv.getFile_chat_uuid() + "." + cFv.getFile_chat_type());
				if (newFile.exists()) {
					newFile.createNewFile();
				}

				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] b = new byte[(int) file.get(i).getSize()];

				while ((read = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, read);
				}
				logger.info("저장한 ChatFileVo는 {}", cFv);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//채팅 저장
	public void chatSave(String room_id) {
		logger.info("@ChatController chatSave {}",room_id);
		int cnt = 0;
		
		listChat = mapChat.get(room_id);
		
		if(listChat != null) {
			for(int i = 0; i < listChat.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				ChatVo vo = listChat.get(i);
				
				map.put("room_id", vo.getRoom_id());
				map.put("emp_id", vo.getEmp_id());
				map.put("chat_con", vo.getChat_con());
				map.put("chat_time", vo.getChat_time());
				map.put("chat_type", vo.getChat_type());
				
				cnt += service.insChat(map);
			}
		}
		
		logger.info("저장 횟수는 {}",cnt);
	}
}
