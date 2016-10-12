package com.rayuniverse.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtility 
{
	
	public static Map<String, Object> jsonToMap(JSONObject jo)
	{
		Map<String, Object> root = new HashMap<String, Object>();
		
		if(jo != null && !jo.isEmpty() && !jo.isNullObject() && !jo.isArray())
		{
			Iterator it = jo.keys();
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				Object value = jo.get(key);
				
				if(value instanceof JSONArray)
				{
					root.put(key, jsonToList((JSONArray) value));
				}
				else if(value instanceof JSONObject)
				{
					root.put(key, jsonToMap((JSONObject) value));
				}
				else
				{
					root.put(key, String.valueOf(value));
				}
			}
		}
		
		return root;
	}
	
	
	public static List jsonToList(JSONArray ja)
	{
		List list = new ArrayList();

		if(ja != null && !ja.isEmpty())
		{
			for(int i = 0; i < ja.size(); i++)
			{
				Object o = ja.get(i);
				if(o instanceof JSONArray)
				{
					list.add(jsonToList((JSONArray) o));
				}
				else if (o instanceof JSONObject)
				{
					list.add(jsonToMap((JSONObject) o));
				}
				else
				{
					list.add(o);
				}
			}
		}
		
		return list;
	}
	
	
	public static void main(String[] args) {

		String json = "{\"id\":\"Root\",\"name\":\"ROOT\",\"pId\":\"\",\"pName\":\"\",\"index\":\"1\",\"key\":\"root\",\"type\":\"click\",\"subMenus\":[{\"id\":\"m_1\",\"name\":\"积分中心\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"1\",\"type\":\"click\",\"key\":\"center\",\"subMenus\":[{\"id\":\"m_7\",\"name\":\"查询积分\",\"pId\":\"m_1\",\"pName\":\"积分中心\",\"index\":\"1\",\"type\":\"view\",\"key\":\"query\",\"subMenus\":[]},{\"id\":\"m_9\",\"name\":\"积分规则\",\"pId\":\"m_1\",\"pName\":\"积分中心\",\"index\":\"2\",\"type\":\"view\",\"key\":\"role\",\"subMenus\":[]}]},{\"id\":\"m_3\",\"name\":\"线上保险\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"2\",\"type\":\"view\",\"key\":\"policy\",\"subMenus\":[]},{\"id\":\"m_5\",\"name\":\"服务中心\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"3\",\"type\":\"view\",\"key\":\"service\",\"subMenus\":[]}]}";
//		String json = "{[{\"id\":\"m_1\",\"name\":\"积分中心\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"1\",\"type\":\"click\",\"key\":\"center\",\"subMenus\":[{\"id\":\"m_7\",\"name\":\"查询积分\",\"pId\":\"m_1\",\"pName\":\"积分中心\",\"index\":\"1\",\"type\":\"view\",\"key\":\"query\",\"subMenus\":[]},{\"id\":\"m_9\",\"name\":\"积分规则\",\"pId\":\"m_1\",\"pName\":\"积分中心\",\"index\":\"2\",\"type\":\"view\",\"key\":\"role\",\"subMenus\":[]}]},{\"id\":\"m_3\",\"name\":\"线上保险\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"2\",\"type\":\"view\",\"key\":\"policy\",\"subMenus\":[]},{\"id\":\"m_5\",\"name\":\"服务中心\",\"pId\":\"Root\",\"pName\":\"ROOT\",\"index\":\"3\",\"type\":\"view\",\"key\":\"service\",\"subMenus\":[]}]}";
		JSONObject jo = JSONObject.fromObject(json);
		Object o = jsonToMap(jo);
		System.out.println(o);
	}
}
