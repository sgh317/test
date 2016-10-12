package com.rayuniverse.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.domain.AreaDomain;
import com.rayuniverse.domain.CarTypeListDomain;
import com.rayuniverse.domain.ClientInfoParameterDomain;
import com.rayuniverse.domain.CustomerDetailDomain;
import com.rayuniverse.domain.NetDomain;
import com.rayuniverse.domain.OrderDetailDomain;
import com.rayuniverse.domain.OrderParameterDomain;
import com.rayuniverse.domain.OrderRiskInfo;
import com.rayuniverse.domain.OrderRiskParameter;
import com.rayuniverse.domain.PolicyDomain;
import com.rayuniverse.domain.ProductDomain;

@Repository
public class CustomerDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查询客户数量
	 * @param clientInfo
	 * @return
	 */
	public int countClientInfo(CustomerDetailDomain clientInfo) {
		return sqlSessionTemplate.selectOne("countClientInfo", clientInfo);
	}

	/**
	 * 
	 * @param clientInfo
	 */
	public void insertClientInfo(CustomerDetailDomain clientInfo) {
		sqlSessionTemplate.insert("insertClientInfo", clientInfo);
	}

	/**
	 * 查询未绑定用户
	 * @param clientInfo
	 * @return
	 */
	public int countClientInfoNotBind(CustomerDetailDomain clientInfo) {
		return 0;
	}

	/**
	 * 根据基本信息查询绑定全量信息
	 * @param clientInfo
	 * @return
	 */
	public CustomerDetailDomain queryClientInfoByParameters(
			CustomerDetailDomain clientInfo) {
		return sqlSessionTemplate.selectOne("queryClientInfoByParameters", clientInfo);
	}

	/**
	 * 激活客户
	 * @param clientInfo
	 */
	public void effectClient(CustomerDetailDomain clientInfo) {
		sqlSessionTemplate.update("effectClient", clientInfo);
	}

	/**
	 * 
	 * @param parameterMap
	 * @return
	 */
	public List<CustomerDetailDomain> queryImportClient(
			Map<String, Object> parameterMap) {
		return sqlSessionTemplate.selectList("queryImportClient", parameterMap);
	}

	/**
	 * 根据openId查询详细信息
	 * @param openId
	 * @return
	 */
	public CustomerDetailDomain queryClientInfoByOpenId(String openId) {
		return sqlSessionTemplate.selectOne("queryClientInfoByOpenId", openId);
	}

	/**
	 * 根据id号查询是否已添加过对应用户
	 * @param umUserId
	 * @return
	 */
	public int countByOpenId(String umUserId) {
		return sqlSessionTemplate.selectOne("countByOpenId", umUserId);
	}

	/**
	 * 更新赠送信息
	 * @param openId
	 * @param zsType
	 */
	public void updateGivenDate(String openId, String zsType) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("openId", openId);
		parameterMap.put("zsType", zsType);
		sqlSessionTemplate.update("updateGivenDate", parameterMap);
		
	}

	/**
	 * 根据车类别查询车详细信息列表
	 * @param carType
	 * @return
	 */
	public List<CarTypeListDomain> queryCarTypeList(String carType) {
		return sqlSessionTemplate.selectList("queryCarTypeList", carType);
	}

	/**
	 * 查询详细信息
	 * @param carSeq
	 * @return
	 */
	public CarTypeListDomain queryCarDetailInfoByCarSeq(String carSeq) {
		return sqlSessionTemplate.selectOne("queryCarDetailInfoByCarSeq", carSeq);
	}

	/**
	 * 插入订单数据
	 * @param orderInfo
	 */
	public void insertOrderDetailInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.insert("insertOrderDetailInfo", orderInfo);
	}

	/**
	 * 更新订单信息
	 * @param orderInfo
	 */
	public void updateOrderDetailInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("updateOrderDetailInfo", orderInfo);
	}

	/**
	 * 删除险种信息
	 * @param orderId
	 */
	public void deleteOrderRiskInfo(String orderId) {
		sqlSessionTemplate.delete("deleteOrderRiskParameterInfo", orderId);
		sqlSessionTemplate.delete("deleteOrderRiskInfo", orderId);
	}

	/**
	 * 根据险种类别openid查询未完成订单
	 * @param openId
	 * @param string
	 * @return
	 */
	public OrderDetailDomain queryOrderByTypeAndNotFinished(String openId,
			String productType) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("openId", openId);
		parameterMap.put("productType", productType);
		return sqlSessionTemplate.selectOne("queryOrderByTypeAndNotFinished", parameterMap);
	}

	/**
	 * 根据orderId查询信息
	 * @param orderId
	 * @return
	 */
	public OrderDetailDomain queryOrderByOrderId(String orderId) {
		return sqlSessionTemplate.selectOne("queryOrderByOrderId", orderId);
	}

	/**
	 * 查询产品列表
	 * @param orderId
	 * @return
	 */
	public List<ProductDomain> queryProductListByOrderId(String orderId) {
		return sqlSessionTemplate.selectList("queryProductListByOrderId", orderId);
	}

	/**
	 * 查询订单列表
	 * @param orderId
	 * @return
	 */
	public PolicyDomain queryProductDomainInfo(String orderId) {
		return sqlSessionTemplate.selectOne("queryProductDomainInfo", orderId);
	}

	/**
	 * 保存订单产品信息
	 * @param orderRisk
	 */
	public void insertOrderRiskInfo(OrderRiskInfo orderRisk) {
		sqlSessionTemplate.insert("insertOrderRiskInfo", orderRisk);
	}

	/**
	 * 查询订单信息
	 * @param orderId
	 * @param productCode
	 * @return
	 */
	public String queryProductSeq(String orderId, String productCode) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		
		parameterMap.put("orderId", orderId);
		parameterMap.put("productCode", productCode);
		
		return sqlSessionTemplate.selectOne("queryProductSeq", parameterMap);
	}

	/**
	 * 查询险种属性表
	 * @param orderParameter
	 */
	public void insertOrderRiskParameterInfo(OrderRiskParameter orderParameter) {
		
		sqlSessionTemplate.insert("insertOrderRiskParameterInfo", orderParameter);
	}

	/**
	 * 更新用户积分
	 * @param customerDomain
	 */
	public void updateClientIntegral(CustomerDetailDomain customerDomain) {
		sqlSessionTemplate.update("updateClientIntegral",customerDomain);
	}

	/**
	 * 更新订单交费信息
	 * @param orderInfo
	 */
	public void updateOrderPremInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("updateOrderPremInfo",orderInfo);
	}
	
	
