package com.rayuniverse.domain;

public class AdDomain extends BaseDomain{
	private static final long serialVersionUID = 3200439034141038317L;
	
	public String adId;
	public String adPicture;
	public String adLocation;
	public String adHref;
	public String adStatus;
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdPicture() {
		return adPicture;
	}
	public void setAdPicture(String adPicture) {
		this.adPicture = adPicture;
	}
	public String getAdLocation() {
		return adLocation;
	}
	public void setAdLocation(String adLocation) {
		this.adLocation = adLocation;
	}
	public String getAdHref() {
		return adHref;
	}
	public void setAdHref(String adHref) {
		this.adHref = adHref;
	}
	public String getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

}
