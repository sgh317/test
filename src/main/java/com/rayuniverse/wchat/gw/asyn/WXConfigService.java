package com.rayuniverse.wchat.gw.asyn;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.wchat.gw.cfg.ConfigItem;
import com.rayuniverse.wchat.gw.cfg.ReloadableWXConfigResource;

@Service
public class WXConfigService {
	 private static final Logger logger = LoggerFactory.getLogger(WXConfigService.class);
	@Autowired
	protected ReloadableWXConfigResource rs;
	
	public HashMap getAccessToken()
	{
		ConfigItem item=rs.getResource().getAccesstoken();
		return item.toMap();
		
	}
	
	public HashMap getJsApiTicket()
	{
		ConfigItem item=rs.getResource().getJsapiTicket();
	    HashMap map=new HashMap();
	    map.put("value", item.getValue());
	    map.put("life", item.getLifeEndTime());
		String appId = PlatformContext.getConfigItem("WEIXIN_APPID");
		String appSecret =  PlatformContext.getConfigItem("WEIXIN_APPSECRET");
	    map.put("appId", appId);
	    map.put("appSecret", appSecret);
	    
		return map;
	}
	
	
}
