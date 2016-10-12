/*package com.rayuniverse.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.domain.OrderParameterDomain;
import com.rayuniverse.domain.OrderRiskInfo;
import com.rayuniverse.domain.OrderRiskParameter;
import com.rayuniverse.order.dao.OrderDao;
import com.rayuniverse.wchat.gw.asyn.WXConfigService;
import com.rayuniverse.wchat.gw.cfg.ConfigItem;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	WXConfigService cfg;
	*//**
	 * 鏌ヨ璁㈠崟鍒楄〃
	 * @param orderInfo	
	 * @return
	 *//*
	public List<OrderDetailDomain> getOrderList(OrderDetailDomain orderInfo) {
							
		List<OrderDetailDomain> orderList = orderDao.getOrderList(orderInfo);
		
		return orderList;
	}
	
	
	
	*//**
	 * 鏍规嵁椤甸潰鍙傛暟鏌ヨ璁㈠崟鍒楄〃
	 * @param orderInfo	
	 * @return
	 *//*
	public List<OrderDetailDomain> getParamOrderList(OrderDetailDomain orderInfo) {
							
		List<OrderDetailDomain> orderList = orderDao.getParamOrderList(orderInfo);
		
		return orderList;
	}
	
	
	*//**
	 * 鏌ヨ鍘嗗彶鎵�湁璁㈠崟鍒楄〃
	 * @param orderInfo	
	 * @return
	 *//*
	public List<OrderDetailDomain> getAllOrderList() {
							
		List<OrderDetailDomain> orderList = orderDao.getAllOrderList();
		
		return orderList;
	}
	
	*//**
	 * 鏌ヨ璁㈠崟璇︾粏淇℃伅
	 * @param orderInfo	
	 * @return
	 *//*
	public OrderDetailDomain queryOrderByOrderId(String orderId) {
							
		OrderDetailDomain orderInfo = orderDao.queryOrderByOrderId(orderId);
		
		return orderInfo;
	}
	
	*//**
	 * 鏌ヨ瀹㈡埛璇︾粏淇℃伅
	 * @param orderInfo	
	 * @return
	 *//*
	public CustomerDetailDomain queryClientInfoByOpenId(String openId) {
							
		CustomerDetailDomain customerDomain = orderDao.queryClientInfoByOpenId(openId);
		
		return customerDomain;
	}
	
	*//**
	 * 鏌ヨ闄╃鍒楄〃
	 * @param orderInfo	
	 * @return
	 *//*
	public List<OrderRiskInfo> queryProductListByOrderId(String orderId) {
							
		List<OrderRiskInfo> productInfoList = orderDao.queryProductListByOrderId(orderId);
		
		return productInfoList;
	}
	
	*//**
	 * 鏌ヨ闄╃闄勫姞灞炴�鍒楄〃
	 * @param orderInfo	
	 * @return
	 *//*
	public List<OrderRiskParameter> queryRiskParameterByProductAndOrder(String orderId, String productSeq) {
							
		List<OrderRiskParameter> orderRiskParameterList = orderDao.queryRiskParameterByProductAndOrder(orderId,productSeq);
		
		return orderRiskParameterList;
	}



	*//**
	 * 查询订单影像
	 * @param orderId
	 * @return
	 *//*
	public Map<String, Object> queryOrderImageList(String orderId) {
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		List<HashMap<String,Object>> xszImageList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> sfzImageList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> jszImageList = new ArrayList<HashMap<String,Object>>();
		
		xszImageList = orderDao.queryOrderImage(orderId,"imageXSZ");
		sfzImageList = orderDao.queryOrderImage(orderId,"imageSFZ");
		jszImageList = orderDao.queryOrderImage(orderId,"imageJSZ");
		
		
		HashMap map=cfg.getAccessToken();
		ConfigItem item=new ConfigItem();
		item.init(map);
		if(item.isValidate()==false)
		{
			 map=cfg.getAccessToken();
		}
		String token=item.getValue();
		
		for(int i=0;i<xszImageList.size();i++)
		{
			HashMap<String,Object> imageInfo = xszImageList.get(i);
			imageInfo.put("url", "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+imageInfo.get("image_id"));
			xszImageList.set(i, imageInfo);
		}
		
		for(int i=0;i<sfzImageList.size();i++)
		{
			HashMap<String,Object> imageInfo = sfzImageList.get(i);
			imageInfo.put("url", "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+imageInfo.get("image_id"));
			sfzImageList.set(i, imageInfo);
		}
		
		for(int i=0;i<jszImageList.size();i++)
		{
			HashMap<String,Object> imageInfo = jszImageList.get(i);
			imageInfo.put("url", "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+token+"&media_id="+imageInfo.get("image_id"));
			jszImageList.set(i, imageInfo);
		}
		
		returnMap.put("xszImageList", xszImageList);
		returnMap.put("sfzImageList", sfzImageList);
		returnMap.put("jszImageList", jszImageList);
		
		return returnMap;
	}


	*//**
	 * 
	 * @param orderId
	 * @return
	 *//*
	public List<OrderParameterDomain> queryOrderParameterList(String orderId) {
		return orderDao.queryOrderParameterList(orderId);
	}
}
*/