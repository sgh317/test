package com.rayuniverse.order.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rayuniverse.order.dao.OrderDao;
import com.rayuniverse.wchat.gw.menu.service.WXMenuService;

@Service
public class OrderService {
	
	private static Logger log = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	OrderDao orderDao;
	
	/**
	 * 查询总销售额和订单量
	 * @param orderInfo	
	 * @return
	 */
	public Map getOrderCount() {
		return orderDao.querySum();
	}
	
	/**
	 * 查询充值总额
	 * @param queryMap
	 * @return
	 */
	public Map querySumRecharge() {
		return orderDao.querySumRecharge();
	}
	
	/**
	 * 查询消费总额
	 * @param queryMap
	 * @return
	 */
	public Map querySumConsume() {
		return orderDao.querySumConsume();
	}
	
	/**
	 * 查询充值列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryRechargeList(Map parameterMap) {
		return orderDao.queryRechargeList(parameterMap);
	}
	/**
	 * 查询充值列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryRechargeTotalPageSize(Map parameterMap) {
		return orderDao.queryRechargeTotalPageSize(parameterMap);
	}
	
	/**
	 * 查询消费列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryConsumeList(Map parameterMap) {
		return orderDao.queryConsumeList(parameterMap);
	}
	/**
	 * 查询消费列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryConsumeTotalPageSize(Map parameterMap) {
		return orderDao.queryConsumeTotalPageSize(parameterMap);
	}
	
	/**
	 * 查询订单列表
	 * @param queryMap
	 * @return
	 */
	public Map queryOrderDetailList(Map parameterMap) {
		List<Map> orderMapList = orderDao.queryOrderDetailList(parameterMap);
		Map<Object,List> orderNewMap = new HashMap<Object,List>();
		
		if(null != orderMapList && orderMapList.size() != 0) {
			for(Map orderMap : orderMapList) {
				if(orderNewMap.containsKey(orderMap.get("orderNum"))) {
					((List) orderNewMap.get(orderMap.get("orderNum"))).add(orderMap);
				} else {
					List<Map> a = new ArrayList<Map>();
					a.add(orderMap);
					orderNewMap.put(orderMap.get("orderNum"), a);
				}
			}
		}
		return orderNewMap;
	}
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void processOrder(Map parameterMap) {
		orderDao.updateOrderStatus(parameterMap);
	}
	
	
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void processEvaluate(String praiseId) {
		orderDao.updateAuditStatus(praiseId);
	}
	
	/**
	 * 查询评论列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryEvaluateList(Map parameterMap) {
		return orderDao.queryEvaluateList(parameterMap);
	}
	
	
	/**
	 * 查询商品评价
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryEvaluateGood(Map parameterMap) {
		return orderDao.queryEvaluateGood(parameterMap);
	}
	
	
	/**
	 * 查询包邮价格
	 * @param queryMap
	 * @return
	 */
	
	public Map queryFreepost() {
		return orderDao.queryFreepost();
	}
	
	/**
	 * 查询评论列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryEvaluateTotalPageSize(Map parameterMap) {
		return orderDao.queryEvaluateTotalPageSize(parameterMap);
	}
	
	/**
	 * 查询广告列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryAdList(Map parameterMap) {
		List<Map> adList = orderDao.queryAdList(parameterMap);
		for(Map adMap:adList) {
			File file = new File(adMap.get("ad_picture").toString());
			adMap.put("ad_picture", file.getAbsolutePath());
			log.info(file.getAbsolutePath());
		}
		return orderDao.queryAdList(parameterMap);
	}
	
	/**
	 * 查询广告列表总数
	 * @param queryMap
	 * @return
	 */
	public Integer queryAdTotalPageSize(Map parameterMap) {
		return orderDao.queryAdTotalPageSize(parameterMap);
	}
	
	
	/**
	 * 更新广告的状态
	 * @param queryMap
	 * @return
	 */
	/*上架*/
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void processAd(String AdId) {
		orderDao.updateAdStatus(AdId);
	}
	/*下架*/
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void processAdN(String AdId) {
		orderDao.updateAdStatusN(AdId);
	}
	
	/*上下架*/
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void processAdY(Map parameterMap) {
		orderDao.updateAdStatusY(parameterMap);
	}
	
	/*修改包邮价格*/
	@Transactional(rollbackFor=Throwable.class,propagation=Propagation.REQUIRED)
	public void updateFreepost(Map parameterMap) {
		orderDao.updateFreepost(parameterMap);
	}
	
}
