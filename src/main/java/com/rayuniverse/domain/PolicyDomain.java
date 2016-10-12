package com.rayuniverse.domain;

import java.util.Date;
import java.util.List;

public class PolicyDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6367712488031955973L;
	
	String carType;//车类别（01，家庭自用车、02，非营业客车、03，营业客车、04，非营业货车、）
	String carTypeDesc;//车类别描述（家庭自用车，非营业客车，营业客车，非营业货车等）
	String seatNum;//座位数
	String loadDate;//初登时间（汽车登记时间 格式 yyyymm）
	String loadYear;//购置年数
	String loadMonth;//购置月数
	String buyPrice;//购置价
	String currentPrice;//目前价格（折旧后价格）
	String address;//详细地址（喊省市区）
	String carProvince;//车牌省
	String carCity;//车牌市
	String netCode;//服务网点
	String isLocalCar;//是否国产车
	Date effectDate;
	
	boolean isDesignatedArea;		// 是否指定区域
	boolean isDesignatedDriver;		// 是否制定驾驶人

    int timesBack3;//2年出险次数
    String amountBack3;//2年前出险金额
    int timesBack2;//1年出险次数
    String amountBack2;//1年前出险金额
    int timesBack1;//上年出险次数
    String amountBack1;//上年出险费用
    
    int businessBack3;
    String businessAmount3;
    int businessBack2;
    String businessAmount2;
    int businessBack;
    String businessAmount;
    
    String sweptVolumn;//排量
    
    String frameNo;
	
	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getSweptVolumn() {
		return sweptVolumn;
	}

	public void setSweptVolumn(String sweptVolumn) {
		this.sweptVolumn = sweptVolumn;
	}

	List<ProductDomain> productList;

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarTypeDesc() {
		return carTypeDesc;
	}

	public void setCarTypeDesc(String carTypeDesc) {
		this.carTypeDesc = carTypeDesc;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public String getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(String loadDate) {
		this.loadDate = loadDate;
	}

	public String getLoadYear() {
		return loadYear;
	}

	public void setLoadYear(String loadYear) {
		this.loadYear = loadYear;
	}

	public String getLoadMonth() {
		return loadMonth;
	}

	public void setLoadMonth(String loadMonth) {
		this.loadMonth = loadMonth;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCarProvince() {
		return carProvince;
	}

	public void setCarProvince(String carProvince) {
		this.carProvince = carProvince;
	}

	public String getCarCity() {
		return carCity;
	}

	public void setCarCity(String carCity) {
		this.carCity = carCity;
	}

	public String getNetCode() {
		return netCode;
	}

	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}

	public String getIsLocalCar() {
		return isLocalCar;
	}

	public void setIsLocalCar(String isLocalCar) {
		this.isLocalCar = isLocalCar;
	}

	public List<ProductDomain> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDomain> productList) {
		this.productList = productList;
	}

	public boolean isDesignatedArea() {
		return isDesignatedArea;
	}

	public void setDesignatedArea(boolean isDesignatedArea) {
		this.isDesignatedArea = isDesignatedArea;
	}

	public boolean isDesignatedDriver() {
		return isDesignatedDriver;
	}

	public void setDesignatedDriver(boolean isDesignatedDriver) {
		this.isDesignatedDriver = isDesignatedDriver;
	}

	public int getTimesBack3() {
		return timesBack3;
	}

	public void setTimesBack3(int timesBack3) {
		this.timesBack3 = timesBack3;
	}

	public String getAmountBack3() {
		return amountBack3;
	}

	public void setAmountBack3(String amountBack3) {
		this.amountBack3 = amountBack3;
	}

	public int getTimesBack2() {
		return timesBack2;
	}

	public void setTimesBack2(int timesBack2) {
		this.timesBack2 = timesBack2;
	}

	public String getAmountBack2() {
		return amountBack2;
	}

	public void setAmountBack2(String amountBack2) {
		this.amountBack2 = amountBack2;
	}

	public int getTimesBack1() {
		return timesBack1;
	}

	public void setTimesBack1(int timesBack1) {
		this.timesBack1 = timesBack1;
	}

	public String getAmountBack1() {
		return amountBack1;
	}

	public void setAmountBack1(String amountBack1) {
		this.amountBack1 = amountBack1;
	}
	
	public String getProductPremByProductCode(String productCode){
		String prem = null;
		
		
		return prem;
	}

	public int getBusinessBack3() {
		return businessBack3;
	}

	public void setBusinessBack3(int businessBack3) {
		this.businessBack3 = businessBack3;
	}

	public String getBusinessAmount3() {
		return businessAmount3;
	}

	public void setBusinessAmount3(String businessAmount3) {
		this.businessAmount3 = businessAmount3;
	}

	public int getBusinessBack2() {
		return businessBack2;
	}

	public void setBusinessBack2(int businessBack2) {
		this.businessBack2 = businessBack2;
	}

	public String getBusinessAmount2() {
		return businessAmount2;
	}

	public void setBusinessAmount2(String businessAmount2) {
		this.businessAmount2 = businessAmount2;
	}

	public int getBusinessBack() {
		return businessBack;
	}

	public void setBusinessBack(int businessBack) {
		this.businessBack = businessBack;
	}

	public String getBusinessAmount() {
		return businessAmount;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public void setBusinessAmount(String businessAmount) {
		this.businessAmount = businessAmount;
	}
	
}
