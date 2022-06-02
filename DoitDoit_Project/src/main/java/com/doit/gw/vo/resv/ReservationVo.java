package com.doit.gw.vo.resv;

import java.io.Serializable;

public class ReservationVo implements Serializable {

	private static final long serialVersionUID = 7221595323689017924L;
	
	private String RESV_ID;
	private String EMP_ID;
	private String RESV_TITLE;
	private String RESV_WRITER;
	private String RESV_DESCRIPTION;
	private String RESV_START;
	private String RESV_END;
	private String RESV_ROOM_ID;
	private String RESV_ROOM_TITLE;
	private String RESV_ROOM_EVENTCOLOR;
	
	
	public ReservationVo() {
	}

	public String getRESV_ID() {
		return RESV_ID;
	}

	public void setRESV_ID(String rESV_ID) {
		RESV_ID = rESV_ID;
	}

	public String getEMP_ID() {
		return EMP_ID;
	}

	public void setEMP_ID(String eMP_ID) {
		EMP_ID = eMP_ID;
	}

	public String getRESV_TITLE() {
		return RESV_TITLE;
	}

	public void setRESV_TITLE(String rESV_TITLE) {
		RESV_TITLE = rESV_TITLE;
	}

	public String getRESV_WRITER() {
		return RESV_WRITER;
	}

	public void setRESV_WRITER(String rESV_WRITER) {
		RESV_WRITER = rESV_WRITER;
	}

	public String getRESV_DESCRIPTION() {
		return RESV_DESCRIPTION;
	}

	public void setRESV_DESCRIPTION(String rESV_DESCRIPTION) {
		RESV_DESCRIPTION = rESV_DESCRIPTION;
	}

	public String getRESV_START() {
		return RESV_START;
	}

	public void setRESV_START(String rESV_START) {
		RESV_START = rESV_START;
	}

	public String getRESV_END() {
		return RESV_END;
	}

	public void setRESV_END(String rESV_END) {
		RESV_END = rESV_END;
	}

	public String getRESV_ROOM_ID() {
		return RESV_ROOM_ID;
	}

	public void setRESV_ROOM_ID(String rESV_ROOM_ID) {
		RESV_ROOM_ID = rESV_ROOM_ID;
	}

	public String getRESV_ROOM_TITLE() {
		return RESV_ROOM_TITLE;
	}

	public void setRESV_ROOM_TITLE(String rESV_ROOM_TITLE) {
		RESV_ROOM_TITLE = rESV_ROOM_TITLE;
	}

	public String getRESV_ROOM_EVENTCOLOR() {
		return RESV_ROOM_EVENTCOLOR;
	}

	public void setRESV_ROOM_EVENTCOLOR(String rESV_ROOM_EVENTCOLOR) {
		RESV_ROOM_EVENTCOLOR = rESV_ROOM_EVENTCOLOR;
	}

	@Override
	public String toString() {
		return "ReservationVo [RESV_ID=" + RESV_ID + ", EMP_ID=" + EMP_ID + ", RESV_TITLE=" + RESV_TITLE
				+ ", RESV_WRITER=" + RESV_WRITER + ", RESV_DESCRIPTION=" + RESV_DESCRIPTION + ", RESV_START="
				+ RESV_START + ", RESV_END=" + RESV_END + ", RESV_ROOM_ID=" + RESV_ROOM_ID + ", RESV_ROOM_TITLE="
				+ RESV_ROOM_TITLE + ", RESV_ROOM_EVENTCOLOR=" + RESV_ROOM_EVENTCOLOR + "]";
	}
	
	

}
