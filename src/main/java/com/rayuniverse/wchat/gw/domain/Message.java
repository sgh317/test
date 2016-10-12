package com.rayuniverse.wchat.gw.domain;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Message {
	
	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * ������Ϣ���ͣ�ͼ��
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�ͼƬ
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * ������Ϣ���ͣ�����λ��
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * �¼����ͣ�subscribe(����)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * �¼����ͣ�unsubscribe(ȡ������)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * �¼����ͣ�CLICK(�Զ���˵�����¼�)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	

	private Map<String,String> messageMap;
	private String messageXml=null;
	
	public Message(HttpServletRequest request) 
	{
		        messageMap=new HashMap();
		        InputStream inputStream =null;
		        try{
		        	inputStream = request.getInputStream();
		        	SAXReader reader = new SAXReader();
					Document document = reader.read(inputStream);
					messageXml=document.asXML();
					Element root = document.getRootElement();
					List<Element> elementList = root.elements();
					for (Element e : elementList)
						messageMap.put(e.getName(), e.getText());
		        	
		        }catch(Throwable e)
		        {
		        	   messageMap=new HashMap();
		        	   messageMap.put("echostr", request.getParameter("echostr"));
		        	   messageMap.put("nonce", request.getParameter("nonce"));
		        	   messageMap.put("signature", request.getParameter("signature"));
		        	   messageMap.put("timestamp", request.getParameter("timestamp"));

		        }finally{
		        	try {
						inputStream.close();
					} catch (Throwable e1) {
					}
		        }
				
	}
	
	public String getMessageKey()
	{
		String MsgType=(String)messageMap.get("MsgType");
		if(REQ_MESSAGE_TYPE_EVENT.equals(MsgType))
		{
			   String eventType = messageMap.get("Event");
			   if(EVENT_TYPE_CLICK.equals(eventType)){
	    			return messageMap.get("EventKey");
	    		}else if(EVENT_TYPE_SUBSCRIBE.equals(eventType)){
	    			return "SYS_SUBSCRIBE";
	    		}else if(EVENT_TYPE_UNSUBSCRIBE.equals(eventType)){
	    			return "SYS_UNSUBSCRIBE";
	    		}
		}
		
		if(REQ_MESSAGE_TYPE_TEXT.equals(MsgType)){
			String content=messageMap.get("Content");
			return content.trim();
		}
		
		return null;
	}

	public Map<String,String> getMessageMap() {
		return messageMap;
	}

	

	public String getMessageXml() {
		return messageXml;
	}


	
	
	
	
}
