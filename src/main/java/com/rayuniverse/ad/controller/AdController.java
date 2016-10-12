package com.rayuniverse.ad.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayuniverse.ad.service.AdService;
import com.rayuniverse.domain.AdDomain;
import com.rayuniverse.order.service.OrderService;

import net.sf.json.JSONObject;



@Controller
public class AdController {
	
	@Autowired
	AdService adService;
	@Autowired
	OrderService orderService;
	
	@RequestMapping("ad/saveAdInfo")
	public String saveAdInfo(AdDomain adDomain){
		adService.saveAdInfo(adDomain);
		return "ad/advertisement_list";
	}
	
	@RequestMapping("ad/updateAdInfo")
	public String updateAdInfo(AdDomain adDomain){
		adService.updateAdInfo(adDomain);
		return "ad/advertisement_list";
	}
	
	@RequestMapping("ad/redirectAdInfo")
	public String redirectCardInfo(HttpServletRequest request,ModelMap model) throws Throwable{
		String adId = request.getParameter("adId");
		Map parameterMap = new HashMap();
		parameterMap.put("adId", adId);
		List<Map> adInfoMap = orderService.queryAdList(parameterMap);
		Map result = new HashMap();
		result.put("adInfoMap", adInfoMap);
		JSONObject obj = JSONObject.fromObject(result);
		model.put("result",obj);
		return "ad/advertisement_edit";
	}
	
}
