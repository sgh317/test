package com.rayuniverse.basic.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao {
	

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void insertOrderImages(Map<String, Object> paramMap) {
		this.sqlSessionTemplate.insert("insertOrderImages", paramMap);
	}

}
