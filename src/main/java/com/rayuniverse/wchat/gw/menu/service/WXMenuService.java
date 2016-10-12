package com.rayuniverse.wchat.gw.menu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.comm.Util;
import com.rayuniverse.wchat.gw.cfg.ReloadableWXConfigResource;
import com.rayuniverse.wchat.gw.menu.dao.WXMenuDao;
import com.rayuniverse.wchat.gw.menu.domain.WXMenu;

@Service
public class WXMenuService {
	private static Logger log = LoggerFactory.getLogger(WXMenuService.class);
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	@Autowired
	private ReloadableWXConfigResource wxconfig;
	@Autowired
	private WXMenuDao wxmenuDao;
	
	
	private Map getMenuMap(Map map)
	{
		Map m = null;
		if(map != null && map.size() > 0)
		{
			m = new HashMap();
			m.put("name", map.get("name"));
			m.put("type", map.get("type"));
			m.put("url", map.get("key"));
			List l = null;
			List list = (List) map.get("subMenus");
			if(list != null && list.size() > 0)
			{
				l = new ArrayList();
				m.put("sub_button", l);
				for (Object o : list) {
					Map mm = getMenuMap((Map) o);
					if(mm != null)
					{
						l.add(mm);
					}
				}
			}
		}
		return m;
	}
	
	public String deployWXMenu()
	{
		WXMenu menu=wxmenuDao.getWXMenu();
		if(menu==null)
		{
			return "部署失败-";
		}
		
		Map<String, Object> menuMap = new HashMap<String, Object>();
		Map map= menu.getMenuMap();
		List list = null;
		if(map != null && map.size() > 0)
		{
			list = new ArrayList();
			menuMap.put("button", list);
			List l = (List) map.get("subMenus");
			for (Object object : l) {
				list.add(getMenuMap((Map) object));
			}
		}
		
//		String jsonMenu = JSONObject.fromObject(menu.getMenuMap()).toString();
		String jsonMenu = JSONObject.fromObject(menuMap).toString();   
		
		String url = menu_create_url.replace("ACCESS_TOKEN", wxconfig.getResource().getAccesstoken().getValue());
		
		JSONObject jsonObject = Util.httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (!"0".equals( jsonObject.getString("errcode"))) {
				
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				return "创建菜单失败";
			}
		}
		return null;
	}
	
	
	public void addWXMenu(WXMenu wxMenu)
	{
		wxmenuDao.insertWXMenu(wxMenu);
	}
	
	public void modifyWXMenu(WXMenu wxMenu)
	{
		wxmenuDao.updateWXMenu(wxMenu);
	}
	
	public void saveWXMenu(WXMenu wxm)
	{
		WXMenu wxMenu = wxmenuDao.getWXMenu();
		if(wxMenu != null)
		{
			wxmenuDao.updateWXMenu(wxm);
		}
		else
		{
			wxmenuDao.insertWXMenu(wxm);
		}
	}

	public ResultObject getWXMenu()
	{
		ResultObject ro = new ResultObject();
		ro.setState(false);
		
		WXMenu wxMenu = wxmenuDao.getWXMenu();
		if(wxMenu != null)
		{
			ro.setState(true);
			ro.setValue(wxMenu);
		}
		
		return ro;
	}
}
