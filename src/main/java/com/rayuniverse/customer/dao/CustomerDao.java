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
	 * ��ѯ�ͻ�����
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
	 * ��ѯδ���û�
	 * @param clientInfo
	 * @return
	 */
	public int countClientInfoNotBind(CustomerDetailDomain clientInfo) {
		return 0;
	}

	/**
	 * ���ݻ�����Ϣ��ѯ��ȫ����Ϣ
	 * @param clientInfo
	 * @return
	 */
	public CustomerDetailDomain queryClientInfoByParameters(
			CustomerDetailDomain clientInfo) {
		return sqlSessionTemplate.selectOne("queryClientInfoByParameters", clientInfo);
	}

	/**
	 * ����ͻ�
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
	 * ����openId��ѯ��ϸ��Ϣ
	 * @param openId
	 * @return
	 */
	public CustomerDetailDomain queryClientInfoByOpenId(String openId) {
		return sqlSessionTemplate.selectOne("queryClientInfoByOpenId", openId);
	}

	/**
	 * ����id�Ų�ѯ�Ƿ�����ӹ���Ӧ�û�
	 * @param umUserId
	 * @return
	 */
	public int countByOpenId(String umUserId) {
		return sqlSessionTemplate.selectOne("countByOpenId", umUserId);
	}

	/**
	 * ����������Ϣ
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
	 * ���ݳ�����ѯ����ϸ��Ϣ�б�
	 * @param carType
	 * @return
	 */
	public List<CarTypeListDomain> queryCarTypeList(String carType) {
		return sqlSessionTemplate.selectList("queryCarTypeList", carType);
	}

	/**
	 * ��ѯ��ϸ��Ϣ
	 * @param carSeq
	 * @return
	 */
	public CarTypeListDomain queryCarDetailInfoByCarSeq(String carSeq) {
		return sqlSessionTemplate.selectOne("queryCarDetailInfoByCarSeq", carSeq);
	}

	/**
	 * ���붩������
	 * @param orderInfo
	 */
	public void insertOrderDetailInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.insert("insertOrderDetailInfo", orderInfo);
	}

	/**
	 * ���¶�����Ϣ
	 * @param orderInfo
	 */
	public void updateOrderDetailInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("updateOrderDetailInfo", orderInfo);
	}

	/**
	 * ɾ��������Ϣ
	 * @param orderId
	 */
	public void deleteOrderRiskInfo(String orderId) {
		sqlSessionTemplate.delete("deleteOrderRiskParameterInfo", orderId);
		sqlSessionTemplate.delete("deleteOrderRiskInfo", orderId);
	}

	/**
	 * �����������openid��ѯδ��ɶ���
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
	 * ����orderId��ѯ��Ϣ
	 * @param orderId
	 * @return
	 */
	public OrderDetailDomain queryOrderByOrderId(String orderId) {
		return sqlSessionTemplate.selectOne("queryOrderByOrderId", orderId);
	}

	/**
	 * ��ѯ��Ʒ�б�
	 * @param orderId
	 * @return
	 */
	public List<ProductDomain> queryProductListByOrderId(String orderId) {
		return sqlSessionTemplate.selectList("queryProductListByOrderId", orderId);
	}

	/**
	 * ��ѯ�����б�
	 * @param orderId
	 * @return
	 */
	public PolicyDomain queryProductDomainInfo(String orderId) {
		return sqlSessionTemplate.selectOne("queryProductDomainInfo", orderId);
	}

	/**
	 * ���涩����Ʒ��Ϣ
	 * @param orderRisk
	 */
	public void insertOrderRiskInfo(OrderRiskInfo orderRisk) {
		sqlSessionTemplate.insert("insertOrderRiskInfo", orderRisk);
	}

	/**
	 * ��ѯ������Ϣ
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
	 * ��ѯ�������Ա�
	 * @param orderParameter
	 */
	public void insertOrderRiskParameterInfo(OrderRiskParameter orderParameter) {
		
		sqlSessionTemplate.insert("insertOrderRiskParameterInfo", orderParameter);
	}

	/**
	 * �����û�����
	 * @param customerDomain
	 */
	public void updateClientIntegral(CustomerDetailDomain customerDomain) {
		sqlSessionTemplate.update("updateClientIntegral",customerDomain);
	}

	/**
	 * ���¶���������Ϣ
	 * @param orderInfo
	 */
	public void updateOrderPremInfo(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("updateOrderPremInfo",orderInfo);
	}
	
	
/**
 * ����orderid�����ֺŲ�ѯ��������
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
	 * �ύ����
	 * @param orderInfo
	 */
	public void submitOrder(OrderDetailDomain orderInfo) {
		sqlSessionTemplate.update("submitOrder",orderInfo);
	}

	/**
	 * ��������
	 * @param orderParameter
	 */
	public void insertOrderParameter(OrderParameterDomain orderParameter) {
		sqlSessionTemplate.update("insertOrderParameter",orderParameter);
	}

	/**
	 * ������
	 * @param hyxOrder
	 */
	public void insertHYXOrder(OrderDetailDomain hyxOrder) {
		sqlSessionTemplate.insert("insertHYXOrder", hyxOrder);
	}

	/**
	 * ɾ��
	 * @param orderId
	 */
	public void deleteOrderRiskParameter(String orderId) {
		sqlSessionTemplate.delete("deleteOrderRiskParameter", orderId);
	}

	/**
	 * ��ѯ�Ƿ�������Ϣ
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
	 * ���¿ͻ���Ϣ
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
	 * ɾ��������Ϣ
	 * @param orderId
	 */
	public void deleteImageInfo(String orderId) {
		sqlSessionTemplate.delete("deleteImageInfo", orderId);
	}

	/**
	 * ���»��ֺ���Ϣ
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
	 * ��ѯ�ܼ�����
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
