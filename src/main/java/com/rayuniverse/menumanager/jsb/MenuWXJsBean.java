package com.rayuniverse.menumanager.jsb;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;
import com.rayuniverse.util.JSONUtility;
import com.rayuniverse.wchat.gw.menu.domain.WXMenu;
import com.rayuniverse.wchat.gw.menu.service.WXMenuService;

@Controller
@JsBean(value="MenuWXJsBean")
public class MenuWXJsBean {

	@Autowired
	private WXMenuService wxMenuService;
	
	
	/**
	 * 获取目录列表
	 * @return
	 */
	public String queryWXMenu()
	{
		ResultObject ro = wxMenuService.getWXMenu();
		
		return JsonUtil.toJson(ro, true);
	}
	
	/**
	 * 保存目录列表
	 * @param menuJson
	 */
	public void saveWXMenu(String menuJson)
	{
		/*ObjectMapper om = new ObjectMapper();
		WXMenu wxm = new WXMenu();
		Map<String, Object> m = om.readValue(menuJson);*/

		System.out.println("menuJson:"+menuJson);
		WXMenu wxm = new WXMenu();
		Map<String, Object> m = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(menuJson);
		wxm.setFlag("1");
		wxm.setMenuMap(JSONUtility.jsonToMap(jsonObject));
		
		wxMenuService.saveWXMenu(wxm);
	}
	
	/**
	 * 部署到微信
	 */
	public void deployWXMenu()
	{
		wxMenuService.deployWXMenu();
	}
}
