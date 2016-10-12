package com.rayuniverse.framework.login;

import com.rayuniverse.framework.PlatformContext;





public class Principal implements java.security.Principal{

	 
	public String getName() {
		return  PlatformContext.getUmUserId();
	}

}
