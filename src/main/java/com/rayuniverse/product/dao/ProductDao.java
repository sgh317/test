package com.rayuniverse.product.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.domain.ProductDomain;
import com.rayuniverse.domain.SkuDomain;

@Repository
public class ProductDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 
	 * @param productInfo
	 */
	public void saveProductInfo(ProductDomain productInfo) {
		sqlSessionTemplate.insert("saveProductInfo", productInfo);
	}
	
	/**
	 * 
	 * @param productInfo
	 */
	public void updateProductInfo(ProductDomain productInfo) {
		sqlSessionTemplate.update("updateProductInfo", productInfo);
	}
	
	/**
	 * 
	 * @param productInfo
	 */
	public void saveSkuInfo(SkuDomain skuInfo) {
		sqlSessionTemplate.insert("saveSkuInfo", skuInfo);
	}
	
	/**
	 * 
	 * @param productInfo
	 */
	public void updateSkuInfo(SkuDomain skuInfo) {
		sqlSessionTemplate.update("updateSkuInfo", skuInfo);
	}
	
	public List<Map> queryCatalogList() {
		return sqlSessionTemplate.selectList("queryCatalogList");
	}


	public List<Map> queryClassifyList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryClassifyList",parameterMap);
	}
	
	public Integer queryClassifyListTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryClassifyListTotalPageSize",parameterMap);
	}
	
	public List<Map> queryProductInfoList(Map parameterMap) {
		return sqlSessionTemplate.selectList("queryProductInfoList",parameterMap);
	}
	
	public Integer queryProductInfoListTotalPageSize(Map parameterMap) {
		return sqlSessionTemplate.selectOne("queryProductInfoListTotalPageSize",parameterMap);
	}
	
	public void updateProductStatus(Map parameterMap) {
		sqlSessionTemplate.update("updateProductStatus", parameterMap);
	}

	/**
	 * 
	 * @param 查询分类名称
	 */
	public List<Map> queryProductClassifyList(String classify) {
		return sqlSessionTemplate.selectList("queryProductClassifyList",classify);
	}
	
	public void deleteSkuInfo(String skuId) {
		sqlSessionTemplate.delete("deleteSkuInfo", skuId);
	}
}
