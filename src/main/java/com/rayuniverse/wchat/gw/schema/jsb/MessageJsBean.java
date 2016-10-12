package com.rayuniverse.wchat.gw.schema.jsb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.user.service.GroupService;
import com.rayuniverse.wchat.gw.schema.service.MessageService;

@Controller
@JsBean(value="MessageJsBean")
public class MessageJsBean {

	@Autowired
	private MessageService messageService;
	
	
	/**
	 * 查询消息列表
	 * @param key
	 * @param type
	 * @param flag
	 * @return
	 */
	public String queryMessageList(String key, String type, String flag)
	{
		ResultObject ro = messageService.getMessageList(key, type, flag);
		
		return JsonUtil.toJson(ro, true);
	}
	
	
	public String addTextMessage(String title, String key, String content)
	{
		ResultObject ro = messageService.addNewTextMsg(title, key, content);
		
		return JsonUtil.toJson(ro, true);
	}
	
	public String getMessageInfoById(String id)
	{
		ResultObject ro = messageService.getMessageInfoById(id);

		return JsonUtil.toJson(ro, true);
	}
	
	public String saveTextMessage(String id, String title, String key, String content)
	{
		ResultObject ro = messageService.modifyTextMsg(id, title, key, content);
		
		return JsonUtil.toJson(ro, true);
	}
	
	public String deleteMessage(String id)
	{
		ResultObject ro = messageService.deleteMessage(id);
		
		return JsonUtil.toJson(ro, true);
	}
	
	public String addNewsMessage(String title, String key, String items)
	{
		ResultObject ro = messageService.addNewNewsMsg(title, key, items);
		
		return JsonUtil.toJson(ro, true);
	}
	
	public String saveNewsMessage(String id, String title, String key, String content)
	{
		ResultObject ro = messageService.modifyNewsMessage(id, title, key, content);
		
		return JsonUtil.toJson(ro, true);
	}
}
