package com.rayuniverse.wchat.gw.cfg;

import java.util.HashMap;

import com.rayuniverse.framework.PlatformContext;


public class ConfigItem {
	long lifeEndTime;
	String value;
	
	public boolean isValidate()
	{
		return (lifeEndTime-System.currentTimeMillis())>10;
	}
	
	public long getLifeEndTime() {
		return lifeEndTime;
	}
	public void setLifeEndTime(long lifeEndTime) {
		this.lifeEndTime = lifeEndTime;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void init(HashMap m)
	{
		lifeEndTime=(Long) m.get("life");
		value=(String) m.get("value");
	}
	
	public HashMap toMap()
	{
		    HashMap map=new HashMap();
		    map.put("value", getValue());
		    map.put("life", getLifeEndTime());
		    String appId = PlatformContext.getConfigItem("WEIXIN_APPID");
			String appSecret =PlatformContext.getConfigItem("WEIXIN_APPSECRET");
		    map.put("appId", appId);
		    map.put("appSecret", appSecret);
			return map;
	}
	

}
