package com.rayuniverse.domain;

public class NetDomain extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3075965707302441094L;
	
	public String netCode;
	public String netName;
	public String areaCode;
	public String netAddress;
	public String getNetCode() {
		return netCode;
	}
	public void setNetCode(String netCode) {
		this.netCode = netCode;
	}
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getNetAddress() {
		return netAddress;
	}
	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}
}
