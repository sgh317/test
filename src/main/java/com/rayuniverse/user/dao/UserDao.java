package com.rayuniverse.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.user.domain.GroupInfo;
import com.rayuniverse.user.domain.UserInfo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public UserInfo getUserInfo(String userId)
	{
		return sqlSessionTemplate.selectOne("getUserInfo", userId);
	}
	
	public UserInfo getUserInfoById(String id)
	{
		return sqlSessionTemplate.selectOne("getUserInfoById", id);
	}
	
	public boolean existsUser(String userId)
	{
		return ((Integer)sqlSessionTemplate.selectOne("existsUser", userId))>0;
	}
	
	public void addNewUser(UserInfo user)
	{
		
		sqlSessionTemplate.insert("addNewUser", user);
	}

	public List<UserInfo> getUserList(String userId, String userName, String userType, String flag)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("userName", userName);
		param.put("userType", userType);
		param.put("flag", flag);
		
		return sqlSessionTemplate.selectList("queryUserList", param);
	}
	
	public void updateUserInfo(UserInfo ui)
	{
		sqlSessionTemplate.update("updateUserInfo", ui);
	}
	
	public void deleteUserById(String id)
	{
		sqlSessionTemplate.update("deleteUserById", id);
	}
	
	public List queryResourceListByUserId(String userId)
	{
		return sqlSessionTemplate.selectList("queryResourceListByUserId", userId);
	}
	
	public List<GroupInfo> queryGroupListByUserId(String userId)
	{
		return sqlSessionTemplate.selectList("queryGroupListByUserId", userId);
	}
	
	public void deleteUserToGroup(String userId)
	{
		sqlSessionTemplate.delete("deleteUserToGroup", userId);
	}
	
	public void insertUserToGroup(String userId, String groupId)
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("groupId", groupId);
		
		sqlSessionTemplate.insert("insertUserToGroup", param);
	}
}
