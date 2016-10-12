package com.rayuniverse.wchat.gw.schema.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.util.JSONUtility;
import com.rayuniverse.wchat.gw.schema.dao.SchemaDao;
import com.rayuniverse.wchat.gw.schema.domain.Schema;

@Service
public class MessageService {
	@Autowired
	private SchemaDao schemaDao;
	
	
	public ResultObject addNewTextMsg(String title,String key,String content)
	{
		// TODO: 是否需要校验key是否存在
		/*if(xxxx != null){
			ResultObject ro=new ResultObject();
			ro.setState(false);
			ro.setErrorMessage("消息[key:"+key+"]已经存在");
			return ro;
		}*/

		Map msg = new HashMap<String, String>();
		msg.put("title", title);
		msg.put("key", key);
		msg.put("content", content);
		msg.put("type", Schema.MSG_TYPE_TEXT);
		
		Schema sca = new Schema();
		sca.setId(UUID.randomUUID().toString());
		sca.setType(Schema.MSG_TYPE_TEXT);
		sca.setMessageMap(msg);
		
		schemaDao.addSchemaInfo(sca);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(sca);
		return ro;
	}
	
	public ResultObject addNewNewsMsg(String title, String key, String items)
	{
		Map msg = new HashMap<String, String>();
		msg.put("title", title);
		msg.put("key", key);
		msg.put("type", Schema.MSG_TYPE_IMG);
		
		JSONObject obj = JSONObject.fromObject(items);
		Map map = JSONUtility.jsonToMap(obj);

		List list = new ArrayList();
		msg.put("items", map.get("newsItem"));
		
		/*if(items != null && !"".equals(items))
		{
			System.out.println(items);
			String[] itemList = items.split(";");
			for (String str : itemList) 
			{
				if(str != null && !"".equals(str))
				{
					String[] s = str.split(",");
					if(s != null && s.length > 0)
					{
						Map map = new HashMap<String, String>();
						for (String kv : s) 
						{
							String[] arr = kv.split("=");
							map.put(arr[0], arr[1]);
						}
						list.add(map);
					}
				}
			}
		}*/
		
		Schema sca = new Schema();
		sca.setId(UUID.randomUUID().toString());
		sca.setType(Schema.MSG_TYPE_IMG);
		sca.setMessageMap(msg);
		
		schemaDao.addSchemaInfo(sca);
		
		ResultObject ro=new ResultObject();
		ro.setState(true);
		ro.setValue(sca);
		return ro;
	}
	
	
	public ResultObject modifyNewsMessage(String id, String title, String key, String content)
	{
		ResultObject ro=new ResultObject();
		Schema sca = schemaDao.getMessageInfoById(id);
		if(sca == null)
		{
			ro.setState(false);
			ro.setErrorMessage("消息不存在，无法修改");
			return ro;
		}
		
		Map map = sca.getMessageMap();
		map.put("title", title);
		map.put("key", key);
		map.put("type", sca.getType());
		JSONObject obj = JSONObject.fromObject(content);
		Map m = JSONUtility.jsonToMap(obj);
		List list = new ArrayList();
		map.put("items", m.get("newsItem"));
		
		sca.initByMessageMap();
		
		schemaDao.updateSchemaInfo(sca);
		ro.setState(true);
		ro.setValue(sca);
		
		return ro;
	}
	
	
	public ResultObject getMessageList(String key, String type, String flag) 
	{
		ResultObject ro=new ResultObject();
		List<Schema> list = schemaDao.getSchemaList(type, flag);
		ro.setState(true);
		ro.setValue(list);
		return ro;
	}
	

	public ResultObject getMessageInfoById(String id)
	{
		ResultObject ro=new ResultObject();
		Schema sca = schemaDao.getMessageInfoById(id);
		if(sca == null)
		{
			ro.setState(false);
			ro.setErrorMessage("无此消息数据");
			return ro;
		}
		ro.setValue(sca);
		ro.setState(true);
		
		return ro;
	}
	
	public ResultObject modifyTextMsg(String id, String title, String key, String content)
	{
		ResultObject ro=new ResultObject();
		Schema sca = schemaDao.getMessageInfoById(id);
		if(sca == null)
		{
			ro.setState(false);
			ro.setErrorMessage("消息不存在，无法修改");
			return ro;
		}
		
		Map map = sca.getMessageMap();
		map.put("title", title);
		map.put("key", key);
		map.put("content", content);
		map.put("type", sca.getType());
		sca.initByMessageMap();
		
		schemaDao.updateSchemaInfo(sca);
		ro.setState(true);
		ro.setValue(sca);
		
		return ro;
	}
	
	public ResultObject deleteMessage(String id)
	{
		ResultObject ro=new ResultObject();
		
		schemaDao.deleteSchemaById(id);
		ro.setState(true);
		
		return ro;
	}
	
}
