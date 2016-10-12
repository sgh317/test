package com.rayuniverse.domain;

public class PolicyCalDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5817320906476866965L;
	
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
	
	boolean isDesignatedArea;		// 是否指定区域
	boolean isDesignatedDriver;		// 是否制定驾驶人

    int timesBack3;//2年出险次数
    String amountBack3;//2年前出险金额
    int timesBack2;//1年出险次数
    String amountBack2;//1年前出险金额
    int timesBack1;//上年出险次数
    String amountBack1;//上年出险费用
    
    ProductDomain product100001;
    ProductDomain product100002;
    ProductDomain product100003;
    ProductDomain product100004;
    ProductDomain product100005;
    ProductDomain product100006;
    ProductDomain product100007;
    ProductDomain product100008;
    ProductDomain product100009;
    ProductDomain product100010;
    ProductDomain product100011;
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
	public ProductDomain getProduct100001() {
		return product100001;
	}
	public void setProduct100001(ProductDomain product100001) {
		this.product100001 = product100001;
	}
	public ProductDomain getProduct100002() {
		return product100002;
	}
	public void setProduct100002(ProductDomain product100002) {
		this.product100002 = product100002;
	}
	public ProductDomain getProduct100003() {
		return product100003;
	}
	public void setProduct100003(ProductDomain product100003) {
		this.product100003 = product100003;
	}
	public ProductDomain getProduct100004() {
		return product100004;
	}
	public void setProduct100004(ProductDomain product100004) {
		this.product100004 = product100004;
	}
	public ProductDomain getProduct100005() {
		return product100005;
	}
	public void setProduct100005(ProductDomain product100005) {
		this.product100005 = product100005;
	}
	public ProductDomain getProduct100006() {
		return product100006;
	}
	public void setProduct100006(ProductDomain product100006) {
		this.product100006 = product100006;
	}
	public ProductDomain getProduct100007() {
		return product100007;
	}
	public void setProduct100007(ProductDomain product100007) {
		this.product100007 = product100007;
	}
	public ProductDomain getProduct100008() {
		return product100008;
	}
	public void setProduct100008(ProductDomain product100008) {
		this.product100008 = product100008;
	}
	public ProductDomain getProduct100009() {
		return product100009;
	}
	public void setProduct100009(ProductDomain product100009) {
		this.product100009 = product100009;
	}
	public ProductDomain getProduct100010() {
		return product100010;
	}
	public void setProduct100010(ProductDomain product100010) {
		this.product100010 = product100010;
	}
	public ProductDomain getProduct100011() {
		return product100011;
	}
	public void setProduct100011(ProductDomain product100011) {
		this.product100011 = product100011;
	}
}
