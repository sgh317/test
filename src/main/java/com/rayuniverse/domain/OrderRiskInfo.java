package com.rayuniverse.domain;

import java.util.Date;
import java.util.List;

public class OrderRiskInfo extends BaseDomain{

	
	/**
	 * 璁㈠崟闄╃淇℃伅
	 */
	private static final long serialVersionUID = 5591840463922610198L;
	
	String orderId;  //璁㈠崟鍙�
	String productSeq; //浜у搧搴忓垪鍙�
	String productCode;//浜у搧CODE
	String productName;//浜у搧鍚嶇О
	String productPrem;//浜у搧淇濊垂
	String productAmount;//浜у搧淇濋
	
	String productConversionPrem;//产品折算保费
	
	/**闄╃闄勫姞璇︾粏淇℃伅*/
	List<OrderRiskParameter> orderRiskParameterList;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductConversionPrem() {
		return productConversionPrem;
	}
	public void setProductConversionPrem(String productConversionPrem) {
		this.productConversionPrem = productConversionPrem;
	}
	public String getProductSeq() {
		return productSeq;
	}
	public void setProductSeq(String productSeq) {
		this.productSeq = productSeq;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}		
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrem() {
		return productPrem;
	}
	public void setProductPrem(String productPrem) {
		this.productPrem = productPrem;
	}
	public String getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<OrderRiskParameter> getOrderRiskParameterList() {
		return orderRiskParameterList;
	}
	public void setOrderRiskParameterList(
			List<OrderRiskParameter> orderRiskParameterList) {
		this.orderRiskParameterList = orderRiskParameterList;
	}
}
