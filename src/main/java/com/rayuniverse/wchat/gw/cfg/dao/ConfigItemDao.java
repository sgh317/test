package com.rayuniverse.wchat.gw.cfg.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.wchat.gw.cfg.ConfigItem;
import com.rayuniverse.wchat.gw.cfg.WXConfig;

@Repository
public class ConfigItemDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public WXConfig selectTokenTicket(){
		WXConfig config=new WXConfig();
		Map m=(Map) sqlSessionTemplate.selectList("selectTokenTicket").get(0);
		ConfigItem accesstoken=new ConfigItem();
		Timestamp  ts=(Timestamp) m.get("LIFE_END");
		accesstoken.setLifeEndTime(ts.getTime());
		accesstoken.setValue(m.get("ACCESSTOKEN")+"");
		config.setAccesstoken(accesstoken);
		
		ConfigItem JSAPI_TICKET=new ConfigItem();
		JSAPI_TICKET.setLifeEndTime(ts.getTime());
		JSAPI_TICKET.setValue(m.get("JSAPI_TICKET")+"");
		config.setJsapiTicket(JSAPI_TICKET);
		
		return config;
	}
	

	public void updateTokenTicket(String token,String ticket)
	{
		HashMap p=new HashMap();
		p.put("token", token);
		p.put("ticket", ticket);
		sqlSessionTemplate.update("updateTokenTicket", p);
	}
	
	
}
