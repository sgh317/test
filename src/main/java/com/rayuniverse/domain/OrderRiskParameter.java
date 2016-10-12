package com.rayuniverse.domain;

public class OrderRiskParameter extends BaseDomain{

	/**
	 * 订单险种附加详细信息
	 */
	private static final long serialVersionUID = 5591840463922610188L;
	
	String orderId;  //订单号	
	String productSeq; //产品序列号
	String parameterKey;//
	String parameterKeyDesc;//中文描述
	String keyValue;//
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductSeq() {
		return productSeq;
	}
	public void setProductSeq(String productSeq) {
		this.productSeq = productSeq;
	}
	public String getParameterKey() {
		return parameterKey;
	}
	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}
	public String getParameterKeyDesc() {
		return parameterKeyDesc;
	}
	public void setParameterKeyDesc(String parameterKeyDesc) {
		this.parameterKeyDesc = parameterKeyDesc;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
