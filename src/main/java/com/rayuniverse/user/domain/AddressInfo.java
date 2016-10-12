package com.rayuniverse.user.domain;

public class AddressInfo {
	
	private String Addressid;   
	private String userId;   
	private String dename; 
	private String province; 
	private String city;  
	private String area;
	private String addressdetail;  
	private String mobile;
	private String defaultaddr;  
	
	public String getAddressid() {
		return Addressid;
	}
	public void setAddressid(String addressid) {
		Addressid = addressid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDename() {
		return dename;
	}
	public void setDename(String dename) {
		this.dename = dename;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddressdetail() {
		return addressdetail;
	}
	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDefaultaddr() {
		return defaultaddr;
	}
	public void setDefaultaddr(String defaultaddr) {
		this.defaultaddr = defaultaddr;
	}
}
