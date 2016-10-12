package com.rayuniverse.basic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.rayuniverse.basic.service.ImageService;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.framework.jsrpc.JsBean;

@Controller
@JsBean("basicImageJSBean")
public class ImageJsBean {
	
	@Autowired
	ImageService imageService;
	
	@Transactional
	public String uploadOrderImages(String orderId,String serviceId,String url,String imageType){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = imageService.insertOrderImages(orderId,serviceId,url,imageType);
		
		return JsonUtil.toJson(resultMap, true);
	}

}
