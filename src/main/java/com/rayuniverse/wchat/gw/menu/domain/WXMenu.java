package com.rayuniverse.wchat.gw.menu.domain;

import java.util.HashMap;
import java.util.Map;

import com.rayuniverse.framework.comm.Util;

public class WXMenu {
	
	private Map menuMap=new HashMap();
	private byte[] data;
	private String flag;
	
	public Map getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(Map menuMap) {
		this.menuMap = menuMap;
		initByMenuMap();
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
		initByData();
	}
	
	public void initByData()
	{
		menuMap=(Map) Util.fromBytes(data);
	}
	
	public void initByMenuMap()
	{
		data=Util.toBytes(menuMap);
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
