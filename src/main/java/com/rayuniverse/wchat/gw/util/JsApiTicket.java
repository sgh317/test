package com.rayuniverse.wchat.gw.util;

public class JsApiTicket {

	    private String ticket;
	    // 凭证有效时间，单位：�?
		private int expiresIn;
		public String getTicket() {
			return ticket;
		}
		public void setTicket(String ticket) {
			this.ticket = ticket;
		}
		public int getExpiresIn() {
			return expiresIn;
		}
		public void setExpiresIn(int expiresIn) {
			this.expiresIn = expiresIn;
		}
		
	
}