/**
 * 根据orderid和险种号查询险种属性
 * @param orderId
 * @param productCode
 * @return
 */
	public List<OrderRiskParameter> queryRiskParameterByProductAndOrder(
			String orderId, String productSeq) {
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("orderId", orderId);
		queryMap.put("productSeq", productSeq);
		return sqlSessionTemplate.selectList("queryRiskParameterByProductAndOrder", queryMap);
	}

	/**
	 * 
	 * @param cityCode
	 * @return
	 */
	public List<AreaDomain> queryAreaByCity(String cityCode) {
		return sqlSessionTemplate.selectList("queryAreaByCity", cityCode);
	}

	/**
	 * 
	 * @param areaCode
	 * @return
	 */
	public List<NetDomain> queryNetByArea(String areaCode) {
		return sqlSessionTemplate.selectList("queryNetByArea", areaCode);
	}

	/**
	 * 提交订单
	 * @param orderInfo
	 */
	public void submitOrder(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("submitOrder",orderInfo);
	}

	/**
	 * 订单属性
	 * @param orderParameter
	 */
	public void insertOrderParameter(OrderParameterDomain orderParameter) {
		sqlSessionTemplate.update("insertOrderParameter",orderParameter);
	}

	/**
	 * 航意险
	 * @param hyxOrder
	 */
	public void insertHYXOrder(OrderDetailDomain hyxOrder) {
		sqlSessionTemplate.insert("insertHYXOrder", hyxOrder);
	}

	/**
	 * 删除
	 * @param orderId
	 */
	public void deleteOrderRiskParameter(String orderId) {
		sqlSessionTemplate.delete("deleteOrderRiskParameter", orderId);
	}

	/**
	 * 查询是否推送消息
	 * @param openId
	 * @param string
	 * @return
	 */
	public ClientInfoParameterDomain queryClientParameterInfo(String openId,
			String parameterKey) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("openId", openId);
		parameterMap.put("parameterKey", parameterKey);
		return sqlSessionTemplate.selectOne("queryClientParameterInfo", parameterMap);
	}

	/**
	 * 
	 * @param openId
	 * @param string
	 * @param string2
	 */
	public void updateClientParameterInfo(String openId, String parameterKey,
			String parameterValue) {
		ClientInfoParameterDomain clientParameter = new ClientInfoParameterDomain();
		clientParameter.setOpenId(openId);
		clientParameter.setParameterKey(parameterKey);
		clientParameter.setParameterValue(parameterValue);
		
		int countParameterKey = sqlSessionTemplate.selectOne("countParameterKey", clientParameter);
		
		if(countParameterKey>0){
			sqlSessionTemplate.update("updateClientParameterInfo", clientParameter);
		}else{
			sqlSessionTemplate.insert("insertClientParameterInfo", clientParameter);
		}
		
	}

	/**
	 * 更新客户信息
	 * @param applicantName
	 * @param applicantIdNo
	 * @param $missing$
	 */
	public void updateClientInfo(String applicantName, String applicantIdNo,
			String openId) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("applicantName", applicantName);
		parameterMap.put("applicantIdNo", applicantIdNo);
		parameterMap.put("openId", openId);
		
		sqlSessionTemplate.update("updateClientInfoHYX", parameterMap);
		
	}

	public int queryHYXConunt(String openId) {
		return sqlSessionTemplate.selectOne("queryHYXConunt", openId);
	}
	

	/**
	 * 删除订单信息
	 * @param orderId
	 */
	public void deleteImageInfo(String orderId) {
		sqlSessionTemplate.delete("deleteImageInfo", orderId);
	}

	/**
	 * 更新积分和信息
	 * @param updateMap
	 */
	public void updateIntegral(Map<String, Object> updateMap) {
		sqlSessionTemplate.update("updateIntegral", updateMap);
	}

	/**
	 * 
	 * @param openId
	 * @return
	 */
	public int countOrderByOpenId(String openId,String riskType) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("openId", openId);
		parameterMap.put("riskType", riskType);
		return sqlSessionTemplate.selectOne("countOrderByOpenId", parameterMap);
	}

	/**
	 * 查询总计条数
	 * @param parameterMap
	 * @return
	 */
	public Integer queryTotalPageSize(Map<String, Object> parameterMap) {
		return sqlSessionTemplate.selectOne("queryTotalPageSize", parameterMap);
	}

	/**
	 * 
	 * @param openId
	 */
	public void deleteClientBind(String openId) {
		sqlSessionTemplate.update("updateClientBind", openId);
		sqlSessionTemplate.delete("deleteClientBind", openId);
	}

	/**
	 * 
	 * @param openId
	 * @return
	 */
	public String queryOrderIdByClientId(String openId) {
		return sqlSessionTemplate.selectOne("queryOrderIdByClientId", openId);
	}

	/**
	 * 
	 * @param openId
	 */
	public void deleteClientOrderBind(String orderId) {
		sqlSessionTemplate.delete("deleteOrderDetail",orderId);
		sqlSessionTemplate.delete("deleteOrderParameter",orderId);
		sqlSessionTemplate.delete("deleteOrderRisk",orderId);
		sqlSessionTemplate.delete("deleteOrderRiskParameter",orderId);
	}	
}
