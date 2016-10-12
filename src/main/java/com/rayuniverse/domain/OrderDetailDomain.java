package com.rayuniverse.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

public class OrderDetailDomain extends BaseDomain {

	/**
	 * 订单详细
	 */
	private static final long serialVersionUID = 5591840463922610148L;
	
	String  orderId;  //订单号	
	String openId; //微信openId
	String riskName;//险种（主险）
	String riskType;//险种类别
	String sumAmount;//金额总合	
	String payAmount;//应付金额
	String discountAmount;//实际付款金额
	Date paymentTime;//付款时间
	String orderStatus;//订单状态()
	String clientId;//客户号
	String productType;//产品类别（001:车险产品; 002:航意险产品）
	String  score;//抵扣积分
	Date submitTime;//订单提交时间
	Date returnTime;//回销时间
	String agent;//受理人
	String orderType;//订单类别(01:微信支付，02：网点支付，03：上门领取)
	String policyNo;//保单号
	String carSeat;//汽车座椅
	String buyPrice;//购买价格
	String carNo;//车牌号码
	String carClassifyType;//汽车分类
	String loadDate;//登记日期
	String frameNo;//车架号
	String engineNo;//发动机号
	String clientName;//客户姓名
	String clientIdNo;//客户证件号
	String carSeq;//汽车序列号
	Date effectDate;//订单提交时间
	String clientMobile;
	
	public String getClientMobile() {
		return clientMobile;
	}
	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}

	/**责任保障信息明细*/
	List<OrderRiskInfo> orderRiskInfoList;
	
	String startDate;//起始时间  (用于查询)
	String endDate;//结束时间   (用于查询)
	
	
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getCarSeat() {
		return carSeat;
	}
	public void setCarSeat(String carSeat) {
		this.carSeat = carSeat;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarClassifyType() {
		return carClassifyType;
	}
	public void setCarClassifyType(String carClassifyType) {
		this.carClassifyType = carClassifyType;
	}
	public String getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}
	public String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientIdNo() {
		return clientIdNo;
	}
	public void setClientIdNo(String clientIdNo) {
		this.clientIdNo = clientIdNo;
	}
	public String getCarSeq() {
		return carSeq;
	}
	public void setCarSeq(String carSeq) {
		this.carSeq = carSeq;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<OrderRiskInfo> getOrderRiskInfoList() {
		return orderRiskInfoList;
	}
	public void setOrderRiskInfoList(List<OrderRiskInfo> orderRiskInfoList) {
		this.orderRiskInfoList = orderRiskInfoList;
	}
	
	@Override
	public OrderDetailDomain clone(){

		try {
		 ByteArrayOutputStream bo=new ByteArrayOutputStream(); 
		 ObjectOutputStream oo;
			oo = new ObjectOutputStream(bo);
		 oo.writeObject(this); 
		 //从流里读出来 
		 ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray()); 
		 ObjectInputStream oi=new ObjectInputStream(bi); 
		 return (OrderDetailDomain) (oi.readObject()); 

		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
