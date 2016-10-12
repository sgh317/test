package com.rayuniverse.domain;

public class CarTypeListDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 80369095231802815L;
	
	String remark;//备注
	String loadDate;//销售日期
	String buyPrice;//购置价
	String sweptVolume;//冬丽
	String carNum;//作为书
	String carName;//车名
	String carSearial;//车系列
	String carCategory;//车品牌
	String carType;//车类别
	String carSeq;//车种类序号
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLoadDate() {
		return loadDate;
	}
	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getSweptVolume() {
		return sweptVolume;
	}
	public void setSweptVolume(String sweptVolume) {
		this.sweptVolume = sweptVolume;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarSearial() {
		return carSearial;
	}
	public void setCarSearial(String carSearial) {
		this.carSearial = carSearial;
	}
	public String getCarCategory() {
		return carCategory;
	}
	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarSeq() {
		return carSeq;
	}
	public void setCarSeq(String carSeq) {
		this.carSeq = carSeq;
	}

}
