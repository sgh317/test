package com.rayuniverse.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class CustomerDetailDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5591840463922610147L;
	
	String clientId;//客户编号
	String clientName;//客户姓名
	String openId;//微信openId
	String carNo;//车牌号
	String mobileNo;//电话号码
	String followTimeStr;//绑定时间String
	Date   followTime;//绑定时间
	String givenType;//赠送类型（话费，流量，赠险）
	String importTimeStr;//导入时间String
	Date   importTime;//导入事假
	String givenTimeStr;//领取赠送物品时间String
	Date   givenTime;//领取赠送物品时间
	String clientIntegral;//客户积分
	
	String clientIdNo;
	
	String carType;//汽车类别
    String frameNo;//车架号
    String engineNo;//发动机号
    
    String loadDate;//初登时间
    String policyEnd;//保单终止时间
    
    int transTimesBack3;//2年交强险出险次数
    String transAmountBack3;//2年前交强险出险金额
    int businessTimesBack3;//2年前商业险出险次数
    String businessAmountBack3;//2年前商业险出险金额
    
    int transTimesBack2;//1年交强险出险次数
    String transAmountBack2;//1年前交强险出险金额
    int businessTimesBack2;//1年前商业险出险次数
    String businessAmountBack2;//1年前商业险出险金额
    
    int transTimes;//上年交强险出险次数
    String transAmount;//上年交强险出险费用
    int businessTimes;//上年商业险出险次数
    String businessAmount;//上年商业险出险费用
    
    String clientAddress;//客户地址
    
    String carSeq;//车型序号
	
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFollowTimeStr() {
		return followTimeStr;
	}
	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}
	public Date getFollowTime() {
		return followTime;
	}
	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}
	public String getGivenType() {
		return givenType;
	}
	public void setGivenType(String givenType) {
		this.givenType = givenType;
	}
	public String getImportTimeStr() {
		return importTimeStr;
	}
	public void setImportTimeStr(String importTimeStr) {
		this.importTimeStr = importTimeStr;
	}
	public Date getImportTime() {
		return importTime;
	}
	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	public String getGivenTimeStr() {
		return givenTimeStr;
	}
	public void setGivenTimeStr(String givenTimeStr) {
		this.givenTimeStr = givenTimeStr;
	}
	public Date getGivenTime() {
		return givenTime;
	}
	public void setGivenTime(Date givenTime) {
		this.givenTime = givenTime;
	}

	public String getClientIntegral() {
		return clientIntegral;
	}
	public void setClientIntegral(String clientIntegral) {
		this.clientIntegral = clientIntegral;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
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
	public String getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}
	public String getPolicyEnd() {
		return policyEnd;
	}
	public void setPolicyEnd(String policyEnd) {
		this.policyEnd = policyEnd;
	}
	public int getTransTimes() {
		return transTimes;
	}
	public void setTransTimes(int transTimes) {
		this.transTimes = transTimes;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public int getBusinessTimes() {
		return businessTimes;
	}
	public void setBusinessTimes(int businessTimes) {
		this.businessTimes = businessTimes;
	}
	public String getBusinessAmount() {
		return businessAmount;
	}
	public void setBusinessAmount(String businessAmount) {
		this.businessAmount = businessAmount;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getClientIdNo() {
		return clientIdNo;
	}
	public void setClientIdNo(String clientIdNo) {
		this.clientIdNo = clientIdNo;
	}
	public int getTransTimesBack3() {
		return transTimesBack3;
	}
	public void setTransTimesBack3(int transTimesBack3) {
		this.transTimesBack3 = transTimesBack3;
	}
	public String getTransAmountBack3() {
		return transAmountBack3;
	}
	public void setTransAmountBack3(String transAmountBack3) {
		this.transAmountBack3 = transAmountBack3;
	}
	public int getBusinessTimesBack3() {
		return businessTimesBack3;
	}
	public void setBusinessTimesBack3(int businessTimesBack3) {
		this.businessTimesBack3 = businessTimesBack3;
	}
	public String getBusinessAmountBack3() {
		return businessAmountBack3;
	}
	public void setBusinessAmountBack3(String businessAmountBack3) {
		this.businessAmountBack3 = businessAmountBack3;
	}
	public int getTransTimesBack2() {
		return transTimesBack2;
	}
	public void setTransTimesBack2(int transTimesBack2) {
		this.transTimesBack2 = transTimesBack2;
	}
	public String getTransAmountBack2() {
		return transAmountBack2;
	}
	public void setTransAmountBack2(String transAmountBack2) {
		this.transAmountBack2 = transAmountBack2;
	}
	public int getBusinessTimesBack2() {
		return businessTimesBack2;
	}
	public void setBusinessTimesBack2(int businessTimesBack2) {
		this.businessTimesBack2 = businessTimesBack2;
	}
	public String getBusinessAmountBack2() {
		return businessAmountBack2;
	}
	public void setBusinessAmountBack2(String businessAmountBack2) {
		this.businessAmountBack2 = businessAmountBack2;
	}
	public String getCarSeq() {
		return carSeq;
	}
	public void setCarSeq(String carSeq) {
		this.carSeq = carSeq;
	}
	
	@Override
	public CustomerDetailDomain clone(){

		try {
		 ByteArrayOutputStream bo=new ByteArrayOutputStream(); 
		 ObjectOutputStream oo;
			oo = new ObjectOutputStream(bo);
		 oo.writeObject(this); 
		 //从流里读出来 
		 ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray()); 
		 ObjectInputStream oi=new ObjectInputStream(bi); 
		 return (CustomerDetailDomain) (oi.readObject()); 

		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
}
