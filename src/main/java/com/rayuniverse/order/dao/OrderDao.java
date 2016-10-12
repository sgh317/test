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
	 * ��ѯ�����۶�Ͷ�����
	 * @param queryMap
	 * @return
	 */
	public Map querySum() {
		return sqlSessionTemplate.selectOne("querySum");
	}

	
	/**
	 * ��ѯ��ֵ�ܶ�
	 * @param queryMap
	 * @return
	 */
	public Map querySumRecharge() {
		return sqlSessionTemplate.selectOne("querySumRecharge");
	}
	
	/**
	 * ��ѯ�����ܶ�
	 * @param queryMap
	 * @return
	 */
	public Map querySumConsume() {
		return sqlSessionTemplate.selectOne("querySumConsume");
	}
	
	
	/**
	 * ��ѯ��ֵ��¼
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
	 * ��ѯ���Ѽ�¼
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
	 * ��ѯ���Ѽ�¼
	 * @param queryMap
	 * @return
	 */
	public List<Map<String, Object>> consumeList() {
		return sqlSessionTemplate.selectList("consumeList");
	}
	
	/**
	 * ��ѯ�����б�
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
	 * �������۵����״̬
	 * @param queryMap
	 * @return
	 */
	
	public void updateAuditStatus(String praiseId) {
		sqlSessionTemplate.update("updateAuditStatus", praiseId);
	}
	
	
	/**
	 * ��ѯ�����б�
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
	 * ��ѯ����б�
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
	 * ��ѯ��Ʒ����
	 * @param queryMap
	 * @return
	 */
	public List<Map> queryEvaluateGood(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryEvaluateGood",parameterMap);
	}
	
	/**
	 * �޸İ��ʼ۸�
	 * @param queryMap
	 * @return
	 */
	public void updateFreepost(Map parameterMap) {
		sqlSessionTemplate.update("updateFreepost", parameterMap);
	}
	
	
	/**
	 * ��ѯ���ʼ۸�
	 * @param queryMap
	 * @return 
	 */
	public Map queryFreepost() {
		return sqlSessionTemplate.selectOne("queryFreepost");
	}
	
}
