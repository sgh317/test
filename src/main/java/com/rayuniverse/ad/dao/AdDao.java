package com.rayuniverse.ad.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.domain.AdDomain;

@Repository
public class AdDao {
	

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 
	 * @param adInfo
	 */
	public void saveAdInfo(AdDomain adInfo) {
		sqlSessionTemplate.insert("saveAdInfo", adInfo);
	}
	
	/**
	 * 
	 * @param adInfo
	 */
	public void updateAdInfo(AdDomain adInfo) {
		sqlSessionTemplate.update("updateAdInfo", adInfo);
	}
	/**
	 * 
	 * @param 展示首页广告
	 */
	public List<Map> queryAdhome(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryAdhome",parameterMap);
	}
	
	
	/**
	 * 
	 * @param 展示首页热点推荐
	 */
	public List<Map> queryHotadvise(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryHotadvise",parameterMap);
	}
	
	/**
	 * 
	 * @param 展示首页特价商品
	 */
	public List<Map> querydiscountgood(Map parameterMap) {
		return sqlSessionTemplate.selectList("querydiscountgood",parameterMap);
	}
}
