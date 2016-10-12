package com.rayuniverse.wchat.gw.asyn;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.wchat.gw.cfg.ConfigItem;
import com.rayuniverse.wchat.gw.cfg.WechatSign;


@Controller
@JsBean(value = "WxImageJsBean")
public class WxImageJsBean {
	private static final Logger logger = LoggerFactory.getLogger(WxImageJsBean.class);
	@Autowired
	protected WXConfigService cfg;
	
	public HashMap getWXConfigData(String url)
	{
		
		HashMap map=cfg.getJsApiTicket();
		ConfigItem item=new ConfigItem();
		item.init(map);
		if(item.isValidate()==false)
		{
			 map=cfg.getJsApiTicket();
		}
		String ticket=item.getValue();
		HashMap<String, String> sign=WechatSign.sign(ticket, url);
		sign.put("appId", (String) map.get("appId"));
		
		return sign;
	}
	
	
	public ArrayList<HashMap> uploadClaimImages(ArrayList<HashMap> arrayList)
	{
		
		HashMap map=cfg.getAccessToken();
		ConfigItem item=new ConfigItem();
		item.init(map);
		if(item.isValidate()==false)
		{
			 map=cfg.getAccessToken();
		}
		String token=item.getValue();
		
		ArrayList<HashMap> list=new ArrayList();
		for(HashMap img:arrayList)
		{
			HashMap url=new HashMap();
			url.put("img", img);
			url.put("url", "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+img.get("serverId"));
			list.add(url);
			
		}
		return list;
	}
	
	
	

}
