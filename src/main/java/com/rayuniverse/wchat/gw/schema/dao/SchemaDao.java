package com.rayuniverse.wchat.gw.schema.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.wchat.gw.schema.domain.Schema;

@Repository
public class SchemaDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public List<Schema> getAllSchema()
	{
		  List<Schema> list= sqlSessionTemplate.selectList("getAllSchema");
		  if(list!=null)
			{
				for(Schema s:list)
				{
					s.initByData();
				}
			}
		  return list;
	}

	
	public List<Schema> getSchemaList(String type, String flag)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("flag", flag);
		
		return sqlSessionTemplate.selectList("getSchemaList", param);
	}
	
	public void addSchemaInfo(Schema sca)
	{
		sqlSessionTemplate.selectList("addSchemaInfo", sca);
	}
	
	public Schema getMessageInfoById(String id)
	{
		return sqlSessionTemplate.selectOne("getSchemaInfoById", id);
	}
	
	public void updateSchemaInfo(Schema sca)
	{
		sqlSessionTemplate.update("updateSchemaInfo", sca);
	}
	
	public void deleteSchemaById(String id)
	{
		sqlSessionTemplate.update("deleteSchemaInfo", id);
	}
}
