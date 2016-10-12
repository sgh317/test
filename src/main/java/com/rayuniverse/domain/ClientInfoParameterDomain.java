package com.rayuniverse.domain;

public class ClientInfoParameterDomain extends BaseDomain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7665210725465680529L;

	String openId;//¿Í»§openId
	
	String parameterKey;
	
	String parameterValue;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getParameterKey() {
		return parameterKey;
	}

	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	
	

}
