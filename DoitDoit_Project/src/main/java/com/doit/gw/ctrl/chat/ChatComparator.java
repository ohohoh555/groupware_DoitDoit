package com.doit.gw.ctrl.chat;

import java.util.Comparator;

import com.doit.gw.vo.chat.ChatJoinVo;

public class ChatComparator implements Comparator<ChatJoinVo>{
	@Override
	public int compare(ChatJoinVo o1, ChatJoinVo o2) {
		if (lngParse(o1.getChat_time()) > lngParse(o2.getChat_time())) {
            return -1;
        } else if (lngParse(o1.getChat_time()) < lngParse(o2.getChat_time())) {
            return 1;
        }
        return 0;
	}
	
	public Long lngParse(String str) {
		str = str.replaceAll(":", "");
		str = str.replaceAll("-", "");
		str = str.replaceAll(" ", "");
		
		Long lng = Long.parseLong(str); 
		
		return lng;
	}
}
