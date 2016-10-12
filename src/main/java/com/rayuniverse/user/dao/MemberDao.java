package com.rayuniverse.user.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rayuniverse.user.domain.AddressInfo;
import com.rayuniverse.user.domain.MemberInfo;

@Repository
public class MemberDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public MemberInfo getMemberInfo(String oprnId)
	{
		return sqlSessionTemplate.selectOne("getMemberInfo", oprnId);
	}
	
	public void addNewMember(MemberInfo member)
	{
		sqlSessionTemplate.insert("addNewMember", member);
	}
	
	public void addUserAddress(AddressInfo address)
	{
		sqlSessionTemplate.insert("addUserAddress", address);
	}
	
	public void updateMemberInfo(MemberInfo member)
	{
		sqlSessionTemplate.update("updateMemberInfo", member);
	}
	
	public void deleteMemberInfo(String userId)
	{
		sqlSessionTemplate.update("deleteMemberInfo", userId);
	}
}
