package com.rayuniverse.backshow.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BqueryDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 查询商品列表
	 * @param orderId
	 * @param string
	 * @return
	 */
	public List<HashMap<String, Object>> getGoodsList() {
		
		return sqlSessionTemplate.selectList("getGoodsList");
	}
	

	/**
	 * 插入商品信息
	 * @param agentCode
	 * @param accountDomain
	 * @return
	 */
	public void getAddgoods(Map AddgoodsMap)
	{
		sqlSessionTemplate.insert("getAddgoods", AddgoodsMap);
	}
	
	
	/**
	 * 插入sku
	 * @param agentCode
	 * @param accountDomain
	 * @return
	 */
	public void addSku(Map skuMap)
	{
		sqlSessionTemplate.insert("addSku", skuMap);
	}

	
	/**
	 * 查询充值卡列表
	 * @param orderId
	 * @param string
	 * @return
	 */
	public List<HashMap<String, Object>> getCardList() {
		
		return sqlSessionTemplate.selectList("getCardList");
	}
	
	
	/**
	 * 查询用户评价列表
	 * @return
	 */
	public List<HashMap<String, Object>> getpraiseList() {
		
		return sqlSessionTemplate.selectList("getpraiseList");
	}
	
	
	/**
	 * 查询广告列表
	 * @return
	 */
	public List<HashMap<String, Object>> queryAdList() {
		
		return sqlSessionTemplate.selectList("queryAdList");
	}
	
	
	/**
	 * 新增广告
	 * @return
	 */
	public void addAdvertise(Map adMap)
	{
		sqlSessionTemplate.insert("addAdvertise", adMap);
	}

	
}
