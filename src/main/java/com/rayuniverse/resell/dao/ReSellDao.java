package com.rayuniverse.resell.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.domain.OrderDetailDomain;

@Repository
public class ReSellDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 鏌ヨ璁㈠崟鐘舵�
	 * @param clientInfo
	 * @return
	 */
	public OrderDetailDomain getOrderStatus(
			OrderDetailDomain orderInfo) {
		return sqlSessionTemplate.selectOne("getOrderStatus", orderInfo);
	}
	
	
	/**
	 * 鍥為攢淇濆崟
	 * @param clientInfo
	 */
	public void reSellPolicyNo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("reSellPolicyNo", orderInfo);
	}
	
	
	/**
	 * 鏀惰垂纭
	 * @param clientInfo
	 */
	public void confirmAmount(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("confirmAmount", orderInfo);
	}
	
	/**
	 * 鍥為攢鑸剰闄╀繚鍗�
	 * @param clientInfo
	 */
	public void reSellHangYiPolicyNo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("reSellPolicyNo", orderInfo);
	}


	/**
	 * 查询订单列表信息
	 * @param policyNo
	 * @return
	 */
	public List<Map<String, Object>> queryOrderParameter(String orderId) {
		return sqlSessionTemplate.selectList("queryOrderParameter", orderId);
	}


	public Map<String,Object> queryNetFullInfo(String netCode) {
		return sqlSessionTemplate.selectOne("queryNetFullInfo", netCode);
	}
}
