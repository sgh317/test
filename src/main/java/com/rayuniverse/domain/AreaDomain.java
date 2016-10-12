package com.rayuniverse.domain;

public class AreaDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5200439034141038397L;
	
	public String cityCode;
	public String areaCode;
	public String areaDesc;
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
}
