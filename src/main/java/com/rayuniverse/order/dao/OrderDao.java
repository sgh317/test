package com.rayuniverse.order.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 查询总销售额和订单量
	 * @param queryMap
	 * @return
	 */
	public Map querySum() {
		return sqlSessionTemplate.selectOne("querySum");
	}

	
	/**
	 * 查询充值总额
	 * @param queryMap
	 * @return
	 */
	public Map querySumRecharge() {
		return sqlSessionTemplate.selectOne("querySumRecharge");
	}
	
	/**
	 * 查询消费总额
	 * @param queryMap
	 * @return
	 */
	public Map querySumConsume() {
		return sqlSessionTemplate.selectOne("querySumConsume");
	}
	
	
	/**
	 * 查询充值记录
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryRechargeList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryRechargeList",parameterMap);
	}
	
	public Integer queryRechargeTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryRechargeTotalPageSize",parameterMap);
	}
	
	/**
	 * 查询消费记录
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryConsumeList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryConsumeList",parameterMap);
	}
	
	public Integer queryConsumeTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryConsumeTotalPageSize",parameterMap);
	} 
	
	/**
	 * 查询消费记录
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> consumeList() {
		return sqlSessionTemplate.selectList("consumeList");
	}
	
	/**
	 * 查询订单列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryOrderDetailList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryOrderDetailList",parameterMap);
	}
	
	public void updateOrderStatus(Map parameterMap) {
		sqlSessionTemplate.update("updateOrderStatus", parameterMap);
	}
	
	/**
	 * 更新评论的审核状态
	 * @param queryMap
	 * @return
	 */
	
	public void updateAuditStatus(String praiseId) {
		sqlSessionTemplate.update("updateAuditStatus", praiseId);
	}
	
	
	/**
	 * 查询评论列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryEvaluateList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryEvaluateList",parameterMap);
	}
	
	public Integer queryEvaluateTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryEvaluateTotalPageSize",parameterMap);
	}
	
	/**
	 * 查询广告列表
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryAdList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryAdList",parameterMap);
	}
	
	public Integer queryAdTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryAdTotalPageSize",parameterMap);
	}
	
	public void updateAdStatus(String AdId) {
		sqlSessionTemplate.update("updateAdStatus", AdId);
	}
	
	public void updateAdStatusN(String AdId) {
		sqlSessionTemplate.update("updateAdStatusN", AdId);
	}
	
	public void updateAdStatusY(Map parameterMap) {
		sqlSessionTemplate.update("updateAdStatusY", parameterMap);
	}
	/**
	 * 查询商品评价
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryEvaluateGood(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryEvaluateGood",parameterMap);
	}
	
	/**
	 * 修改包邮价格
	 * @param queryMap
	 * @return
	 */
	public void updateFreepost(Map parameterMap) {
		sqlSessionTemplate.update("updateFreepost", parameterMap);
	}
	
	
	/**
	 * 查询包邮价格
	 * @param queryMap
	 * @return 
	 */
	public Map queryFreepost() {
		return sqlSessionTemplate.selectOne("queryFreepost");
	}
	
}
