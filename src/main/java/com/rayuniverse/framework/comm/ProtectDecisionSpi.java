package com.rayuniverse.framework.comm;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @author liuhaidong
 * 
 */
public interface ProtectDecisionSpi {
	
	public boolean isProtect(HttpServletRequest request);
}
