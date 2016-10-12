package com.rayuniverse.basic.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.basic.dao.ImageDao;

@Service
public class ImageService {
	
	@Autowired
	ImageDao imageDao;

	/**
	 * 记录信息
	 * @param orderId
	 * @param serviceId
	 * @param url
	 * @param imageType
	 * @return
	 */
	public Map<String, Object> insertOrderImages(String orderId,
			String serviceId, String url, String imageType) {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("serviceId", serviceId);
		paramMap.put("url", url);
		paramMap.put("imageType", imageType);
		paramMap.put("imageStatus", "0");
		
		imageDao.insertOrderImages(paramMap);
		
		resultMap.put("flag","Y");
		resultMap.put("message","保存成功");
		return resultMap;
	}

}
