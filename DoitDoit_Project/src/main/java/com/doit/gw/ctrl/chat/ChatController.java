package com.doit.gw.ctrl.chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.http.HttpClient.Redirect;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.doit.gw.service.chat.IChatService;
import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatRoomVo;
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
		logger.info("@ChatController enter() : {}", cVo);

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

	// 채팅방 퇴장 했을때(그냥 나감)
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
	public void chatMessage(@RequestParam Map<String, String> map) throws ParseException, IOException {		
		if (mapChat.get(map.get("room_id")) != null) {
			listChat = mapChat.get(map.get("room_id"));
			logger.info("해당 방의 채팅 있음 {}", listChat);
		} else {
			listChat = new ArrayList<ChatVo>();
			logger.info("해당 방의 채팅 없음 {}", listChat);
		}
		
		String html = "";
		if(map.get("type").equals("T")) {
			html += "<span class=\"Name\">"+map.get("user_name")+"</span>";
			html += "<span class=\"msg\">"+ map.get("chat_con")+"</span>";

			map.put("html", html);
			map.put("type", "T");
		}else if(map.get("type").equals("O")) {
			html += "<span class=\"msg\">"+map.get("user_name")+"님이 나가셨습니다</span>";
			map.put("emp_id", "0");
			map.put("html", html);
			map.put("type", "O");
			
			getOut(map.get("room_id"), map.get("emp_id"));
		}
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatedNow = now.format(formatter);
				
		ChatVo vo = new ChatVo(null, map.get("room_id"), map.get("emp_id"), map.get("html"), formatedNow.toString(), map.get("type"));
		listChat.add(vo);
		mapChat.put(map.get("room_id"), listChat);

		logger.info("저장된 채팅 {}", mapChat);
		
		template.convertAndSend("/sub/chat/room/" + map.get("room_id"), vo);
	}
	
	// 파일 메시지 받아서 저장
	@RequestMapping(value = "/saveFile.do", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, String> saveFile(HttpServletRequest req, MultipartHttpServletRequest multipartRequest) throws FileNotFoundException {
		logger.info("@ChatController fileMessage {}", multipartRequest);

		LocalDate date = LocalDate.now();
		
		Map<String, String> map = null;

		List<MultipartFile> file = multipartRequest.getFiles("file");

		String user_name = multipartRequest.getParameter("user_name");

		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		for (int i = 0; i < file.size(); i++) {
			ChatFileVo cFv = new ChatFileVo();

			String fileName = file.get(i).getOriginalFilename();

			cFv.setFile_chat_originnm(fileName.substring(0, fileName.lastIndexOf(".")));
			cFv.setFile_chat_type(fileName.substring(fileName.lastIndexOf(".") + 1));
			cFv.setFile_chat_uuid(UUID.randomUUID().toString());
			cFv.setFile_uploadpath(WebUtils.getRealPath(req.getSession().getServletContext(),
					"/chatFile/" + date.getYear() +"/" + date.getMonthValue() + "/" + date.getDayOfMonth()));

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
				
				map = new HashMap<String, String>();
				
				String html = "";
				html += "<span class=\"Name\">"+user_name+"</span>";
           		html += "<span class=\"msg\">";
           		html +=		"<img src=\"./chatFile/" + date.getYear() +"/" + date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + cFv.getFile_chat_uuid()+"."+cFv.getFile_chat_type()+"\" width=\"200px\">";
            	html += "</span>";
            	
            	map.put("html", html);
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
		
		return map;
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
		
		mapChat.remove(room_id);
		
		logger.info("저장 횟수는 {}",cnt);
	}
	
	//나가는 데이터 update
	@SuppressWarnings("unchecked")
	public void getOut(String room_id, String emp_id) throws ParseException {
		ChatRoomVo vo = service.selGetOut(room_id);
		logger.info("가져온 vo {}",vo.getRoom_mem());
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(vo.getRoom_mem());
		JSONObject json = (JSONObject)obj;
				
		JSONArray jsonRoom = (JSONArray) json.get("ROOM");
		
		logger.info("현재 방의 멤버 {}",jsonRoom);
		for (int i = 0; i < jsonRoom.size(); i++) {
			JSONObject jsonVal = (JSONObject) jsonRoom.get(i);
			String id = (String) jsonVal.get("id");
			
			if(id.equals(emp_id)) {
				jsonRoom.remove(i);
			}
		} 
		logger.info("삭제된 멤버 {}", jsonRoom);
		
		json.clear();
		json.put("ROOM", jsonRoom);
		logger.info("업데이트 할 json / {}", json);
		vo.setRoom_mem(json.toString());
		logger.info("업데이트 vo {}", vo);
		
		service.updGetOut(vo);
	}
}