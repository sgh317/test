package com.rayuniverse.user.domain;

public class MemberInfo {
	private String openId;   
	private String userId;   
	private String nickName; 
	private String userType; 
	private String userPic;  
	private String userMoney;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public String getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(String userMoney) {
		this.userMoney = userMoney;
	}
}
