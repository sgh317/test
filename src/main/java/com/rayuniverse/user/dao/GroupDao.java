package com.rayuniverse.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.user.domain.GroupInfo;

@Repository
public class GroupDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public GroupInfo getGroupInfoById(String id)
	{
		return sqlSessionTemplate.selectOne("getGroupInfoById", id);
	}
	
	public List getGroupResource(String id)
	{
		return sqlSessionTemplate.selectList("getGroupResource", id);
	}
	
	public GroupInfo getGroupInfoByCode(String code)
	{
		return sqlSessionTemplate.selectOne("getGroupInfoByCode", code);
	}
	
	public void addNewGroup(GroupInfo group)
	{
		sqlSessionTemplate.insert("addNewGroup", group);
	}

	public List<GroupInfo> getGroupList(String code, String name, String desc, String flag)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		param.put("name", name);
		param.put("desc", desc);
		param.put("flag", flag);
		
		return sqlSessionTemplate.selectList("queryGroupList", param);
	}
	
	public void updateGroupInfo(GroupInfo gi)
	{
		sqlSessionTemplate.update("updateGroupInfo", gi);
	}
	
	public void deleteGroupById(String id)
	{
		sqlSessionTemplate.update("deleteGroupById", id);
	}
	
	public void deleteGroupResource(String id)
	{
		sqlSessionTemplate.delete("deleteGroupResource", id);
	}
	
	public void insertGroupResource(String groupId, String resId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupId", groupId);
		param.put("resId", resId);
		sqlSessionTemplate.insert("insertGroupResource", param);
	}
}
