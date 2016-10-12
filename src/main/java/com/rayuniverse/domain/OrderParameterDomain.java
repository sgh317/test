package com.rayuniverse.domain;

public class OrderParameterDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393441012868198600L;
	
	public String orderId;
	public String parameterKey;
	public String parameterValue;
	public String keyValue;
	public String parameterKeyDesc;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getParameterKeyDesc() {
		return parameterKeyDesc;
	}
	public void setParameterKeyDesc(String parameterKeyDesc) {
		this.parameterKeyDesc = parameterKeyDesc;
	}
}
