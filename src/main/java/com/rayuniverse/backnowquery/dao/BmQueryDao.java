package com.rayuniverse.backnowquery.dao;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BmQueryDao {
		
		
		@Autowired
		@Qualifier("sqlSessionTemplate")
		private SqlSessionTemplate sqlSessionTemplate;
		
		/**
		 * 查询1分钟之内的新订单
		 * @param queryMap
		 * @return
		 */
		public int queryBmList8() {
			return sqlSessionTemplate.selectOne("queryNewOrder");
		}
		
		/**
		 * 查询近12个月的销售额
		 * @param queryMap
		 * @return
		 */
		public List<Map<String, Object>> querySumsaleBymonth() {
			return sqlSessionTemplate.selectList("querySumsaleBymonth");
		}
		
		/**
		 * 查询近12个月的订单量
		 * @param queryMap
		 * @return
		 */
		public List<Map<String, Object>> querySumorderBymonth() {
			return sqlSessionTemplate.selectList("querySumorderBymonth");
		}

	}