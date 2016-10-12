package com.rayuniverse.wchat.gw.schema.domain;

import java.util.HashMap;
import java.util.Map;

import com.rayuniverse.framework.comm.Util;

public class Schema {
	private String id;
	private String type;	// 消息类型[text-文本消息；news-图文消息]
	private String flag;
	private byte[] data;
	private Map messageMap=new HashMap();
	
	public void initByData()
	{
		messageMap=(Map) Util.fromBytes(data);
	}
	
	public void initByMessageMap()
	{
		data=Util.toBytes(messageMap);
	}

	public Map  getMessageMap() {
		return messageMap;
	}
	public String getTitle()
	{
		return (String) messageMap.get("title");
	}
	public String getKey() {
		return (String) messageMap.get("key");
	}
	public String getMsgContent() {
		return (String) messageMap.get("content");
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
		initByData();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		this.messageMap.put("type", type);
		initByMessageMap();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setMessageMap(Map messageMap) {
		this.messageMap = messageMap;
		initByMessageMap();
	}

	
	public static final String MSG_TYPE_TEXT = "text";
	public static final String MSG_TYPE_IMG	 = "news";
}
